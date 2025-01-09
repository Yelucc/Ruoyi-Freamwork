package com.autoparams.common.oss.implement;

import com.autoparams.common.oss.ObjectStorageService;
import com.autoparams.common.oss.config.QiniuOSSConfig;
import com.qiniu.common.QiniuException;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

import java.io.InputStream;

public class QiniuOSSStorage implements ObjectStorageService {

    private final UploadManager uploadManager;
    private final BucketManager bucketManager;
    private final String bucketName;
    private final Auth auth;

    public QiniuOSSStorage(QiniuOSSConfig qiniuOSSConfig) {
        Configuration cfg = new Configuration(Region.autoRegion());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
        cfg.useHttpsDomains = false;
        this.auth = Auth.create(qiniuOSSConfig.getAccessKey(), qiniuOSSConfig.getSecretKey());
        this.uploadManager = new UploadManager(cfg);
        this.bucketManager = new BucketManager(auth, cfg);
        this.bucketName = qiniuOSSConfig.getBucketName();
    }

    @Override
    public void upload(String fileName, InputStream inputStream) {
        // 使用 uploadManager 上传文件
        try {
            uploadManager.put(inputStream, fileName, auth.uploadToken(bucketName), null, null);
        } catch (QiniuException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public InputStream download(String fileName) {
        // 根据业务需求返回文件内容
        return null;
    }

    @Override
    public void delete(String fileName) {
        try {
            bucketManager.delete(bucketName, fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getFileUrl(String fileName) {
//        return "http://" + bucketName + "." + qiniuOSSConfig.getDomain() + "/" + fileName;
        return null;
    }

    @Override
    public boolean fileExists(String fileName) {
        try {
            bucketManager.stat(bucketName, fileName);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
