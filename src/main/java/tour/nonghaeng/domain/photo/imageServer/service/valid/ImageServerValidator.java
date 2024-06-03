package tour.nonghaeng.domain.photo.imageServer.service.valid;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.photo.imageServer.exception.ImageServerErrorCode;
import tour.nonghaeng.domain.photo.imageServer.exception.ImageServerException;

@Component
@Slf4j
public class ImageServerValidator {

    public void checkExtensionValidate(String fileExtension) {

        if (!(fileExtension.equals(".jpeg") || fileExtension.equals(".jpg")
                || fileExtension.equals(".png") || fileExtension.equals(".gif"))) {

            throw new ImageServerException(ImageServerErrorCode.NOT_SUPPORT_EXTENSION);
        }
    }




}
