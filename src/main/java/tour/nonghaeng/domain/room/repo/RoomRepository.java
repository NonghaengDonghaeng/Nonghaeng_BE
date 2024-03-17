package tour.nonghaeng.domain.room.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.room.entity.Room;
import tour.nonghaeng.domain.tour.entity.Tour;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room,Long> {

    @Query("SELECT MIN(r.priceOffPeak) FROM Room r where r.tour = :tour")
    Integer findMinPriceByTour(Tour tour);

    @Query("SELECT MAX(CASE when r.pricePeak > r.priceHoliday then r.pricePeak else r.priceHoliday END) FROM Room r where r.tour = :tour")
    Integer findMaxPriceByTour(Tour tour);

    @Query("SELECT r.seller FROM Room r where r.id = :roomId")
    Optional<Seller> findSellerByRoomId(Long roomId);

    @Query("SELECT MIN(rcd.closeDate) from RoomCloseDate rcd where rcd.room.id = :id ")
    Optional<LocalDate> findOldestCloseDate(@Param("id") Long roomId);

    @Query("select r.id from Room r")
    List<Long> findAllIds();

    boolean existsById(Long roomId);

}
