package tour.nonghaeng.domain.test.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tour.nonghaeng.global.infra.enums.area.AreaCode;
import tour.nonghaeng.global.infra.enums.bank.BankCode;
import tour.nonghaeng.global.infra.enums.experience.ExperienceType;
import tour.nonghaeng.global.infra.enums.room.RoomType;
import tour.nonghaeng.global.infra.enums.tour.TourType;
import tour.nonghaeng.domain.experience.dto.AddExpRoundDto;
import tour.nonghaeng.domain.experience.dto.CreateExpDto;
import tour.nonghaeng.domain.experience.service.ExperienceService;
import tour.nonghaeng.domain.member.data.Seller;
import tour.nonghaeng.domain.member.data.User;
import tour.nonghaeng.domain.member.dto.SellerJoinDto;
import tour.nonghaeng.domain.member.dto.UserJoinDto;
import tour.nonghaeng.domain.member.service.SellerService;
import tour.nonghaeng.domain.member.service.UserService;
import tour.nonghaeng.domain.reservation.service.ReservationService;
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
public class DummyDataWellService implements DummyDataService{


    private final UserService userService;
    private final SellerService sellerService;
    private final TourService tourService;
    private final ExperienceService experienceService;
    private final RoomService roomService;
    private final ReservationService reservationService;
    @Override
    public void setDummyData() {

        //유저등록
        //user 등록
        UserJoinDto userJoinDto1 = new UserJoinDto(AreaCode.DAEJEON, "user1", "user1", "user1@email.com", "user1", "user1","user1");
        UserJoinDto userJoinDto2 = new UserJoinDto(AreaCode.DAEJEON, "user2", "user2", "user2@email.com", "user2", "user2","user2");
        UserJoinDto userJoinDto3 = new UserJoinDto(AreaCode.DAEJEON, "user3", "user3", "user3@email.com", "user3", "user3","user3");

        User user1 = userService.join(userJoinDto1);
        User user2 = userService.join(userJoinDto2);
        User user3 = userService.join(userJoinDto3);

        //---------------------------
        LocalTime checkinTime = LocalTime.of(15, 0);
        LocalTime checkoutTime = LocalTime.of(11, 0);
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 12, 31);
        int minParticipant = 2;
        int maxParticipant = 10;

        AddExpRoundDto addExpRoundDto1 = new AddExpRoundDto(LocalTime.of(8, 0), LocalTime.of(10, 0), 0);
        AddExpRoundDto addExpRoundDto2 = new AddExpRoundDto(LocalTime.of(10, 0), LocalTime.of(12, 0), 0);
        AddExpRoundDto addExpRoundDto3 = new AddExpRoundDto(LocalTime.of(12, 0), LocalTime.of(14, 0), 0);
        List<AddExpRoundDto> expRoundDtoList = new ArrayList<>();
        expRoundDtoList.add(addExpRoundDto1);
        expRoundDtoList.add(addExpRoundDto2);
        expRoundDtoList.add(addExpRoundDto3);



        //1번. 산들강웅포마을
        SellerJoinDto sellerJoinDto1 = new SellerJoinDto("010-1111-1111", "01-1234-1234", "seller1", "testSeller1", "testSeller1@email.com", "seller1", "seller1", "전북 익산시 웅포면 강변로 284", "063-861-6627~8", AreaCode.JEONBUK, BankCode.KOREA, "02-898123-91", "testSeller1");
        Seller seller1 = sellerService.join(sellerJoinDto1);

        CreateTourDto createTourDto1 = new CreateTourDto(TourType.VILLAGE, "산들강웅포마을", "http://www.ungpo.kr", "산들강웅포마을은 이름 그대로 산과 들, 강이 어우러진 아름다운 마을입니다. 마을 앞으로 금강이 흐르고, 뒤로는 함라산이 자리한 이곳은 서쪽으로는 옥구평야, 남쪽으로는 만경강을 경계로 김제평야와 인접해 있습니다. 아름다운 자연경관만으로도 농촌여행지로 손색이 없는, 전북특별자치도에서 으뜸촌으로 선정된 마을입니다. 농어촌관광사업 등급결정제도에서 교육, 체험, 숙박, 음식에 대한 등급평가가 모두 1등급이어야 으뜸촌으로 선정되는데 산들강웅포마을은 그야말로 등급으로 인증된 농촌체험여행지라고 할 수 있습니다.", "산과 들 강이 어우러진 마을", "마을 앞으로 금강이 흐르며, 일몰이 장관인 산들강웅포마을은 7대 낙조 명소 중 하나로 꼽히는 곳입니다. 환경과 생태계 보호를 위해 힘쓰고 있으며, 깔끔한 숙박, 맛있는 음식, 다양한 체험 프로그램을 경험할 수 있습니다.", "메뉴정보: 산들강시골밥상 기타사항: 뷔페식", "80대", "남녀 화장실", "바베큐장, 공용주방, 다목적구장(족구, 농구장)");
        tourService.create(seller1, createTourDto1);

