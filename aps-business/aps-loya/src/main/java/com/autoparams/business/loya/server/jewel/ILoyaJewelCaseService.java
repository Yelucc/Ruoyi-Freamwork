package com.autoparams.business.loya.server.jewel;

import com.autoparams.business.loya.domain.LoyaJewel;
import com.autoparams.business.loya.enums.ReservationStatus;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * 首饰管理Service接口
 *
 * @author Yeeluc
 * @date 2024-08-09
 */
public interface ILoyaJewelCaseService extends IService<LoyaJewel> {
    /**
     * 更新首饰状态
     * @param jewelId 首饰ID
     * @param status 新的状态
     * @return 受影响的行数
     */
    int updateJewelStatus(Long jewelId, ReservationStatus status);
}
