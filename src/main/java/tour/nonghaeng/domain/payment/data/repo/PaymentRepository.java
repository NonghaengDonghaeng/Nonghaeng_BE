package tour.nonghaeng.domain.payment.data.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tour.nonghaeng.domain.payment.data.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