        CreateExpDto createExpDto1_1 = new CreateExpDto(ExperienceType.RURAL, "감자수확 체험", startDate, endDate, 10, 60, 10000, 2, "1.마을 주민분들이 정성껏 카꾼 밭에 직접 가서 수확하기\n2.친환경 감자", "감자를 따는 수확체험입니다.", "감자수확", "없음", "--", expRoundDtoList);
        CreateExpDto createExpDto1_2 = new CreateExpDto(ExperienceType.CRAFTING, "반려식물심기", startDate, endDate, 2, 40, 17000, 2, "1.어디에 배치해도 멋있는 인테리어 소품~\n2.관리하기 쉬운 반려식물 3.나만의 친환경 공기청정기~",
                "금강이 흐르는 웅포곰개나루 근처에 위치한 산들강웅포마을은 지역특산품을 이용한 체험부터 흔하지 않는 체험상품까지 다양한 체험을 운영하고 있습니다. 그 중 반려식물 심기는 일반적인 화분에 식물을 심는걸로 되어 있지만 산들강웅포에서는 액자로 만들어 벽에 걸 수 있어 인테리어 소품으로도 효과적이지만 방안에 걸어두면 공기청정기의 역활도 할 수 있어 한번에 2가지의 장점을 가질수 있습니다.", "화분이 아닌 액자에 식물을 심다?", "나만의 미적감각", "다육식물을 살살 다뤄주세요.", expRoundDtoList);
        CreateExpDto createExpDto1_3 = new CreateExpDto(ExperienceType.CRAFTING, "쑥개떡만들기 체험", startDate, endDate, 2, 100, 10000, 1, "#농촌체험#쑥개떡#봄나물#쑥#요리체험", "봄나물 쑥을이용해 떡을 만들어 보는 체험입니다.", "봄나물 쑥을 이용해 떡을 만들어 보는 체험", "없음", "--", expRoundDtoList);
        CreateExpDto createExpDto1_4 = new CreateExpDto(ExperienceType.CRAFTING, "블루베리호떡만들기 체험 ", startDate, endDate, 4, 40, 10000, 1, "1.지역에서 수확한 신선한 재료 체험\n2.달달함 더하기 달달함은 맛있다~\n3.간단한 요리체험으로 자신감 업!",
                "따뜻한 겨울간식 호떡을 먹고싶을땐? 직접 반죽하고 구워서 따끈따끈 할때 먹으면 맛있는 호떡,가족, 연인, 친구 누구나 방문해도 만족할 수 있는 산들강웅포마을에 어서오세요~", "달달한 호떡과 새콤달콤한 블루베리가 만났다~", "친구, 연인, 가족과 함께 하기 위한 배려하는 마음, 원할한 체험 진행을 위한 집중!", "뜨거운 조리도구를 사용하기 때문에 화상 주의", expRoundDtoList);

        experienceService.create(seller1, createExpDto1_1);
        experienceService.create(seller1, createExpDto1_2);
        experienceService.create(seller1, createExpDto1_3);
        experienceService.create(seller1, createExpDto1_4);

