package tour.nonghaeng.domain.notice.service.valid;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.notice.data.repo.NoticeRepository;

@Component
@RequiredArgsConstructor
public class NoticeValidator {

    private final NoticeRepository noticeRepository;


}