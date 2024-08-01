package com.infinityCoder.movie_streaming_service.controller;

import com.infinityCoder.movie_streaming_service.service.FirebaseStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@RestController
public class MovieStreamController {

    private static final Logger log = LoggerFactory.getLogger(MovieStreamController.class);

    @Autowired
    private FirebaseStorageService firebaseStorageService;

    @Autowired
    private MovieCatelogService movieCatelogService;

    @GetMapping("/stream/{videoPath}")
    public ResponseEntity<InputStreamResource> streamVideo(@PathVariable String videoPath) {
        try {
            byte[] videoData = firebaseStorageService.downloadFile(videoPath);
            if (videoData == null || videoData.length == 0) {
                return ResponseEntity.notFound().build();
            }
            InputStreamResource inputStreamResource = new InputStreamResource(new ByteArrayInputStream(videoData));
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("video/mp4"))
                    .body(inputStreamResource);
        } catch (IOException e) {
            log.error("Error fetching video from Firebase Storage", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/stream/with-id/{videoInfoId}")
    public ResponseEntity<InputStreamResource> streamVideoById(@PathVariable Long videoInfoId) {
        try {
            String moviePath = movieCatelogService.getMoviePath(videoInfoId);
            if (moviePath == null || moviePath.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            log.info("Resolved movie path: {}", moviePath);
            return streamVideo(moviePath);
        } catch (Exception e) {
            log.error("Error resolving video path by ID", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