        CreateRoomDto createRoomDto1_1 = new CreateRoomDto(RoomType.VILLAGE, "3인실(별관)", "자연이 주는 평화로움", 40000, 40000, 40000, 3, 3, 10000, checkinTime, checkoutTime, 1, "온돌", "에어컨, TV, 옷장, 냉장고, 샴푸, 린스, 비누", "미성년자는 보호자 동반없이 이용하실 수 없습니다.", "-호젓한 농촌경치를 보며 즐길 수 있는 야외 바비큐장이 준비되어 있습니다.\n-객실 내부는 편백나무로 마감되어 음식 조리를 금하고 있습니다. 공용주방을 이용해 주세요.", "1.서해안 7대 낙조 중 하나인 금강낙조를 감상해 보세요\n2.통창을 통한 논뷰(view)도 놓치지 마세요.", "반려동물 동반입실이 불가합니다.");
        CreateRoomDto createRoomDto1_2 = new CreateRoomDto(RoomType.VILLAGE, "5인실(별관)", "자연과 가족과 함께", 80000, 80000, 80000, 5, 5, 10000, checkinTime, checkoutTime, 2, "온돌,화장실 2개", "에어컨, TV, 옷장, 냉장고, 샴푸, 린스, 비누", "미성년자는 보호자 동반없이 이용하실 수 없습니다.", "-호젓한 농촌경치를 보며 즐길 수 있는 야외 바비큐장이 준비되어 있습니다.\n-객실 내부는 편백나무로 마감되어 음식 조리를 금하고 있습니다. 공용주방을 이용해 주세요.", "1.서해안 7대 낙조 중 하나인 금강낙조를 감상해 보세요\n2.마을에서 자전거를 빌려 곰개나루 자전거길을 달려보세요. 정말 예뻐요. 석양질 때 추천!", "전 객실 금연입니다.");
        CreateRoomDto createRoomDto1_3 = new CreateRoomDto(RoomType.VILLAGE, "6인실(본관)", "자연이 주는 평화로움", 150000, 150000, 150000, 6, 6, 10000, checkinTime, checkoutTime, 5, "거실,온돌,화장실 2개", "에어컨, TV, 옷장, 냉장고, 식탁, 샴푸, 린스, 비누, 이불, 화장지", "미성년자는 보호자 동반없이 이용하실 수 없습니다.", "-호젓한 농촌경치를 보며 즐길 수 있는 야외 바비큐장이 준비되어 있습니다.\n-객실 내부는 편백나무로 마감되어 음식 조리를 금하고 있습니다. 공용주방을 이용해 주세요.", "1.서해안 7대 낙조 중 하나인 금강낙조를 감상해 보세요\n2.통창을 통한 논뷰(view)도 놓치지 마세요.", "전 객실 금연입니다.");
        CreateRoomDto createRoomDto1_4 = new CreateRoomDto(RoomType.VILLAGE, "4인실(침대방)", "자연경치를 침대에서", 120000, 120000, 120000, 4, 4, 10000, checkinTime, checkoutTime, 4, "거실,온돌,화장실 2개", "에어컨, TV, 옷장, 냉장고, 식탁, 샴푸, 린스, 비누, 이불, 화장지", "미성년자는 보호자 동반없이 이용하실 수 없습니다.", "-호젓한 농촌경치를 보며 즐길 수 있는 야외 바비큐장이 준비되어 있습니다.\n-객실 내부는 편백나무로 마감되어 음식 조리를 금하고 있습니다. 공용주방을 이용해 주세요.\n-농구장과 족구장이 있습니다. 사용 시 마을에 문의해주세요.", "1.마을에서 자전거를 빌려 곰개나루 자전거길을 달려보세요. 정말 예뻐요. 석양질 때 추천!\n2.통창을 통한 논뷰(view)도 놓치지 마세요.", "반려동물 동반입실이 불가합니다.");
        CreateRoomDto createRoomDto1_5 = new CreateRoomDto(RoomType.VILLAGE, "별관(다목적실)", "자연이 주는 평화로움", 200000, 200000, 200000, 10, 15, 10000, checkinTime, checkoutTime, 1, "별관, 화장실 2개", "에어컨, TV, 옷장, 냉장고, 식탁, 샴푸, 린스, 비누, 이불, 화장지", "미성년자는 보호자 동반없이 이용하실 수 없습니다.", "-호젓한 농촌경치를 보며 즐길 수 있는 야외 바비큐장이 준비되어 있습니다.\n-객실 내부는 편백나무로 마감되어 음식 조리를 금하고 있습니다. 공용주방을 이용해 주세요.\n-농구장과 족구장이 있습니다. 사용 시 마을에 문의해주세요.", "1.마을에서 자전거를 빌려 곰개나루 자전거길을 달려보세요. 정말 예뻐요. 석양질 때 추천!\n2.통창을 통한 논뷰(view)도 놓치지 마세요.", "반려동물 동반입실이 불가합니다.");

