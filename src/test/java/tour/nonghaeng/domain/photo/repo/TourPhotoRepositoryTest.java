package tour.nonghaeng.domain.photo.repo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.member.repo.SellerRepository;
import tour.nonghaeng.domain.photo.entity.TourPhoto;
import tour.nonghaeng.domain.tour.entity.Tour;
import tour.nonghaeng.domain.tour.repo.TourRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static tour.nonghaeng.global.testEntity.photo.TestTourPhoto.makeTestTourPhoto;
import static tour.nonghaeng.global.testEntity.seller.TestSeller.makeTestSeller;
import static tour.nonghaeng.global.testEntity.tour.TestTour.makeTestTour;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "/application-data.properties")
class TourPhotoRepositoryTest {

    @Autowired
    private TourPhotoRepository tourPhotoRepository;

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private TourRepository tourRepository;

    private static Seller seller;
    private static Tour tour;

    @BeforeEach
    void setUp() {
        seller = makeTestSeller();
        sellerRepository.save(seller);

        tour = makeTestTour(seller);
        tourRepository.save(tour);
    }

    @Test
    void existsById() {
    }

    @Nested
    @DisplayName("hasExactlyOneRepresentativePhoto() 테스트")
    class hasExactlyOneRepresentativePhoto {

        @Test
        @DisplayName("대표 사진이 1개가 아닐때")
        void hasExactlyOneRepresentativePhoto1() {
            //given
            TourPhoto tourPhoto1 = makeTestTourPhoto(tour, "123", true);
            TourPhoto tourPhoto2 = makeTestTourPhoto(tour, "513", false);
            TourPhoto tourPhoto3 = makeTestTourPhoto(tour, "5113", true);
            tourPhotoRepository.save(tourPhoto1);
            tourPhotoRepository.save(tourPhoto2);
            tourPhotoRepository.save(tourPhoto3);
            //when
            boolean result = tourPhotoRepository.hasExactlyOneRepresentativePhoto(tour);
            //then
            assertThat(result).isFalse();
        }

        @Test
        @DisplayName("대표 사진이 1개일때")
        void hasExactlyOneRepresentativePhoto2() {
            //given
            TourPhoto tourPhoto1 = makeTestTourPhoto(tour, "123", true);
            TourPhoto tourPhoto2 = makeTestTourPhoto(tour, "513", false);
            TourPhoto tourPhoto3 = makeTestTourPhoto(tour, "5113", false);
            tourPhotoRepository.save(tourPhoto1);
            tourPhotoRepository.save(tourPhoto2);
            tourPhotoRepository.save(tourPhoto3);
            //when
            boolean result = tourPhotoRepository.hasExactlyOneRepresentativePhoto(tour);
            //then
            assertThat(result).isTrue();
        }
    }

    @Nested
    @DisplayName("findRepresentativePhotoId() 테스트")
    class findRepresentativePhotoId {

        @Test
        @DisplayName("대표사진이 1개라서 존재할때")
        void findRepresentativePhotoId1() {
            //given
            TourPhoto tourPhoto1 = makeTestTourPhoto(tour, "123", true);
            TourPhoto tourPhoto2 = makeTestTourPhoto(tour, "513", false);
            TourPhoto tourPhoto3 = makeTestTourPhoto(tour, "5113", false);
            tourPhotoRepository.save(tourPhoto1);
            tourPhotoRepository.save(tourPhoto2);
            tourPhotoRepository.save(tourPhoto3);
            //when
            Optional<Long> representativePhotoId = tourPhotoRepository.findRepresentativePhotoId(tour);
            //then
            representativePhotoId.ifPresent(id->
                    assertThat(tourPhoto1.getId()).isEqualTo(id));
        }

        @Test
        @DisplayName("대표사진이 0개라서 존재하지 않을때")
        void findRepresentativePhotoId2() {
            //given
            TourPhoto tourPhoto1 = makeTestTourPhoto(tour, "123", false);
            TourPhoto tourPhoto2 = makeTestTourPhoto(tour, "513", false);
            TourPhoto tourPhoto3 = makeTestTourPhoto(tour, "5113", false);
            tourPhotoRepository.save(tourPhoto1);
            tourPhotoRepository.save(tourPhoto2);
            tourPhotoRepository.save(tourPhoto3);
            //when
            Optional<Long> representativePhotoId = tourPhotoRepository.findRepresentativePhotoId(tour);

            //then
            boolean empty = representativePhotoId.isEmpty();
            assertThat(empty).isTrue();

        }
    }

    @Test
    void countRepresentative() {
        //given
        TourPhoto tourPhoto1 = makeTestTourPhoto(tour, "1234", false);
        TourPhoto tourPhoto2 = makeTestTourPhoto(tour, "12345", true);
        TourPhoto tourPhoto3 = makeTestTourPhoto(tour, "12341235", true);
        tourPhotoRepository.save(tourPhoto1);
        tourPhotoRepository.save(tourPhoto2);
        tourPhotoRepository.save(tourPhoto3);
        //when
        Integer num = tourPhotoRepository.countRepresentative(tour);
        //then
        assertThat(num).isEqualTo(2);

    }
}