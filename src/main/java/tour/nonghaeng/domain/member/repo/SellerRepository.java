package tour.nonghaeng.domain.member.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.global.login.dto.TempMember;

import java.util.Optional;

@Repository
public interface SellerRepository extends JpaRepository<Seller,Long> {

    @Query("select new tour.nonghaeng.global.login.dto.TempMember(s.username,s.password,s.role) from Seller s where s.username = :username")
    Optional<TempMember> findTempSellerByUsername(String username);

    Optional<Seller> findByUsername(String username);

    Optional<Seller> findByRefreshToken(String refreshToken);
}
