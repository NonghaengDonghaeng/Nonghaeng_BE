package tour.nonghaeng.domain.member.data.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tour.nonghaeng.domain.etc.enums.social.SocialType;
import tour.nonghaeng.domain.member.data.Member;
import tour.nonghaeng.domain.member.data.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {


    Optional<User> findByUsername(String username);

    @Query("select u from User u where u.username = :username")
    Optional<Member> findMemberByUsername(@Param("username") String username);

    Optional<User> findBySocialTypeAndSocialId(SocialType socialType, String socialId);

}
