package com.infinityCoder.movie_streaming_service.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;

public class FirebaseConfig {

    String serviceAccountUrl="streamingapp-56ff3-firebase-adminsdk-aebof-15f3374194.json";
    @PostConstruct
    public void initialize(){
        try{
            FileInputStream serviceAccount=new FileInputStream(serviceAccountUrl);

            FirebaseOptions options=FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setStorageBucket("streamingapp-56ff3.appspot.com")
                    .build();
            FirebaseApp.initializeApp();
        }
        catch (IOException io){
            io.printStackTrace();
        }
    }
}
