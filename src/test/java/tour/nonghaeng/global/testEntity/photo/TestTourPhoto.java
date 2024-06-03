package tour.nonghaeng.global.testEntity.photo;

import tour.nonghaeng.domain.photo.data.TourPhoto;
import tour.nonghaeng.domain.tour.data.Tour;

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
