package tour.nonghaeng.domain.photo.s3.valid;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.photo.s3.exception.S3Exception;
import tour.nonghaeng.domain.photo.s3.exception.error.S3ErrorCode;

@Component
@Slf4j
public class AmazonS3Validator {

    public void checkExtensionValidate(String fileExtension) {

        if (!(fileExtension.equals(".jpeg") || fileExtension.equals(".jpg")
                || fileExtension.equals(".png") || fileExtension.equals(".gif"))) {

            throw new S3Exception(S3ErrorCode.NOT_SUPPORT_EXTENSION);
        }
    }




}
