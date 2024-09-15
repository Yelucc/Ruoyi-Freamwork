package com.ruoyi.kuihua.service.impl;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.kuihua.domain.KhLeaderBoardVo;
import com.ruoyi.kuihua.domain.KhTeam;
import com.ruoyi.kuihua.domain.KhUser;
import com.ruoyi.kuihua.service.KhTeamService;
import com.ruoyi.kuihua.service.KhUserService;
import com.ruoyi.system.service.ISysUrlMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.kuihua.mapper.KhScoreRecordMapper;
import com.ruoyi.kuihua.domain.KhScoreRecord;
import com.ruoyi.kuihua.service.KhScoreRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

/**
 * 葵花分数记录Service业务层处理
 *
 * @author ruoyi
 * @date 2024-09-15
 */
@Service
public class KhScoreRecordServiceImpl extends ServiceImpl<KhScoreRecordMapper, KhScoreRecord>
        implements KhScoreRecordService {

    @Autowired
    private ISysUrlMapService urlMapService;
    @Autowired
    private KhUserService khUserService;
    @Autowired
    private KhTeamService khTeamService;

    /**
     * (移动端) 提交种草记录
     *
     * @param shareRecord
     */
    @Transactional
    @Override
    public Boolean submitShareRecord(KhScoreRecord shareRecord) throws NoSuchAlgorithmException {
        long count = count(Wrappers.lambdaQuery(KhScoreRecord.class)
                .eq(KhScoreRecord::getUserId, SecurityUtils.getLoginUser().getUserId())
                .eq(KhScoreRecord::getStatus, "Normal")
        );
        shareRecord.setUserId(SecurityUtils.getLoginUser().getUserId());
        if (count == 0) {
            shareRecord.setScore(3L);
        } else if (count == 1) {
            shareRecord.setScore(2L);
        } else {
            shareRecord.setScore(1L);
        }

        KhUser khUser = khUserService.getOne(Wrappers.lambdaQuery(KhUser.class)
                .eq(KhUser::getSysUserId, SecurityUtils.getLoginUser().getUserId()));
        KhTeam khTeam = khTeamService.getOne(Wrappers.lambdaQuery(KhTeam.class)
                .eq(KhTeam::getTeamId, khUser.getTeamId()));
        khTeam.addScore(shareRecord.getScore());
        khTeamService.updateById(khTeam);

        String shortenUrl = urlMapService.shortenUrl(shareRecord.getSharedLink());
        shareRecord.setSharedLink("/common/r/" + shortenUrl);

        return save(shareRecord);
    }




}
