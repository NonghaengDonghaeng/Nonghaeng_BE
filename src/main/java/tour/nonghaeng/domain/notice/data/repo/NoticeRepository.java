package tour.nonghaeng.domain.notice.data.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tour.nonghaeng.domain.notice.data.Notice;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {

    boolean existsById(Long noticeId);

    Page<Notice> findAll(Specification<Notice> spec, Pageable pageable);
}
