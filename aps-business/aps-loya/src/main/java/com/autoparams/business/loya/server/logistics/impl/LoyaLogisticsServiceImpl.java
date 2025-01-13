package com.autoparams.business.loya.server.logistics.impl;

import com.autoparams.business.loya.domain.LoyaLogistics;
import com.autoparams.business.loya.mapper.LoyaLogisticsMapper;
import com.autoparams.business.loya.server.logistics.ILoyaLogisticsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.stereotype.Service;

/**
 * 物流管理Service业务层处理
 *
 * @author Yelucc
 * @date 2024-08-10
 */
@Service
public class LoyaLogisticsServiceImpl extends ServiceImpl<LoyaLogisticsMapper, LoyaLogistics> implements ILoyaLogisticsService {
}
