package tour.nonghaeng.domain.test.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tour.nonghaeng.domain.etc.enums.area.AreaCode;
import tour.nonghaeng.domain.etc.enums.bank.BankCode;
import tour.nonghaeng.domain.etc.enums.experience.ExperienceType;
import tour.nonghaeng.domain.etc.enums.room.RoomType;
import tour.nonghaeng.domain.etc.enums.tour.TourType;
import tour.nonghaeng.domain.experience.dto.AddExpRoundDto;
import tour.nonghaeng.domain.experience.dto.CreateExpDto;
import tour.nonghaeng.domain.experience.service.ExperienceService;
import tour.nonghaeng.domain.member.data.Seller;
import tour.nonghaeng.domain.member.data.User;
import tour.nonghaeng.domain.member.dto.SellerJoinDto;
import tour.nonghaeng.domain.member.dto.UserJoinDto;
import tour.nonghaeng.domain.member.service.SellerService;
import tour.nonghaeng.domain.member.service.UserService;
import tour.nonghaeng.domain.reservation.service.ReservationServiceImpl;
import tour.nonghaeng.domain.room.dto.CreateRoomDto;
import tour.nonghaeng.domain.room.service.RoomService;
import tour.nonghaeng.domain.tour.dto.CreateTourDto;
import tour.nonghaeng.domain.tour.service.TourService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class DummyDataManyService implements DummyDataService {

    private final UserService userService;
    private final SellerService sellerService;
    private final TourService tourService;
    private final ExperienceService experienceService;
    private final RoomService roomService;
    private final ReservationServiceImpl reservationServiceImpl;

    @Override
    public void setDummyData() {
        //seller 등록
        SellerJoinDto sellerJoinDto1 = new SellerJoinDto("010-1111-1111", "01-1234-1234", "seller1", "testSeller1", "testSeller1@email.com", "seller1", "seller1", "대전광역시", "042-123-1111", AreaCode.DAEJEON, BankCode.KOREA, "02-898123-91", "testSeller1");
        SellerJoinDto sellerJoinDto2 = new SellerJoinDto("010-2222-2222", "02-1234-1234", "seller2", "testSeller2", "testSeller2@email.com", "seller2", "seller2", "서울", "042-123-2222", AreaCode.SEOUL, BankCode.KOREA, "02-89814412-91", "testSeller2");
        SellerJoinDto sellerJoinDto3 = new SellerJoinDto("010-3333-3333", "03-1234-1234", "seller3", "testSeller3", "testSeller3@email.com", "seller3", "seller3", "대전광역시", "042-123-3333", AreaCode.DAEJEON, BankCode.KOREA, "02-815423-91", "testSeller3");
        SellerJoinDto sellerJoinDto4 = new SellerJoinDto("010-4444-4444", "04-1234-1234", "seller4", "testSeller4", "testSeller4@email.com", "seller4", "seller4", "서울", "042-123-4444", AreaCode.SEOUL, BankCode.KOREA, "02-90123-91", "testSeller4");
        SellerJoinDto sellerJoinDto5 = new SellerJoinDto("010-5555-5555", "05-1234-1234", "seller5", "testSeller5", "testSeller5@email.com", "seller5", "seller5", "대전광역시", "042-123-5555", AreaCode.DAEJEON, BankCode.KOREA, "02-852123-91", "testSeller5");
        SellerJoinDto sellerJoinDto6 = new SellerJoinDto("010-6666-6666", "06-1234-1234", "seller6", "testSeller6", "testSeller6@email.com", "seller6", "seller6", "부산", "042-123-6666", AreaCode.BUSAN, BankCode.KOREA, "02-8982323-91", "testSeller6");
        SellerJoinDto sellerJoinDto7 = new SellerJoinDto("010-6666-1266", "06-1234-1234", "seller7", "testSeller7", "testSeller7@email.com", "seller7", "seller7", "부산", "042-123-6666", AreaCode.SEOUL, BankCode.KOREA, "02-8982323-91", "testSeller7");
        SellerJoinDto sellerJoinDto8 = new SellerJoinDto("010-6666-6566", "06-1234-1234", "seller8", "testSeller8", "testSeller8@email.com", "seller8", "seller8", "부산", "042-123-6666", AreaCode.DAEJEON, BankCode.KOREA, "02-8982323-91", "testSeller8");
        SellerJoinDto sellerJoinDto9 = new SellerJoinDto("010-6666-8666", "06-1234-1234", "seller9", "testSeller9", "testSeller9@email.com", "seller9", "seller9", "부산", "042-123-6666", AreaCode.SEJONG, BankCode.KOREA, "02-8982323-91", "testSeller9");
        SellerJoinDto sellerJoinDto10 = new SellerJoinDto("010-6666-6696", "06-1234-1234", "seller10", "testSeller10", "testSeller10@email.com", "seller10", "seller10", "부산", "042-123-6666", AreaCode.DAEJEON, BankCode.KOREA, "02-8982323-91", "testSeller10");


        Seller seller1 = sellerService.join(sellerJoinDto1);
        Seller seller2 = sellerService.join(sellerJoinDto2);
        Seller seller3 = sellerService.join(sellerJoinDto3);
        Seller seller4 = sellerService.join(sellerJoinDto4);
        Seller seller5 = sellerService.join(sellerJoinDto5);
        Seller seller6 = sellerService.join(sellerJoinDto6);
        Seller seller7 = sellerService.join(sellerJoinDto7);
        Seller seller8 = sellerService.join(sellerJoinDto8);
        Seller seller9 = sellerService.join(sellerJoinDto9);
        Seller seller10 = sellerService.join(sellerJoinDto10);

        //user 등록
        UserJoinDto userJoinDto1 = new UserJoinDto(AreaCode.DAEJEON, "user1", "user1", "user1@email.com", "user1", "user1", "user1");
        UserJoinDto userJoinDto2 = new UserJoinDto(AreaCode.DAEJEON, "user2", "user2", "user2@email.com", "user2", "user2", "user2");
        UserJoinDto userJoinDto3 = new UserJoinDto(AreaCode.DAEJEON, "user3", "user3", "user3@email.com", "user3", "user3", "user3");

        User user1 = userService.join(userJoinDto1);
        User user2 = userService.join(userJoinDto2);
        User user3 = userService.join(userJoinDto3);

        //tour 생성
        CreateTourDto createTourDto1 = new CreateTourDto(TourType.VILLAGE, "1번 체험마을", "1번.com", "1번 체험마을입니다.", "정이 있는 마을", "체험마을", "식당 1개", "주차장 10대 수용가능", "남녀 화장실", "편의점");
        CreateTourDto createTourDto2 = new CreateTourDto(TourType.BED_BREAKFAST, "2번 농촌민박", "2번.com", "2번 농촌민박입니다.", "정이 있는 민박", "민박", "식당 1개", "주차장 10대 수용가능", "남녀 화장실", "편의점");
        CreateTourDto createTourDto3 = new CreateTourDto(TourType.VILLAGE, "3번 체험마을", "3번.com", "3번 체험마을입니다.", "정이 있는 마을", "체험마을", "식당 1개", "주차장 10대 수용가능", "남녀 화장실", "편의점");
        CreateTourDto createTourDto4 = new CreateTourDto(TourType.CAMPING, "4번 캠핑장", "4번.com", "4번 캠핑장입니다.", "정이 있는 캠핑장", "캠핑", "식당 1개", "주차장 10대 수용가능", "남녀 화장실", "편의점");
        CreateTourDto createTourDto5 = new CreateTourDto(TourType.VILLAGE, "5번 체험마을", "5번.com", "5번 체험마을입니다.", "정이 있는 마을", "체험마을", "식당 1개", "주차장 10대 수용가능", "남녀 화장실", "편의점");
        CreateTourDto createTourDto6 = new CreateTourDto(TourType.BED_BREAKFAST, "6번 농촌민박", "6번.com", "6번 농촌민박입니다.", "싼 있는 민박", "민박", "식당 1개", "주차장 10대 수용가능", "남녀 화장실", "편의점");



        tourService.create(seller1, createTourDto1);
        tourService.create(seller2, createTourDto2);
        tourService.create(seller3, createTourDto3);
        tourService.create(seller4, createTourDto4);
        tourService.create(seller5, createTourDto5);
        tourService.create(seller6, createTourDto6);
        tourService.create(seller7, createTourDto1);
        tourService.create(seller8, createTourDto2);
        tourService.create(seller9, createTourDto3);
        tourService.create(seller10, createTourDto4);

        //체험 생성
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 12, 31);
        int minParticipant = 2;
        int maxParticipant = 10;
        LocalTime startTime = LocalTime.of(8, 0);
        LocalTime endTime = LocalTime.of(10, 0);
        AddExpRoundDto addExpRoundDto1 = new AddExpRoundDto(LocalTime.of(8, 0), LocalTime.of(10, 0), maxParticipant);
        AddExpRoundDto addExpRoundDto2 = new AddExpRoundDto(LocalTime.of(10, 0), LocalTime.of(12, 0), maxParticipant);
        List<AddExpRoundDto> expRoundDtoList = new ArrayList<>();
        expRoundDtoList.add(addExpRoundDto1);
        expRoundDtoList.add(addExpRoundDto2);

        CreateExpDto createExpDto1 = new CreateExpDto(ExperienceType.RURAL, "벼따기 체험", startDate, endDate, minParticipant, maxParticipant, 10000, 2, "벼따기 수확도 가능", "벼따는 체험", "벼따기", "낫", "낫가져오슈", expRoundDtoList);
        CreateExpDto createExpDto2 = new CreateExpDto(ExperienceType.RURAL, "소타기 체험", startDate, endDate, minParticipant, maxParticipant, 15000, 2, "소타는거 재밌음", "소타기 체험", "음메", "안장", "소 난폭해", expRoundDtoList);
        CreateExpDto createExpDto3 = new CreateExpDto(ExperienceType.CRAFTING, "도자기 체험", startDate, endDate, minParticipant, maxParticipant, 50000, 2, "도자기 가져가", "도자기 체험", "도자기", "손", "흙 알레르기 불가", expRoundDtoList);
        CreateExpDto createExpDto4 = new CreateExpDto(ExperienceType.CULTURE, "제사 지내기체험", startDate, endDate, minParticipant, maxParticipant, 5000, 2, "제사음식 맛있음", "제사를 준비하고 지내보자", "제사", "귀신믿는마음", "귀신에 쇼크올수있음", expRoundDtoList);
        CreateExpDto createExpDto5 = new CreateExpDto(ExperienceType.LEISURE_SPORTS, "둘레길 걷기", startDate, endDate, minParticipant, maxParticipant, 1000, 2, "둘레길 걸어요", "가족과 함께 둘렛길 걸어봐요", "둘레길", "운동화", "물집패", expRoundDtoList);


        experienceService.create(seller1, createExpDto1);
        experienceService.create(seller1, createExpDto2);
        experienceService.create(seller1, createExpDto3);
        experienceService.create(seller1, createExpDto4);
        experienceService.create(seller1, createExpDto5);

        experienceService.create(seller2, createExpDto1);
        experienceService.create(seller2, createExpDto2);
        experienceService.create(seller2, createExpDto3);
        experienceService.create(seller2, createExpDto4);
        experienceService.create(seller2, createExpDto5);

        experienceService.create(seller3, createExpDto1);
        experienceService.create(seller3, createExpDto2);
        experienceService.create(seller3, createExpDto3);
        experienceService.create(seller3, createExpDto4);
        experienceService.create(seller3, createExpDto5);

        experienceService.create(seller4, createExpDto1);
        experienceService.create(seller4, createExpDto2);
        experienceService.create(seller4, createExpDto3);
        experienceService.create(seller4, createExpDto4);
        experienceService.create(seller4, createExpDto5);

        experienceService.create(seller5, createExpDto1);
        experienceService.create(seller5, createExpDto2);
        experienceService.create(seller5, createExpDto3);
        experienceService.create(seller5, createExpDto4);
        experienceService.create(seller5, createExpDto5);

        experienceService.create(seller6, createExpDto1);
        experienceService.create(seller6, createExpDto2);
        experienceService.create(seller6, createExpDto3);
        experienceService.create(seller6, createExpDto4);
        experienceService.create(seller6, createExpDto5);

        experienceService.create(seller7, createExpDto1);
        experienceService.create(seller7, createExpDto2);
        experienceService.create(seller7, createExpDto3);
        experienceService.create(seller7, createExpDto4);
        experienceService.create(seller7, createExpDto5);

        experienceService.create(seller8, createExpDto1);
        experienceService.create(seller8, createExpDto2);
        experienceService.create(seller8, createExpDto3);
        experienceService.create(seller8, createExpDto4);
        experienceService.create(seller8, createExpDto5);

        experienceService.create(seller9, createExpDto1);
        experienceService.create(seller9, createExpDto2);
        experienceService.create(seller9, createExpDto3);
        experienceService.create(seller9, createExpDto4);
        experienceService.create(seller9, createExpDto5);

        experienceService.create(seller10, createExpDto1);
        experienceService.create(seller10, createExpDto2);
        experienceService.create(seller10, createExpDto3);
        experienceService.create(seller10, createExpDto4);
        experienceService.create(seller10, createExpDto5);

        //숙소 생성
        LocalTime checkinTime = LocalTime.of(15, 0);
        LocalTime checkoutTime = LocalTime.of(11, 0);
        CreateRoomDto createRoomDto1 = new CreateRoomDto(RoomType.VILLAGE, "사랑방", "사랑이 삭트는 방", 20000, 10000, 15000, 2, 4, 1000, checkinTime, checkoutTime, 5, "거실,방2,화장실1", "취사도구,전자레인지", "X", "편의점", "팁이 없어", "객실내 흡연안됨");
        CreateRoomDto createRoomDto2 = new CreateRoomDto(RoomType.VILLAGE, "온돌방", "온돌이 뜨근한 방", 20000, 10000, 15000, 2, 4, 1000, checkinTime, checkoutTime, 5, "거실,방2,화장실1", "취사도구,전자레인지,젓가락", "X", "편의점", "팁이 없어", "객실내 흡연안됨");
        CreateRoomDto createRoomDto3 = new CreateRoomDto(RoomType.BED, "201호", "논뷰가 있는 방", 20000, 10000, 15000, 2, 4, 1000, checkinTime, checkoutTime, 5, "거실1, 방3, 화장실2", "취사도구,전자레인지,WIFI", "X", "편의점,카페", "아침에 논뷰보면서 커피", "창문환기 하루1번");
        CreateRoomDto createRoomDto4 = new CreateRoomDto(RoomType.BED, "404호", "바다가 보이는 오션뷰", 20000, 10000, 15000, 2, 4, 1000, checkinTime, checkoutTime, 5, "거실,루프탑, 화장실1", "취사도구,전자레인지,침구류,TV", "X", "편의점,식당", "팁이 없어", "객실내 흡연안됨");
        CreateRoomDto createRoomDto5 = new CreateRoomDto(RoomType.CAMPING, "논캠핑장", "논산 군인뷰", 20000, 10000, 15000, 2, 4, 1000, checkinTime, checkoutTime, 5, "카라반,화장실1", "취사도구,전자레인지,침구류,모니터", "X", "편의점,식당", "팁이 없어", "불놀이 금지");
        CreateRoomDto createRoomDto6 = new CreateRoomDto(RoomType.ETC, "노숙", "노숙 경험을 해볼수 있지만 노숙은 아님", 20000, 10000, 15000, 2, 4, 1000, checkinTime, checkoutTime, 5, "거실,천막", "취사도구,전자레인지,침구류", "X", "편의점,식당,공용화장실", "팁이 없어", "자다 입돌아가요");

        roomService.create(seller1,createRoomDto1);
        roomService.create(seller1,createRoomDto2);
        roomService.create(seller1,createRoomDto3);
        roomService.create(seller1,createRoomDto4);
        roomService.create(seller1,createRoomDto5);
        roomService.create(seller1,createRoomDto6);

        roomService.create(seller2,createRoomDto1);
        roomService.create(seller2,createRoomDto2);
        roomService.create(seller2,createRoomDto3);
        roomService.create(seller2,createRoomDto4);
        roomService.create(seller2,createRoomDto5);
        roomService.create(seller2,createRoomDto6);

        roomService.create(seller3,createRoomDto1);
        roomService.create(seller3,createRoomDto2);
        roomService.create(seller3,createRoomDto3);
        roomService.create(seller3,createRoomDto4);
        roomService.create(seller3,createRoomDto5);
        roomService.create(seller3,createRoomDto6);

        roomService.create(seller4,createRoomDto1);
        roomService.create(seller4,createRoomDto2);
        roomService.create(seller4,createRoomDto3);
        roomService.create(seller4,createRoomDto4);
        roomService.create(seller4,createRoomDto5);
        roomService.create(seller4,createRoomDto6);

        roomService.create(seller5,createRoomDto1);
        roomService.create(seller5,createRoomDto2);
        roomService.create(seller5,createRoomDto3);
        roomService.create(seller5,createRoomDto4);
        roomService.create(seller5,createRoomDto5);
        roomService.create(seller5,createRoomDto6);

        roomService.create(seller6,createRoomDto1);
        roomService.create(seller6,createRoomDto2);
        roomService.create(seller6,createRoomDto3);
        roomService.create(seller6,createRoomDto4);
        roomService.create(seller6,createRoomDto5);
        roomService.create(seller6,createRoomDto6);

        roomService.create(seller7,createRoomDto1);
        roomService.create(seller7,createRoomDto2);
        roomService.create(seller7,createRoomDto3);
        roomService.create(seller7,createRoomDto4);
        roomService.create(seller7,createRoomDto5);
        roomService.create(seller7,createRoomDto6);

        roomService.create(seller8,createRoomDto1);
        roomService.create(seller8,createRoomDto2);
        roomService.create(seller8,createRoomDto3);
        roomService.create(seller8,createRoomDto4);
        roomService.create(seller8,createRoomDto5);
        roomService.create(seller8,createRoomDto6);

        roomService.create(seller9,createRoomDto1);
        roomService.create(seller9,createRoomDto2);
        roomService.create(seller9,createRoomDto3);
        roomService.create(seller9,createRoomDto4);
        roomService.create(seller9,createRoomDto5);
        roomService.create(seller9,createRoomDto6);

        roomService.create(seller10,createRoomDto1);
        roomService.create(seller10,createRoomDto2);
        roomService.create(seller10,createRoomDto3);
        roomService.create(seller10,createRoomDto4);
        roomService.create(seller10,createRoomDto5);
        roomService.create(seller10,createRoomDto6);

    }



}
