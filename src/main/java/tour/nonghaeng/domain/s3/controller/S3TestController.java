package tour.nonghaeng.domain.s3.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tour.nonghaeng.domain.s3.AmazonS3Service;

@RestController
@RequestMapping("/s3")
@RequiredArgsConstructor
@Slf4j
public class S3TestController {

    private final AmazonS3Service amazonS3Service;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestPart(value = "image", required = false) MultipartFile image,
                                              @RequestPart("name") String name) {


        String imageUrl = amazonS3Service.uploadImage(image);

        return new ResponseEntity<>(imageUrl, HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<Void> deleteImage(@RequestBody String imgUrl) {

        amazonS3Service.deleteImage(imgUrl);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
