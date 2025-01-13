package com.autoparams.business.loya.mapper;

import com.autoparams.business.loya.domain.LoyaOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;


import java.util.List;

/**
 * 订单管理Mapper接口
 *
 * @author ruoyi
 * @date 2024-08-09
 */
public interface LoyaOrderManagementMapper extends BaseMapper<LoyaOrder> {
    /**
     * 查询订单管理
     *
     * @param orderId 订单管理主键
     * @return 订单管理
     */
    public LoyaOrder selectLoyaOrderManagementByOrderId(Long orderId);

    /**
     * 查询订单管理列表
     *
     * @param loyaOrder 订单管理
     * @return 订单管理集合
     */
    public List<LoyaOrder> selectLoyaOrderManagementList(LoyaOrder loyaOrder);

    /**
     * 新增订单管理
     *
     * @param loyaOrder 订单管理
     * @return 结果
     */
    public int insertLoyaOrderManagement(LoyaOrder loyaOrder);

    /**
     * 修改订单管理
     *
     * @param loyaOrder 订单管理
     * @return 结果
     */
    public int updateLoyaOrderManagement(LoyaOrder loyaOrder);

    /**
     * 删除订单管理
     *
     * @param orderId 订单管理主键
     * @return 结果
     */
    public int deleteLoyaOrderManagementByOrderId(Long orderId);

    /**
     * 批量删除订单管理
     *
     * @param orderIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteLoyaOrderManagementByOrderIds(Long[] orderIds);
}
