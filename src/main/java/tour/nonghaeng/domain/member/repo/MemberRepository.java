package tour.nonghaeng.domain.member.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tour.nonghaeng.domain.member.dto.TempMember;
import tour.nonghaeng.domain.member.entity.Member;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("select new tour.nonghaeng.domain.member.dto.TempMember(m.username,m.password,m.role) from Member m where m.username = :username")
    Optional<TempMember> findTempUserByUsername(String username);

    Optional<Member> findByUsername(String username);

    Optional<Member> findByRefreshToken(String refreshToken);
}
