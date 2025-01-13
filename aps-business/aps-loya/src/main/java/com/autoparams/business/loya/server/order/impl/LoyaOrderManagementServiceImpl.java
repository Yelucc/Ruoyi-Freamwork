package com.autoparams.business.loya.server.order.impl;

import cn.hutool.core.collection.CollUtil;
import com.autoparams.business.loya.domain.LoyaJewel;
import com.autoparams.business.loya.domain.LoyaLogistics;
import com.autoparams.business.loya.domain.LoyaOrder;
import com.autoparams.business.loya.domain.vo.LoyaOrderReserveVo;
import com.autoparams.business.loya.domain.vo.LoyaOrderVo;
import com.autoparams.business.loya.enums.OrderStatus;
import com.autoparams.business.loya.mapper.LoyaOrderManagementMapper;
import com.autoparams.business.loya.server.jewel.ILoyaJewelCaseService;
import com.autoparams.business.loya.server.logistics.ILoyaLogisticsService;
import com.autoparams.business.loya.server.order.ILoyaOrderManagementService;
import com.autoparams.business.loya.server.order.Strategy.OrderProcessStrategy;
import com.autoparams.business.loya.server.order.Strategy.OrderProcessStrategyFactory;
import com.autoparams.common.base.utils.SecurityUtils;
import com.autoparams.common.base.utils.bean.BeanUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.autoparams.business.loya.enums.ReservationStatus.AVAILABLE;

/**
 * 订单管理Service业务层处理
 *
 * @author ruoyi
 * @date 2024-08-09
 */
@Service
public class LoyaOrderManagementServiceImpl extends ServiceImpl<LoyaOrderManagementMapper, LoyaOrder> implements ILoyaOrderManagementService {
    @Autowired
    private OrderProcessStrategyFactory orderProcessStrategyFactory;
    @Autowired
    private ILoyaJewelCaseService caseService;
    @Autowired
    private ILoyaLogisticsService logisticsService;


    /**
     * @param order
     * @return
     */
    @Override
    public Page<LoyaOrderVo> listVo(Page page, LoyaOrderVo order) {
        Page<LoyaOrder> loyaOrders = page(page, Wrappers.lambdaQuery(LoyaOrder.class)
                .eq(Objects.nonNull(order.getOrderStatus()), LoyaOrder::getOrderStatus, order.getOrderStatus())
                .eq(LoyaOrder::getCreateBy, SecurityUtils.getLoginUser().getUserId())
        );

        Page<LoyaOrderVo> res = new Page<>();
        res.setRecords(loyaOrders.getRecords().stream()
                .peek(item -> {
                     item.setJewels(caseService.list(Wrappers.lambdaQuery(LoyaJewel.class)
                            .in(LoyaJewel::getJewelId, Arrays.asList(item.getJewelCode())
                            )));
                    item.setTripInfo(logisticsService.getOne(Wrappers.lambdaQuery(LoyaLogistics.class)
                            .eq(LoyaLogistics::getDirection, "trip")
                            .eq(LoyaLogistics::getOrderId, item.getOrderId())));
                    item.setTurnInfo(logisticsService.list(Wrappers.lambdaQuery(LoyaLogistics.class)
                            .eq(LoyaLogistics::getDirection, "turn")
                            .eq(LoyaLogistics::getOrderId, item.getOrderId())));
                })
                .map(LoyaOrderVo::of)
                .collect(Collectors.toList()));
        res.setTotal(loyaOrders.getTotal());
        return res;
    }

