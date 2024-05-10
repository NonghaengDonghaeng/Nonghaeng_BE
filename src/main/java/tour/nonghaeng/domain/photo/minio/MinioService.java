package tour.nonghaeng.domain.photo.minio;

import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import tour.nonghaeng.domain.etc.photo.PhotoType;
import tour.nonghaeng.domain.photo.s3.exception.S3Exception;
import tour.nonghaeng.domain.photo.s3.exception.error.S3ErrorCode;
import tour.nonghaeng.domain.photo.s3.valid.AmazonS3Validator;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MinioService {

    private final MinioClient minioClient;

    private final AmazonS3Validator amazonS3Validator;

    @Value("${spring.cloud.minio.s3.bucket}")
    private String bucket;

    public String uploadImage(PhotoType photoType, MultipartFile image) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {

        if (image.isEmpty() || Objects.isNull(image.getOriginalFilename())) {
            throw new S3Exception(S3ErrorCode.DEFAULT_S3_ERROR);
        }
        String key = photoType.getFolderName()+createFileName(image);
        try {

            PutObjectArgs putObArgs = PutObjectArgs.builder()
                    .bucket(bucket)
                    .contentType(image.getContentType())
                    .object(key)
                    .stream(image.getInputStream(), image.getSize(), -1)
                    .build();

            minioClient.putObject(putObArgs);


        } catch (IOException ie) {
            log.error("파일을 읽어들이는데 에러가 발생했습니다.");
            log.error(ie.getMessage());
            throw new RuntimeException(ie.getMessage());
        } catch (ServerException | InsufficientDataException | ErrorResponseException | NoSuchAlgorithmException |
                 InvalidKeyException | InvalidResponseException | XmlParserException | InternalException e) {
            throw new RuntimeException(e);
        }

        return getUrl(bucket, key);
    }

    private String getUrl(String bucket, String key) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {

        String url =
                minioClient.getPresignedObjectUrl(
                        GetPresignedObjectUrlArgs.builder()
                                .method(Method.PUT)
                                .bucket(bucket)
                                .object(key)
                                .expiry(1, TimeUnit.DAYS)
                                .build());
        return url;
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
//        s3Client.deleteObject(request);
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
