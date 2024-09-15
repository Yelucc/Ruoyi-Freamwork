package com.ruoyi.system.service;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import com.ruoyi.system.domain.SysUrlMap;
import com.baomidou.mybatisplus.extension.service.IService;
/**
 * 短链映射Service接口
 * 
 * @author ruoyi
 * @date 2024-09-15
 */
public interface ISysUrlMapService extends IService<SysUrlMap>
{
    Boolean saveAndShortenUrl(SysUrlMap mapping) throws NoSuchAlgorithmException;

    String shortenUrl(String longUrl) throws NoSuchAlgorithmException;

    String getOriginalUrl(String shortUrl);
}
