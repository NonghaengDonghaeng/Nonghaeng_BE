package tour.nonghaeng.domain.notice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tour.nonghaeng.domain.member.data.Member;
import tour.nonghaeng.domain.notice.data.Notice;
import tour.nonghaeng.domain.notice.data.repo.NoticeRepository;
import tour.nonghaeng.domain.notice.dto.CreateNoticeDto;
import tour.nonghaeng.domain.notice.dto.NoticeDetailDto;
import tour.nonghaeng.domain.notice.dto.NoticeSpecDto;
import tour.nonghaeng.domain.notice.dto.NoticeSummaryDto;
import tour.nonghaeng.domain.notice.presentation.exception.NoticeException;
import tour.nonghaeng.domain.notice.service.valid.NoticeValidator;
import tour.nonghaeng.global.auth.AuthValidator;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository noticeRepository;

    private final AuthValidator authValidator;
    private final NoticeValidator noticeValidator;

    @Override
    public Notice findById(Long id) {
        return noticeRepository.findById(id).orElseThrow(()-> NoticeException.EXCEPTION);
    }

    @Override
    public Notice create(Member member, CreateNoticeDto createDto) {

        Notice notice = createDto.toEntity(authValidator.adminValidate(member));

        return noticeRepository.save(notice);
    }

    @Override
    public NoticeDetailDto getDetailDto(Long noticeId) {

        Notice notice = findById(noticeId);
        return NoticeDetailDto.toDto(notice);
    }

    @Override
    public Page<NoticeSummaryDto> getSummaryDtoPage(Pageable pageable, NoticeSpecDto searchCondition) {

        Specification<Notice> noticeSpecification = searchCondition.buildSpecification();
        Page<Notice> dtoPage = noticeRepository.findAll(noticeSpecification, pageable);

        return NoticeSummaryDto.toDtoPage(dtoPage);
    }

    @Override
    public void deleteNotice(Member member, Long noticeId) {

        authValidator.adminValidate(member);

        noticeRepository.delete(findById(noticeId));
    }

    @Override
    public boolean setImportant(Member member, Long noticeId,boolean notImportantFlg) {
        authValidator.adminValidate(member);

        Notice notice = findById(noticeId);

        if(notImportantFlg){
            notice.offImportant();
            return noticeRepository.save(notice).isImportant();
        }

        notice.onImportant();
        return noticeRepository.save(notice).isImportant();

    }
}
