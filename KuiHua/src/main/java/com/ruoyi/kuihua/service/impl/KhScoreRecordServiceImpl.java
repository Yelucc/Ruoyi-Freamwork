package com.ruoyi.kuihua.service.impl;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.SysUrlMap;
import com.ruoyi.system.service.ISysUrlMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.kuihua.mapper.KhScoreRecordMapper;
import com.ruoyi.kuihua.domain.KhScoreRecord;
import com.ruoyi.kuihua.service.KhScoreRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

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
    /**
     * (移动端) 提交种草记录
     *
     * @param shareRecord
     */
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

        String shortenUrl = urlMapService.shortenUrl(shareRecord.getSharedLink());
        shareRecord.setSharedLink("/common/r/"+shortenUrl);

        return save(shareRecord);
    }


}
