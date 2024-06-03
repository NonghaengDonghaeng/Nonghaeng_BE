package tour.nonghaeng.domain.photo.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tour.nonghaeng.domain.photo.data.Photo;

import java.util.List;
import java.util.Optional;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PhotoInfoDto {

    private static final String DEFAULT_PHOTO_IMG_URL = "https://nonghaeng.s3.ap-northeast-2.amazonaws.com/%E1%84%87%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A1%E1%84%8C%E1%85%B5%E1%86%AB.jpeg";

    private Long photoId;
    private String imgUrl;
    private boolean representative;

    @Builder
    private PhotoInfoDto(Long photoId, String imgUrl, boolean representative) {
        this.photoId = photoId;
        this.imgUrl = imgUrl;
        this.representative = representative;
    }

    public static List<PhotoInfoDto> toDtoList(List<Photo> photos) {
        return photos.stream().map(photo ->
                PhotoInfoDto.builder()
                        .photoId(photo.getId())
                        .imgUrl(photo.getImgUrl())
                        .representative(photo.isRepresentative())
                        .build()
        ).toList();
    }

    public static PhotoInfoDto toDto(Optional<Photo> photo) {

        if (photo.isEmpty()) {
            return PhotoInfoDto.builder()
                    .imgUrl(DEFAULT_PHOTO_IMG_URL)
                    .representative(false)
                    .build();
        }

        return PhotoInfoDto.builder()
                .photoId(photo.get().getId())
                .imgUrl(photo.get().getImgUrl())
                .representative(photo.get().isRepresentative())
                .build();
    }

    public static PhotoInfoDto toDto(Photo photo) {

        return PhotoInfoDto.builder()
                .photoId(photo.getId())
                .imgUrl(photo.getImgUrl())
                .representative(photo.isRepresentative())
                .build();
    }
}
