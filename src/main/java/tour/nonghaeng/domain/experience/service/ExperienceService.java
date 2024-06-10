package tour.nonghaeng.domain.experience.service;

import tour.nonghaeng.domain.experience.data.Experience;
import tour.nonghaeng.domain.experience.dto.*;
import tour.nonghaeng.global.infra.service.CrudService;
import tour.nonghaeng.global.infra.service.ManageCloseDateService;
import tour.nonghaeng.global.infra.service.ViewService;

import java.time.LocalDate;
import java.util.List;

public interface ExperienceService extends CrudService<Experience, CreateExpDto>, ViewService<ExpSummaryDto, ExpDetailDto, ExpSpecDto>, ManageCloseDateService<AddExpCloseDateDto> {


    void addOnlyRounds(Long experienceId, List<AddExpRoundDto> dtoList);

    ExpRoundInfoDto getExpRoundInfoDto(Long experienceId, LocalDate dateParameter);
}
