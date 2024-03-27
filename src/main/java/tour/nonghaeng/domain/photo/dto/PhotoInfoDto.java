package tour.nonghaeng.domain.photo.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tour.nonghaeng.domain.photo.entity.Photo;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PhotoInfoDto {
    private Long photoId;
    private String imgUrl;
    private boolean representative;

    @Builder
    private PhotoInfoDto(Long photoId, String imgUrl, boolean representative) {
        this.photoId = photoId;
        this.imgUrl = imgUrl;
        this.representative = representative;
    }

    public static List<PhotoInfoDto> toDto(List<Photo> photos) {
        return photos.stream().map(photo ->
            PhotoInfoDto.builder()
                    .photoId(photo.getId())
                    .imgUrl(photo.getImgUrl())
                    .representative(photo.isRepresentative())
                    .build()
        ).toList();
    }
}
