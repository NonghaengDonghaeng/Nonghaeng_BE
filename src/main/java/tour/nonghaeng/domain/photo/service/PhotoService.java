package tour.nonghaeng.domain.photo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tour.nonghaeng.domain.experience.service.ExperienceService;
import tour.nonghaeng.domain.room.service.RoomService;
import tour.nonghaeng.domain.s3.AmazonS3Service;
import tour.nonghaeng.domain.tour.service.TourService;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PhotoService {

    private final AmazonS3Service amazonS3Service;
    private final TourService tourService;
    private final ExperienceService experienceService;
    private final RoomService roomService;

//    public Long upload(Seller seller, MultipartFile imageFile, String type) {
//    }
}
