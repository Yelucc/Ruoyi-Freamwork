package com.ruoyi.kuihua.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.kuihua.domain.KhTeam;
import com.ruoyi.kuihua.domain.KhUser;
import com.ruoyi.kuihua.service.KhTeamService;
import com.ruoyi.kuihua.service.KhUserService;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.system.service.ISysUrlMapService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class KhScoreRecordServiceImpl extends ServiceImpl<KhScoreRecordMapper, KhScoreRecord>
        implements KhScoreRecordService {

    private static final String TOKEN_URL = "https://cdp.xiaokuihua.net/api/mip-security/auth/mipApp/token/getToken";
    private static final String DATA_URL = "https://cdp.xiaokuihua.net/api/mip-etl-sync/v2/api/receiveData";
    private static final int MAX_DATA_ITEMS = 1000; // 每次请求的最大条数
    private static final long WAIT_TIME_MS = 2000; // 每次请求的等待时间（2秒）
    @Autowired
    private ISysConfigService configService;
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
    public Boolean submitShareRecord(String sharedLink, MultipartFile[] sharedPicture) throws IOException {
        configService.resetConfigCache();
        String config = configService.selectConfigByKey("kuihua.server.enable");

        if (!"true".equals(config)) {
            throw ExceptionUtil.wrapRuntime("活动未开始");
        }

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


    public String getToken(String clientId, String clientSecret) throws Exception {
        URL url = new URL(TOKEN_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("clientId", clientId);
        conn.setRequestProperty("clientSecret", clientSecret);
        conn.setDoOutput(true);

        int responseCode = conn.getResponseCode();
        if (responseCode == 200) {
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            // 解析 JSON 获取 token
            JSONObject jsonResponse = JSONObject.from(response.toString());
            return jsonResponse.getString("data"); // 提取 token
        } else {
            throw new RuntimeException("Failed : HTTP error code : " + responseCode);
        }
    }

    public String sendData(String appToken, String jsonData) throws Exception {
        URL url = new URL(DATA_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", appToken);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            os.write(jsonData.getBytes("UTF-8"));
            os.flush();
        }

        int responseCode = conn.getResponseCode();
        if (responseCode == 200) {
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString(); // 返回完整的响应内容
        } else {
            throw new RuntimeException("Failed : HTTP error code : " + responseCode);
        }
    }

    @Override
    public String put2Cdp(String clientId, String clientSecret) throws Exception {
        // 获取评分记录
        List<KhScoreRecord> scoreRecords = list();

        // 将数据切片并发送
        for (int i = 0; i < scoreRecords.size(); i += MAX_DATA_ITEMS) {
            // 计算当前批次的结束索引
            int end = Math.min(i + MAX_DATA_ITEMS, scoreRecords.size());
            List<KhScoreRecord> batch = scoreRecords.subList(i, end);

            // 获取 Token
            String token = getToken(clientId, clientSecret);

            // 发送数据并返回响应
            String response = sendData(token, JSONObject.toJSONString(batch));
            log.info("Response for batch {}: {}", i / MAX_DATA_ITEMS + 1, response);

            // 等待 2 秒钟
            Thread.sleep(WAIT_TIME_MS);
        }

        return "All batches sent successfully";
    }

}
