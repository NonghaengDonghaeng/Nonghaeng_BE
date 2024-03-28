package tour.nonghaeng.domain.experience.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tour.nonghaeng.domain.experience.entity.Experience;
import tour.nonghaeng.domain.photo.dto.PhotoInfoDto;

import java.time.LocalDate;
import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ExpDetailDto {

    private String experienceName;
    private String experienceTypeName;
    private LocalDate startDate;
    private LocalDate endDate;
    private int minParticipant;
    private int maxParticipant;
    private int price;
    private int durationHours;
    private String checkPoint;
    private String detailIntroduction;
    private String summary;
    private String supplies;
    private String precautions;
    private TourInfo tourInfo;
    private SellerInfo sellerInfo;
    private List<PhotoInfoDto> photoInfoDtoList;

    @Builder
    public ExpDetailDto(String experienceName, String experienceTypeName, LocalDate startDate, LocalDate endDate, int minParticipant, int maxParticipant, int price, int durationHours, String checkPoint, String detailIntroduction, String summary, String supplies, String precautions, TourInfo tourInfo, SellerInfo sellerInfo, List<PhotoInfoDto> photoInfoDtoList) {
        this.experienceName = experienceName;
        this.experienceTypeName = experienceTypeName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.minParticipant = minParticipant;
        this.maxParticipant = maxParticipant;
        this.price = price;
        this.durationHours = durationHours;
        this.checkPoint = checkPoint;
        this.detailIntroduction = detailIntroduction;
        this.summary = summary;
        this.supplies = supplies;
        this.precautions = precautions;
        this.tourInfo = tourInfo;
        this.sellerInfo = sellerInfo;
        this.photoInfoDtoList = photoInfoDtoList;
    }

    @Builder
    @Getter
    public static class SellerInfo {
        private String address;
        private String callNumber;
    }

    @Builder
    @Getter
    public static class TourInfo {
        private Long tourId;
        private String tourName;
        private String AreaName;
    }

    public static ExpDetailDto toDto(Experience experience) {
        return ExpDetailDto.builder()
                .experienceName(experience.getExperienceName())
                .experienceTypeName(experience.getExperienceType().getName())
                .startDate(experience.getStartDate())
                .endDate(experience.getEndDate())
                .minParticipant(experience.getMinParticipant())
                .maxParticipant(experience.getMaxParticipant())
                .price(experience.getPrice())
                .durationHours(experience.getDurationHours())
                .checkPoint(experience.getCheckPoint())
                .detailIntroduction(experience.getDetailIntroduction())
                .summary(experience.getSummary())
                .supplies(experience.getSupplies())
                .precautions(experience.getPrecautions())
                .tourInfo(TourInfo.builder()
                        .tourId(experience.getTour().getId())
                        .tourName(experience.getTour().getName())
                        .AreaName(experience.getTour().getAreaCode().getAreaName())
                        .build())
                .sellerInfo(SellerInfo.builder()
                        .address(experience.getSeller().getAddress())
                        .callNumber(experience.getSeller().getCallNumber())
                        .build())
                .photoInfoDtoList(experience.getExperiencePhotoList().stream()
                        .map(experiencePhoto -> PhotoInfoDto.toDto(experiencePhoto))
                        .toList())
                .build();
    }



}
