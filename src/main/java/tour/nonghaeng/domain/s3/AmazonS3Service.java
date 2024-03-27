package tour.nonghaeng.domain.s3;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import tour.nonghaeng.domain.etc.photo.PhotoType;
import tour.nonghaeng.global.exception.S3Exception;
import tour.nonghaeng.global.exception.code.S3ErrorCode;
import tour.nonghaeng.global.validation.s3.AmazonS3Validator;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AmazonS3Service {

    private final S3Client s3Client;

    private final AmazonS3Validator amazonS3Validator;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;

    public String uploadImage(PhotoType photoType, MultipartFile image) {

        if (image.isEmpty() || Objects.isNull(image.getOriginalFilename())) {
            throw new S3Exception(S3ErrorCode.DEFAULT_S3_ERROR);
        }
        String key = photoType.getFolderName()+createFileName(image);
        try {

            PutObjectRequest putOb = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
//                    .acl(ObjectCannedACL.BUCKET_OWNER_FULL_CONTROL)
                    .contentType(image.getContentType())
                    .contentLength(image.getSize())
                    .build();

            s3Client.putObject(putOb, RequestBody.fromBytes(image.getBytes()));

        } catch (IOException ie) {
            log.error("파일을 읽어들이는데 에러가 발생했습니다.");
            log.error(ie.getMessage());
            throw new RuntimeException(ie.getMessage());
        }catch (IllegalStateException se){
            log.error("AWS와 통신에 문제가 발생했습니다.");
            log.error(se.getMessage());
            throw new RuntimeException(se.getMessage());
        }

        return getUrl(bucket, key);
    }

    private String getUrl(String bucket, String key) {

        GetUrlRequest request = GetUrlRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();

        return s3Client.utilities().getUrl(request).toString();
    }

    public void deleteImage(PhotoType photoType,String imgUrl) {

        //키가 존재하지 않으면 오류 발생

        String imgKey = photoType.getFolderName()+extractS3KeyFromImgUrl(imgUrl);
        log.info("imgKey : {}", imgKey);


        DeleteObjectRequest request = DeleteObjectRequest.builder()
                .key(imgKey)
                .bucket(bucket)
                .build();
        log.info(request.key());
        s3Client.deleteObject(request);
    }

    //파일 이름 중복 방지를 위한 파일이름 생성 함수
    private String createFileName(MultipartFile image) {

        String originalFilename = image.getOriginalFilename();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        log.info(fileExtension);
        amazonS3Validator.checkExtensionValidate(fileExtension);

        return "image_" + new Date().getTime() + "_"
                + UUID.randomUUID().toString().concat(fileExtension);
    }

    //url 에서 키 추출 함수
    private String extractS3KeyFromImgUrl(String imgUrl) {

        int lastSlashIndex = imgUrl.lastIndexOf("/");

        if (lastSlashIndex != -1 && (lastSlashIndex < imgUrl.length() - 1)) {
            String s3Key = imgUrl.substring(lastSlashIndex + 1);

            return URLDecoder.decode(s3Key, StandardCharsets.UTF_8);
        }else{

            throw new S3Exception(S3ErrorCode.NOT_VALIDATE_IMAGE_URL);
        }
    }

}
