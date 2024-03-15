package tour.nonghaeng.domain.member.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.global.login.dto.TempMember;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    @Query("select new tour.nonghaeng.global.login.dto.TempMember(u.number, u.password, u.role) from User u where u.number = :number")
    Optional<TempMember> findByTempUserByNumber(String number);

    Optional<User> findByNumber(String number);

    Optional<User> findByRefreshToken(String refreshToken);

}
