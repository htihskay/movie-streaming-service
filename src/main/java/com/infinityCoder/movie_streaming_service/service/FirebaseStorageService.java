package com.infinityCoder.movie_streaming_service.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class FirebaseStorageService {

    private final Storage storage;
    private final String bucketName; // Name of your bucket

    public FirebaseStorageService() throws IOException {
        ClassPathResource resource = new ClassPathResource("streamingapp-56ff3-firebase-adminsdk-aebof-aed9928ecf.json");
        GoogleCredentials credentials = GoogleCredentials.fromStream(resource.getInputStream());
        this.storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        this.bucketName = "streamingapp-56ff3.appspot.com"; // replace with your bucket name

    }

    public byte[] downloadFile(String filePath) throws IOException {
        Blob blob = storage.get(bucketName, filePath);
        if (blob == null) {
            throw new IOException("File not found in bucket: " + filePath);
        }
        return blob.getContent();
    }
}

