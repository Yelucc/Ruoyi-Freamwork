package com.ruoyi.kuihua.service.impl;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
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
import org.springframework.web.multipart.MultipartFile;

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

    @Transactional
    @Override
    public Boolean changeRecordStatus(KhScoreRecord newRecord) {
        KhScoreRecord oldRecord = getById(newRecord.getRecordId());
        if (Objects.equals(oldRecord.getStatus(), newRecord.getStatus())) {
            return true;
        }
        if ("Invalid".equals(newRecord.getStatus())) {
            KhTeam khTeam = khTeamService.getById(newRecord.getTeamId());
            khTeam.setTeamAllScore(khTeam.getTeamAllScore() - oldRecord.getScore());
            oldRecord.setStatus(newRecord.getStatus());

            return khTeamService.updateById(khTeam) && updateById(oldRecord);
        }
        if ("Normal".equals(newRecord.getStatus())) {
            KhTeam khTeam = khTeamService.getById(newRecord.getTeamId());
            khTeam.setTeamAllScore(khTeam.getTeamAllScore() + oldRecord.getScore());
            oldRecord.setStatus(newRecord.getStatus());
            return khTeamService.updateById(khTeam) && updateById(oldRecord);
        }
        return false;
    }

    /**
     * (移动端) 提交种草记录
     */
    @Transactional
    @Override
    public Boolean submitShareRecord(String sharedLink, MultipartFile[] sharedPicture) throws NoSuchAlgorithmException, IOException {
        KhUser khUser = khUserService.getOne(Wrappers.lambdaQuery(KhUser.class)
                .eq(KhUser::getSysUserId, SecurityUtils.getLoginUser().getUserId()));
        long count = count(Wrappers.lambdaQuery(KhScoreRecord.class)
                .eq(KhScoreRecord::getUserId, khUser.getUserId())
                .eq(KhScoreRecord::getStatus, "Normal")
        );
        KhScoreRecord shareRecord = new KhScoreRecord();

        KhTeam khTeam = khTeamService.getOne(Wrappers.lambdaQuery(KhTeam.class)
                .eq(KhTeam::getTeamId, khUser.getTeamId()));
        shareRecord.setNickName(khUser.getNickName());
        shareRecord.setUserId(khUser.getUserId());
        shareRecord.setTeamId(khTeam.getTeamId());
        shareRecord.setTeamName(khTeam.getTeamName());
        shareRecord.setSharedLink(sharedLink);
        String[] imgs = new String[sharedPicture.length];
        for (int i = 0, sharedPictureLength = sharedPicture.length; i < sharedPictureLength; i++) {
            MultipartFile multipartFile = sharedPicture[i];
            try {
                imgs[i] = FileUploadUtils.uploadMinio(multipartFile);
            } catch (IOException e) {
                throw e;
            }
        }
        shareRecord.setSharedPicture(imgs);
        if (count == 0) {
            shareRecord.setScore(3L);
        } else if (count == 1) {
            shareRecord.setScore(2L);
        } else {
            shareRecord.setScore(1L);
        }


        khTeam.addScore(shareRecord.getScore());
        khTeamService.updateById(khTeam);

//        String shortenUrl = urlMapService.shortenUrl(shareRecord.getSharedLink());
//        shareRecord.setSharedLink("/common/r/" + shortenUrl);

        return save(shareRecord);
    }


}
