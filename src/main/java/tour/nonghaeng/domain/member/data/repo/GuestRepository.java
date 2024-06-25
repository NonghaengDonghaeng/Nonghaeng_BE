package tour.nonghaeng.domain.member.data.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tour.nonghaeng.domain.member.data.Guest;
import tour.nonghaeng.domain.member.data.Member;

import java.util.Optional;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {

    Optional<Guest> findByUsername(String username);

    @Query("select g from Guest g where g.username = :username")
    Optional<Member> findMemberByUsername(@Param("username") String username);
}
