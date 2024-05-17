package tour.nonghaeng.domain.photo.imageServer.service;

import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.errors.*;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tour.nonghaeng.domain.etc.enums.photo.PhotoType;
import tour.nonghaeng.domain.photo.imageServer.exception.ImageServerException;
import tour.nonghaeng.domain.photo.imageServer.exception.error.ImageServerErrorCode;
import tour.nonghaeng.domain.photo.imageServer.valid.ImageServerValidator;

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
@Profile("oracle-minio")
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MinioService implements ImageService {

    private final MinioClient minioClient;

    private final ImageServerValidator imageServerValidator;

    @Value("${spring.cloud.minio.s3.bucket}")
    private String bucket;



    @Override
    public String uploadImage(PhotoType photoType, MultipartFile image) {

        if (image.isEmpty() || Objects.isNull(image.getOriginalFilename())) {
            throw new ImageServerException(ImageServerErrorCode.DEFAULT_S3_ERROR);
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

    private String createFileName(MultipartFile image) {

        String originalFilename = image.getOriginalFilename();
        assert originalFilename != null;
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        log.info(fileExtension);
        imageServerValidator.checkExtensionValidate(fileExtension);

        return "image_" + new Date().getTime() + "_"
                + UUID.randomUUID().toString().concat(fileExtension);
    }

    private String getUrl(String bucket, String key) {

        try {

            GetPresignedObjectUrlArgs args = GetPresignedObjectUrlArgs.builder()
                    .method(Method.PUT)
                    .bucket(bucket)
                    .object(key)
                    .expiry(1, TimeUnit.DAYS)
                    .build();

            String longUrl = minioClient.getPresignedObjectUrl(args);

            return longUrl.split("\\?")[0];

        }catch (ServerException | InsufficientDataException | ErrorResponseException | IOException |
                NoSuchAlgorithmException | InvalidKeyException | InvalidResponseException | XmlParserException |
                InternalException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteImage(PhotoType photoType,String imgUrl) {

        //키가 존재하지 않으면 오류 발생

        String imgKey = photoType.getFolderName()+extractKeyFromImgUrl(imgUrl);
        log.info("imgKey : {}", imgKey);

        try {
            RemoveObjectArgs args = RemoveObjectArgs.builder()
                    .bucket(bucket)
                    .object(imgKey)
                    .build();

            minioClient.removeObject(args);
        } catch (ServerException | InsufficientDataException | ErrorResponseException | IOException |
                 NoSuchAlgorithmException | InvalidKeyException | InvalidResponseException | XmlParserException |
                 InternalException e) {
            throw new RuntimeException(e);
        }
    }

    private String extractKeyFromImgUrl(String imgUrl) {

        int lastSlashIndex = imgUrl.lastIndexOf("/");

        if (lastSlashIndex != -1 && (lastSlashIndex < imgUrl.length() - 1)) {
            String s3Key = imgUrl.substring(lastSlashIndex + 1);

            return URLDecoder.decode(s3Key, StandardCharsets.UTF_8);
        }else{

            throw new ImageServerException(ImageServerErrorCode.NOT_VALIDATE_IMAGE_URL);
        }
    }
}
