package tour.nonghaeng.domain.member.data.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tour.nonghaeng.domain.member.data.Admin;
import tour.nonghaeng.domain.member.data.Member;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findByUsername(String username);

    @Query("select a from Admin a where a.username = :username")
    Optional<Member> findMemberByUsername(@Param("username") String username);
}