    /**
     * @param order
     * @return
     */
    @Transactional
    @Override
    public Boolean createOrder(LoyaOrderReserveVo order) {

        if (Objects.isNull(order.getOrderId())) {
            //如果地址填写 插入地址 直接走锁定策略 检查地址填写
            if (order.checkPending()) {
                LoyaOrder loyaOrder = new LoyaOrder();
                BeanUtils.copyBeanProp(loyaOrder, order);
                loyaOrder.setJewelCode(order.getJewelCode().toArray(new String[0]));
                loyaOrder.setOrderStatus(OrderStatus.LOCKED);
                save(loyaOrder);

                LoyaLogistics logistics = new LoyaLogistics();
                BeanUtils.copyBeanProp(logistics, order);
                logistics.setOrderId(loyaOrder.getOrderId());
                logistics.setDirection("trip");
                logistics.setJewelIds(order.getJewelCode().toArray(new String[0]));
                logisticsService.save(logistics);


                OrderProcessStrategy strategy = orderProcessStrategyFactory.getStrategy(loyaOrder.getOrderStatus());
                return strategy.process(loyaOrder);

            } else {
                // 如果地址未填写 从初始化开始
                LoyaOrder loyaOrder = new LoyaOrder();
                BeanUtils.copyBeanProp(loyaOrder, order);
                loyaOrder.setJewelCode(order.getJewelCode().toArray(new String[0]));
                loyaOrder.setOrderStatus(OrderStatus.INIT);
                save(loyaOrder);
                OrderProcessStrategy strategy = orderProcessStrategyFactory.getStrategy(loyaOrder.getOrderStatus());
                return strategy.process(loyaOrder);
            }
        } else {
            if (order.checkPending()) {
                LoyaOrder loyaOrder = getById(order.getOrderId());
                //还原首饰预定状态
                Collection<String> jewels = CollUtil.subtract(CollUtil.newArrayList(loyaOrder.getJewelCode())
                        , CollUtil.newArrayList(order.getJewelCode()));
                jewels.forEach(j -> caseService.updateJewelStatus(Long.valueOf(j), AVAILABLE));
                loyaOrder.setJewelCode(order.getJewelCode().toArray(new String[0]));
                updateById(loyaOrder);

                LoyaLogistics logistics = new LoyaLogistics();
                BeanUtils.copyBeanProp(logistics, order);
                logistics.setOrderId(loyaOrder.getOrderId());
                logistics.setDirection("trip");
                logistics.setJewelIds(order.getJewelCode().toArray(new String[0]));
                logisticsService.save(logistics);

                OrderProcessStrategy strategy = orderProcessStrategyFactory.getStrategy(loyaOrder.getOrderStatus());
                return strategy.process(loyaOrder);

            } else {
                LoyaOrder loyaOrder = getById(order.getOrderId());
                //还原首饰预定状态
                Collection<String> jewels = CollUtil.subtract(CollUtil.newArrayList(loyaOrder.getJewelCode())
                        , CollUtil.newArrayList(order.getJewelCode()));
                jewels.forEach(j -> caseService.updateJewelStatus(Long.valueOf(j), AVAILABLE));
                loyaOrder.setJewelCode(order.getJewelCode().toArray(new String[0]));
                return updateById(loyaOrder);
            }
        }
    }

    /**
     * @param order
     * @return
     */
    @Override
    public Boolean updateOrder(LoyaOrder order) {
        OrderProcessStrategy strategy = orderProcessStrategyFactory.getStrategy(order.getOrderStatus());
        strategy.process(order);
        return updateById(order);
    }

    /**
     * @param order
     * @return
     */
    @Transactional
    @Override
    public Boolean closeOrder(Long[] orderIds) {
        List<Long> ids = Arrays.asList(orderIds);
        List<LoyaOrder> loyaOrders = list(Wrappers.lambdaQuery(LoyaOrder.class).in(LoyaOrder::getOrderId, ids));
        loyaOrders.stream().flatMap(item -> Arrays.stream(item.getJewelCode()))
                .map(Long::valueOf).forEach(id -> caseService.updateJewelStatus(id, AVAILABLE));
        List<LoyaOrder> orders = loyaOrders.stream().peek(item -> item.setOrderStatus(OrderStatus.CLOSE)).collect(Collectors.toList());
        return updateBatchById(orders);
    }


}
