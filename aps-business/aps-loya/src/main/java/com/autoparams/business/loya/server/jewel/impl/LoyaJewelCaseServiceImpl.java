package com.autoparams.business.loya.server.jewel.impl;

import com.autoparams.business.loya.domain.LoyaJewel;
import com.autoparams.business.loya.enums.ReservationStatus;
import com.autoparams.business.loya.mapper.LoyaJewelCaseMapper;
import com.autoparams.business.loya.server.jewel.ILoyaJewelCaseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.stereotype.Service;

/**
 * 首饰管理Service业务层处理
 *
 * @author Yeeluc
 * @date 2024-08-09
 */
@Service
public class LoyaJewelCaseServiceImpl extends ServiceImpl<LoyaJewelCaseMapper, LoyaJewel> implements ILoyaJewelCaseService {

    /**
     * 更新首饰状态
     *
     * @param jewelId 首饰ID
     * @param status  新的状态
     * @return 受影响的行数
     */
    @Override
    public int updateJewelStatus(Long jewelId, ReservationStatus status) {
        return baseMapper.updateJewelStatus(jewelId, status);
    }
}
