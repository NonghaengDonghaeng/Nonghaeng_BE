package tour.nonghaeng.global.testEntity.photo;

import tour.nonghaeng.domain.photo.entity.TourPhoto;
import tour.nonghaeng.domain.tour.entity.Tour;

public class TestTourPhoto {

    public static TourPhoto makeTestTourPhoto(Tour tour,String imgUrl,boolean representative) {
        TourPhoto tourPhoto = TourPhoto.builder()
                .tour(tour)
                .imgUrl(imgUrl)
                .build();
        if (representative) {
            tourPhoto.onRepresentative();
        }
        return tourPhoto;
    }
}
