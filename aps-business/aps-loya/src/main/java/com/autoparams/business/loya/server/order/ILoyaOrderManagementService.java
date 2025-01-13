package com.autoparams.business.loya.server.order;

import com.autoparams.business.loya.domain.LoyaOrder;
import com.autoparams.business.loya.domain.vo.LoyaOrderReserveVo;
import com.autoparams.business.loya.domain.vo.LoyaOrderVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 订单管理Service接口
 *
 * @author ruoyi
 * @date 2024-08-09
 */
public interface ILoyaOrderManagementService extends IService<LoyaOrder> {
    /**
     * @param order
     * @return
     */
    Page<LoyaOrderVo> listVo(Page page, LoyaOrderVo order);

    /**
     * @param order
     * @return
     */
    Boolean createOrder(LoyaOrderReserveVo order);

    /**
     * @param order
     * @return
     */
    Boolean updateOrder(LoyaOrder order);

    /**
     * @param order
     * @return
     */
    Boolean closeOrder(Long[] orderIds);
}
