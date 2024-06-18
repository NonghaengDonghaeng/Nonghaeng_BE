package tour.nonghaeng.domain.reservation.data.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tour.nonghaeng.domain.reservation.data.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
