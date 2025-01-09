package com.autoparams.common.oss;

import java.io.InputStream;

public interface ObjectStorageService {
    // 上传文件
    void upload(String fileName, InputStream inputStream);

    // 下载文件
    InputStream download(String fileName);

    // 删除文件
    void delete(String fileName);

    // 获取文件URL
    String getFileUrl(String fileName);

    // 检查文件是否存在
    boolean fileExists(String fileName);
}
