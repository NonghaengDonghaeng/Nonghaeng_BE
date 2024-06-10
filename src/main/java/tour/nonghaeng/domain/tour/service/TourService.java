package tour.nonghaeng.domain.tour.service;

import tour.nonghaeng.domain.member.data.Member;
import tour.nonghaeng.domain.tour.data.Tour;
import tour.nonghaeng.domain.tour.dto.CreateTourDto;
import tour.nonghaeng.domain.tour.dto.TourDetailDto;
import tour.nonghaeng.domain.tour.dto.TourSpecDto;
import tour.nonghaeng.domain.tour.dto.TourSummaryDto;
import tour.nonghaeng.global.infra.service.CrudService;
import tour.nonghaeng.global.infra.service.ViewService;

public interface TourService extends CrudService<Tour, CreateTourDto>, ViewService<TourSummaryDto, TourDetailDto, TourSpecDto> {

   Tour findBySeller(Member seller);
}
