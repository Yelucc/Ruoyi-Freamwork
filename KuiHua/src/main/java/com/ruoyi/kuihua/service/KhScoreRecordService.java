package com.ruoyi.kuihua.service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import com.ruoyi.kuihua.domain.KhLeaderBoardVo;
import com.ruoyi.kuihua.domain.KhScoreRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * 葵花分数记录Service接口
 *
 * @author ruoyi
 * @date 2024-09-15
 */
public interface KhScoreRecordService extends IService<KhScoreRecord>
{
    Boolean changeRecordStatus(KhScoreRecord newRecord);

    /**
     * (移动端) 提交种草记录
     */
    @Transactional
    Boolean submitShareRecord(String sharedLink, MultipartFile[] sharedPicture) throws NoSuchAlgorithmException, IOException;
}
