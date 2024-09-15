package com.ruoyi.kuihua.service;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import com.ruoyi.kuihua.domain.KhScoreRecord;
import com.baomidou.mybatisplus.extension.service.IService;
/**
 * 葵花分数记录Service接口
 *
 * @author ruoyi
 * @date 2024-09-15
 */
public interface KhScoreRecordService extends IService<KhScoreRecord>
{
    /**
     * (移动端) 提交种草记录
     */
    Boolean submitShareRecord(KhScoreRecord shareRecord) throws NoSuchAlgorithmException;
}
