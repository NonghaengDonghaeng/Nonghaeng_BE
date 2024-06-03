package tour.nonghaeng.domain.member.data.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tour.nonghaeng.domain.member.data.Member;
import tour.nonghaeng.domain.member.data.Seller;

import java.util.Optional;

@Repository
public interface SellerRepository extends JpaRepository<Seller,Long> {


    Optional<Seller> findByUsername(String username);

    @Query("select s from Seller s where s.username = :username")
    Optional<Member> findMemberByUsername(@Param("username") String username);

}
