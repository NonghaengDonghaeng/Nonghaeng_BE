package tour.nonghaeng.domain.photo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tour.nonghaeng.domain.etc.photo.PhotoType;
import tour.nonghaeng.domain.photo.repo.RoomPhotoRepository;
import tour.nonghaeng.domain.room.service.RoomService;
import tour.nonghaeng.domain.s3.AmazonS3Service;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RoomPhotoService {

    private static final PhotoType PHOTO_TYPE = PhotoType.ROOM;

    private final RoomPhotoRepository roomPhotoRepository;

    private final RoomService roomService;
    private final AmazonS3Service amazonS3Service;

}
