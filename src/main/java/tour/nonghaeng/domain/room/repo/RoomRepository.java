package tour.nonghaeng.domain.room.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tour.nonghaeng.domain.room.entity.Room;
import tour.nonghaeng.domain.tour.entity.Tour;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room,Long> {

    @Query("SELECT MIN(r.priceOffPeak) FROM Room r where r.tour = :tour")
    Integer findMinPriceByTour(Tour tour);

    @Query("SELECT MAX(CASE when r.pricePeak > r.priceHoliday then r.pricePeak else r.priceHoliday END) FROM Room r where r.tour = :tour")
    Integer findMaxPriceByTour(Tour tour);

    List<Room> findByTour(Tour tour);
}
