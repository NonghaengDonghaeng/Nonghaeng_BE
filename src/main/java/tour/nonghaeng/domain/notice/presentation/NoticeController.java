package tour.nonghaeng.domain.notice.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import tour.nonghaeng.domain.member.data.Member;
import tour.nonghaeng.domain.notice.dto.CreateNoticeDto;
import tour.nonghaeng.domain.notice.dto.NoticeDetailDto;
import tour.nonghaeng.domain.notice.dto.NoticeSpecDto;
import tour.nonghaeng.domain.notice.dto.NoticeSummaryDto;
import tour.nonghaeng.domain.notice.service.NoticeService;
import tour.nonghaeng.global.auth.AuthService;

@RestController
@RequestMapping("/notices")
@RequiredArgsConstructor
@Slf4j
public class NoticeController {

    private final NoticeService noticeService;
    private final AuthService authService;


    @GetMapping
    public ResponseEntity<Page<NoticeSummaryDto>> getNoticeSummaryDtoPage(@PageableDefault(size = 10) Pageable pageable,
                                                                          @ModelAttribute NoticeSpecDto noticeSpecDto) {

        log.info(noticeSpecDto.toString());

        Page<NoticeSummaryDto> dtoPage = noticeService.getSummaryDtoPage(pageable, noticeSpecDto);

        return new ResponseEntity<>(dtoPage, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createNotice(Authentication authentication,
                                               @RequestBody CreateNoticeDto createNoticeDto) {
        Member admin = authService.toMemberEntity(authentication);

        Long id = noticeService.create(admin, createNoticeDto).getId();

        return new ResponseEntity<>("공지 생성완료(id:" + id + ")", HttpStatus.CREATED);
    }

    @GetMapping("/{noticeId}")
    public ResponseEntity<NoticeDetailDto> getNoticeDetailDto(@PathVariable("noticeId") Long noticeId) {

        NoticeDetailDto detailDto = noticeService.getDetailDto(noticeId);

        return new ResponseEntity<>(detailDto, HttpStatus.OK);
    }

    @DeleteMapping("/{noticeId}")
    public ResponseEntity<String> deleteNotice(Authentication authentication,
                                               @PathVariable("noticeId") Long noticeId) {

        Member member = authService.toMemberEntity(authentication);

        noticeService.deleteNotice(member,noticeId);

        return new ResponseEntity<>("공지 삭제 완료(id:"+noticeId+")", HttpStatus.OK);
    }

    @GetMapping("/important/{noticeId}")
    public ResponseEntity<String> onImportantNotice(Authentication authentication,
                                                    @PathVariable("noticeId") Long noticeId,
                                                    @RequestParam(value = "not",defaultValue = "false") boolean notFlg) {

        Member member = authService.toMemberEntity(authentication);

        noticeService.setImportant(member, noticeId, notFlg);

        return new ResponseEntity<>("중요공지로 변환(id:" + noticeId + ")", HttpStatus.OK);
    }

}