        roomService.create(seller1,createRoomDto1_1);
        roomService.create(seller1,createRoomDto1_2);
        roomService.create(seller1,createRoomDto1_3);
        roomService.create(seller1,createRoomDto1_4);
        roomService.create(seller1,createRoomDto1_5);



        //2번. 동편제마을
        SellerJoinDto sellerJoinDto2 = new SellerJoinDto("010-1111-1113", "01-1234-1234", "seller2", "testSeller2", "testSeller2@email.com", "seller2", "seller2", "전북 남원시 운봉읍 가산화수길 51-7", "063-625-3183, 010-6723-3183", AreaCode.JEONBUK, BankCode.KOREA, "02-898223-91", "testSeller2");
        Seller seller2 = sellerService.join(sellerJoinDto2);

        CreateTourDto createTourDto2 = new CreateTourDto(TourType.VILLAGE, "동편제마을", "http://dongpyeonje.co.kr/","남원은 판소리 다섯 마당 중 춘향가와 흥부가의 배경지가 될 만큼 예로부터 국악의 산실이었습니다. 그리고 동편제마을은 오늘날 동편제 판소리를 정형화한 가왕 송흥록과 박초월 명창이 태어난 유서 깊은 곳이기도 합니다. 지리산 자락의 풍경과 판소리 명인들의 고향이자 동편제의 태동지라 불리며 매년 국악축제가 열리는 판소리의 고장으로 자연과 전통이 함께하는 곳입니다. 소나무 숲길에서 힐링하며 한옥을 모던하게 재해석한 숙소로 전통의 미를 살리고 다양한 체험을 경험할 수 있습니다.", "동편제의 역사와 전통을 잇다", "지리산 둘레길 2코스가 지나는 동편제마을은 판소리 동편제의 태동지로, 매년 ‘동편제마을 국악 거리축제’가 열리는 소리와 예술이 살아있는 마을입니다. 한옥을 재해석한 ㄷ자 형의 게스트하우스 ‘휴(休)’는 공간이 주는 독특한 재미를 맛볼 수 있으며, 모던한 한옥 인테리어와 호텔식 침구, 정성이 느껴지는 어메니티로 잊을 수 없는 하룻밤을 만들어 줍니다.", "메뉴정보: 지리산 흑돈 바베큐, 동편제밥상, 흑돈 돈까스", "30대", "남녀 화장실", "족구장, 바비큐장, 카페, 야외잔디밭, 야외음향시설, 소나무숲, 숲야간조명, 숲쉼터 등");
        tourService.create(seller2, createTourDto2);

        CreateExpDto createExpDto2_1 = new CreateExpDto(ExperienceType.CRAFTING, "고소한 들깨초콜릿만들기", startDate, endDate, 2, 30, 14000, 2, "1.우리 농산물을 이용해 만드는 초콜렛\n2.우리 아이들에게 내가 만든 초콜렛이라는 자부심을 넣어주세요",
                "들깨초콜릿 체험은 잘 볶은 고소한 들깨의 향과 달콤하게 펴져가는 진한 카카오(초콜릿)의 향기가 어루러져 갈때쯤 따뜻한 물에 녹인 초콜릿 안에 들깨를 넣어 조물조물 잘 섞어 원하는 몰드안에 넘치지 않게 살짝 짜서 채워주고 15분간 잘 굳혀주면 바삭바삭한 크런치 식감에 고소하면서 달콤한 초콜릿이 만들어집니다. \n" +
                        "들깨 초콜릿 만들기 체험은 너무 달지 않고 고소하면서 바삭한 식감때문에 어린친구부터 부모님들까지 모두 만족하면서 체험하고 맛볼 수 있습니다.", "달콤하고 즐거운 초콜릿체험", "없음", "예약 후 전화주세요", expRoundDtoList);
        CreateExpDto createExpDto2_2 = new CreateExpDto(ExperienceType.CULTURE, "산양교감체험 + 쿠킹교실", startDate, endDate, 5, 30, 18000, 2, "1.산양의 따뜻한 눈빛과 체온을 나누며 자연과 동물을 소중하게 여기는 아이로 자랄 수 있습니다.\n2.아담하고 예쁜 농장에서 귀여운 산양과 함께 사진을 찍을 수 있습니다.\n3.마음의 휴식 뿐만 아니라 직접 맛있는 피자를 만들고 먹을 수 있어서 몸과 마음이 모두 힐링됩니다.", "산양체험은 직접 먹이를 주면서 동물과 친해지는 방법을 알고 천천히 동물에게 다가가는 방법을 배워인내심을 기를 수 있는 시간이 되며 동물의 소중함을 알게하고 생명의 존중을 교육시켜줄 수 있는 체험입니다. 동물과 친해지는 방법을 배우면 사회에 나가서 친구를 사귀는 방법도 알 수 있고 친구에게 다가가는 방법도 배워 배려하는 마음가짐도 배울 수 있는 교육적이며 활동적인 체험입니다.",
                "푸른 풀밭의 산양을 보며 자연을 느끼고, 친구들과 함께 팜피자를 만든다.", "야외활동으로 편안한 신발과 옷", "쾌적한 체험활동을 위해 대형주차장 ( 남원시 운봉읍 가산화수길 71 ) 을 이용해주세요. 안내간판을 따라 100m 걸어오시면 됩니다.", expRoundDtoList);

