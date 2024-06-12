package tour.nonghaeng.domain.notice.service;

import tour.nonghaeng.domain.member.data.Member;
import tour.nonghaeng.domain.notice.data.Notice;
import tour.nonghaeng.domain.notice.dto.CreateNoticeDto;
import tour.nonghaeng.domain.notice.dto.NoticeDetailDto;
import tour.nonghaeng.domain.notice.dto.NoticeSpecDto;
import tour.nonghaeng.domain.notice.dto.NoticeSummaryDto;
import tour.nonghaeng.global.infra.service.CrudService;
import tour.nonghaeng.global.infra.service.ViewService;

public interface NoticeService extends CrudService<Notice, CreateNoticeDto>, ViewService<NoticeSummaryDto, NoticeDetailDto, NoticeSpecDto> {

    void deleteNotice(Member member, Long noticeId);


    boolean setImportant(Member member, Long noticeId,boolean notImportantFlg);

}
