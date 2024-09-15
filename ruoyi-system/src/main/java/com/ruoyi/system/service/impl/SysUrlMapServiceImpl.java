package com.ruoyi.system.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.sign.Md5Utils;
import com.ruoyi.system.service.ISysUrlMapService;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SysUrlMapMapper;
import com.ruoyi.system.domain.SysUrlMap;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * 短链映射Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-09-15
 */
@Service
public class SysUrlMapServiceImpl extends ServiceImpl<SysUrlMapMapper, SysUrlMap>
        implements ISysUrlMapService
{
    @Override
    public Boolean saveAndShortenUrl(SysUrlMap mapping) throws NoSuchAlgorithmException {
        shortenUrl(mapping.getLongUrl());
        return true;
    }

    /**
     * 将给定的 URL 转换为短链接并存储。
     *
     * @param longUrl 需要被缩短的长 URL
     * @return 生成的短 URL
     * @throws NoSuchAlgorithmException 如果指定的加密算法不存在
     */
    @Override
    public String shortenUrl(String longUrl) throws NoSuchAlgorithmException {
        String sha256hex = hashUrl(longUrl);
        String shortUrl = Md5Utils.hash(Base64.getUrlEncoder().encodeToString(sha256hex.getBytes())).substring(0, 8);

        SysUrlMap urlMap = new SysUrlMap();
        urlMap.setShortUrl(shortUrl);
        urlMap.setLongUrl(longUrl);
        urlMap.setCreateTime(DateUtils.getNowDate());
        save(urlMap);

        return shortUrl;
    }

    /**
     * 根据短链接查询长链接。
     *
     * @param shortUrl 生成的短链接
     * @return 原始的长 URL
     */
    @Override
    public String getOriginalUrl(String shortUrl) {
        SysUrlMap urlMap = getById(shortUrl);
        return urlMap.getLongUrl();
    }

    private String hashUrl(String url) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(url.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(hash);
    }

    private String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
