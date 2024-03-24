package tour.nonghaeng.domain.s3;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import tour.nonghaeng.global.exception.S3Exception;
import tour.nonghaeng.global.exception.code.S3ErrorCode;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AmazonS3Service {

    private final S3Client s3Client;

    @Value("${spring.cloud.s3.bucket}")
    private String bucket;

    private void uploadFile(MultipartFile image) {

        PutObjectRequest putOb = PutObjectRequest.builder()
                .bucket(bucket)
                .key(createFileName(image))
                .acl(ObjectCannedACL.BUCKET_OWNER_FULL_CONTROL)
                .contentType(image.getContentType())
                .contentLength(image.getSize())
                .build();


        s3Client.putObject(putOb, RequestBody.fromFile(convertToFile(image)));

    }

    private String createFileName(MultipartFile image) {

        String originalFilename = image.getOriginalFilename();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        return "imagine_" + new Date().getTime() + "_"
                + UUID.randomUUID().toString().concat(fileExtension);
    }

    private File convertToFile(MultipartFile multipartFile){
        try {
            File file = new File(multipartFile.getOriginalFilename());
            multipartFile.transferTo(file);
            return file;
        } catch (IOException e) {
            throw new S3Exception(S3ErrorCode.CONVERT_TO_FILE_FROM_MULTI_PART_FILE);
        }

    }
}
