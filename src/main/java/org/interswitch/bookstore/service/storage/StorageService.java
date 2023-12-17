package org.interswitch.bookstore.service.storage;


import org.interswitch.bookstore.dto.AmazonResponse;

import java.io.IOException;
import java.io.InputStream;

public interface StorageService {
    String FILE_SEPARATOR = "/";

    void createBucket(String name);

    boolean doesBucketExist(String name);

    void createFolder(String bucketName, String name, String access);

    String getUrl(String bucketName, String name);

    AmazonResponse uploadToBucket(InputStream inputStream, UploadObject uploadObject) throws IOException;
    void deleteFile(String url);
}
