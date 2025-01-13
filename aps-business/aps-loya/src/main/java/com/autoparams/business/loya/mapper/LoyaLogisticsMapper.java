package com.autoparams.business.loya.mapper;

import com.autoparams.business.loya.domain.LoyaLogistics;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * 物流管理Mapper接口
 *
 * @author Yelucc
 * @date 2024-08-10
 */
public interface LoyaLogisticsMapper extends BaseMapper<LoyaLogistics> {
    /**
     * 查询物流管理
     *
     * @param trackingNumber 物流管理主键
     * @return 物流管理
     */
    public LoyaLogistics selectLoyaLogisticsByTrackingNumber(String trackingNumber);

    /**
     * 查询物流管理列表
     *
     * @param loyaLogistics 物流管理
     * @return 物流管理集合
     */
    public List<LoyaLogistics> selectLoyaLogisticsList(LoyaLogistics loyaLogistics);

    /**
     * 新增物流管理
     *
     * @param loyaLogistics 物流管理
     * @return 结果
     */
    public int insertLoyaLogistics(LoyaLogistics loyaLogistics);

    /**
     * 修改物流管理
     *
     * @param loyaLogistics 物流管理
     * @return 结果
     */
    public int updateLoyaLogistics(LoyaLogistics loyaLogistics);

    /**
     * 删除物流管理
     *
     * @param trackingNumber 物流管理主键
     * @return 结果
     */
    public int deleteLoyaLogisticsByTrackingNumber(String trackingNumber);

    /**
     * 批量删除物流管理
     *
     * @param trackingNumbers 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteLoyaLogisticsByTrackingNumbers(String[] trackingNumbers);
}
