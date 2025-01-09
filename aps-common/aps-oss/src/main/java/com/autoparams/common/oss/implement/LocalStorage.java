package com.autoparams.common.oss.implement;

import com.autoparams.common.oss.ObjectStorageService;
import com.autoparams.common.oss.config.LocalStorageConfig;

import java.io.*;

public class LocalStorage implements ObjectStorageService {
    private final String storagePath;

    public LocalStorage(LocalStorageConfig localStorageConfig) {
        this.storagePath = localStorageConfig.getStoragePath();
    }

    @Override
    public void upload(String fileName, InputStream inputStream) {
        File file = new File(storagePath, fileName);
        try (FileOutputStream out = new FileOutputStream(file)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public InputStream download(String fileName) {
        try {
            File file = new File(storagePath, fileName);
            return new FileInputStream(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(String fileName) {
        File file = new File(storagePath, fileName);
        if (file.exists()) {
            file.delete();
        }
    }

    @Override
    public String getFileUrl(String fileName) {
        File file = new File(storagePath, fileName);
        if (file.exists()) {
            return file.toURI().toString();
        }
        return null;
    }

    @Override
    public boolean fileExists(String fileName) {
        File file = new File(storagePath, fileName);
        return file.exists();
    }
}
