package com.autoparams.business.loya.mapper;

import com.autoparams.business.loya.domain.LoyaJewel;
import com.autoparams.business.loya.enums.ReservationStatus;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 首饰管理Mapper接口
 *
 * @author Yeeluc
 * @date 2024-08-09
 */
public interface LoyaJewelCaseMapper extends BaseMapper<LoyaJewel> {
    /**
     * 查询首饰管理
     *
     * @param jewelId 首饰管理主键
     * @return 首饰管理
     */
    public LoyaJewel selectLoyaJewelCaseByJewelId(Long jewelId);
    /**
     * 更新首饰状态
     * @param jewelId 首饰ID
     * @param status 新的状态
     * @return 受影响的行数
     */
    @Update("UPDATE loya_jewel_case SET reservation_status = #{status}, update_time = NOW() WHERE jewel_id = #{jewelId}")
    int updateJewelStatus(@Param("jewelId") Long jewelId, @Param("status") ReservationStatus status);

    /**
     * 查询首饰管理列表
     *
     * @param loyaJewel 首饰管理
     * @return 首饰管理集合
     */
    public List<LoyaJewel> selectLoyaJewelCaseList(LoyaJewel loyaJewel);

    /**
     * 新增首饰管理
     *
     * @param loyaJewel 首饰管理
     * @return 结果
     */
    public int insertLoyaJewelCase(LoyaJewel loyaJewel);

    /**
     * 修改首饰管理
     *
     * @param loyaJewel 首饰管理
     * @return 结果
     */
    public int updateLoyaJewelCase(LoyaJewel loyaJewel);

    /**
     * 删除首饰管理
     *
     * @param jewelId 首饰管理主键
     * @return 结果
     */
    public int deleteLoyaJewelCaseByJewelId(Long jewelId);

    /**
     * 批量删除首饰管理
     *
     * @param jewelIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteLoyaJewelCaseByJewelIds(Long[] jewelIds);
}