        experienceService.create(seller2, createExpDto2_1);
        experienceService.create(seller2, createExpDto2_2);


        CreateRoomDto createRoomDto2_1 = new CreateRoomDto(RoomType.VILLAGE, "2인실(침대실)", "한옥을 재해석하여 모던함을 더한 한옥 숙소", 180000, 180000, 180000, 2, 5, 10000, checkinTime, checkoutTime, 2, "온돌(거실1+방1+화장실1+테라스)", "옷장, 화장대, 냉장고, 에어컨, 와인잔, 사각접시, 물잔, 전기 주전자, 헤어드라이어, 수건, 비누, 샴푸, 컨디셔너, 바디워시", "-침구 추가 : 인원 추가 비용에 포함입니다.\n-미성년자는 보호자 동반없이 이용하실 수 없습니다.", "-객실 내에서 음식 조리가 불가합니다. 공용주방이 마련되어 있으니 먹을 것만 챙겨주세요.\n-공용주방 비품 : 전자렌지, 인덕션, 냉장고, 집기류, 그릇, 밥솥",
                "-숙소 주변에는 아름다운 산책길이 있습니다. 자전거를 빌려드리니 상쾌한 아침 산책을 추천드립니다.\n-고품질 지리산 흑돈 버크셔K를 만나볼 수 있는 곳입니다. 하몽, 생햄, 흑돈샤브샤브로 다양하게 맛보세요.", "-여가시간에는 함께 오시는 분들과 충분히 대화를 나누는 시간을 드리고자 TV가 설치되어 있지 않습니다.\n반려동물 동반 입실이 불가합니다.\n오후 10시이후에는 외부와 실내 공용공간이 소등되니 과도한 음주와 소음 유발은 주의해주세요.");
        CreateRoomDto createRoomDto2_2 = new CreateRoomDto(RoomType.VILLAGE, "4인실", "한옥에서의 여유로운 하루", 180000, 180000, 200000, 4, 6, 10000, checkinTime, checkoutTime, 2, "온돌(방1+방1+화장실1+테라스)", "옷장, 화장대, 냉장고, 에어컨, 와인잔, 사각접시, 물잔, 전기 주전자, 헤어드라이어, 수건, 비누, 샴푸, 컨디셔너, 바디워시", "-침구 추가 : 인원 추가 비용에 포함입니다.\n-미성년자는 보호자 동반없이 이용하실 수 없습니다.", "-객실 내에서 음식 조리가 불가합니다. 공용주방이 마련되어 있으니 먹을 것만 챙겨주세요.\n-공용주방 비품 : 전자렌지, 인덕션, 냉장고, 집기류, 그릇, 밥솥",
                "-숙소 주변에는 아름다운 산책길이 있습니다. 자전거를 빌려드리니 상쾌한 아침 산책을 추천드립니다.\n-고품질 지리산 흑돈 버크셔K를 만나볼 수 있는 곳입니다. 하몽, 생햄, 흑돈샤브샤브로 다양하게 맛보세요.", "-여가시간에는 함께 오시는 분들과 충분히 대화를 나누는 시간을 드리고자 TV가 설치되어 있지 않습니다.\n반려동물 동반 입실이 불가합니다.\n오후 10시이후에는 외부와 실내 공용공간이 소등되니 과도한 음주와 소음 유발은 주의해주세요.");

        roomService.create(seller2,createRoomDto2_1);
        roomService.create(seller2,createRoomDto2_2);

    }
}
