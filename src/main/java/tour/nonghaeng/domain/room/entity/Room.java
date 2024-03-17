package tour.nonghaeng.domain.room.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tour.nonghaeng.domain.etc.BaseTimeEntity;
import tour.nonghaeng.domain.etc.room.RoomType;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.tour.entity.Tour;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ROOMS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Room extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tour_id")
    private Tour tour;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoomCloseDate> roomCloseDateList = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "room_type")
    private RoomType roomType;

    private String roomName;            //숙소이름

    private String summary;             //객실 간단소개

    private int pricePeak;              //성수기가격

    private int priceOffPeak;           //비성수기가격

    private int priceHoliday;           //주말.공휴일 가격

    private int standardCapacity;       //기준인원

    private int maxCapacity;            //최대인원

    private int additionalCost;         //인당 추가요금

    private LocalTime checkinTime;      //체크인 시간

    private LocalTime checkoutTime;     //체크아웃 시간

    private int numOfRoom;              //객실 수

    private String roomConfiguration;   //방 구성(화장실 1개, 거실1개, 방2개)

    private String inclusions;          //포함사항(객실 내 화장실, 무료 WIFI)

    private String requirement;         //구비사항(조리도구, 인덕션, 냉장고, 각종그릇, 에어컨, TV, 커피포트, 샴푸, 린스, 바디워시, 치약, 인터넷과 와이파이 가능)

    private String facilities;          //부대시설(식당)  이거 관광에도 있으니까 거기서 받아올지 고민

    private String usageTips;           //이용팁(시원하게 흐르는 요천강변이 가까워 산책을 즐기기 좋고 여름에 물놀이 하기 좋습니다.)

    private String precautions;         //유의사항(전 객실 금연입니다.)

    @Builder
    public Room(Tour tour, Seller seller, RoomType roomType, String roomName, String summary, int pricePeak, int priceOffPeak, int priceHoliday, int standardCapacity, int maxCapacity, int additionalCost, LocalTime checkinTime, LocalTime checkoutTime, int numOfRoom, String roomConfiguration, String inclusions, String requirement, String facilities, String usageTips, String precautions) {
        this.tour = tour;
        this.seller = seller;
        this.roomType = roomType;
        this.roomName = roomName;
        this.summary = summary;
        this.pricePeak = pricePeak;
        this.priceOffPeak = priceOffPeak;
        this.priceHoliday = priceHoliday;
        this.standardCapacity = standardCapacity;
        this.maxCapacity = maxCapacity;
        this.additionalCost = additionalCost;
        this.checkinTime = checkinTime;
        this.checkoutTime = checkoutTime;
        this.numOfRoom = numOfRoom;
        this.roomConfiguration = roomConfiguration;
        this.inclusions = inclusions;
        this.requirement = requirement;
        this.facilities = facilities;
        this.usageTips = usageTips;
        this.precautions = precautions;
    }

    public void addCloseDate(RoomCloseDate roomCloseDate) {
        this.roomCloseDateList.add(roomCloseDate);
    }

    public void removeCloseDate(RoomCloseDate roomCloseDate) {
        this.roomCloseDateList.remove(roomCloseDate);
    }
    //성수기 비성수기의 기준 내용 들어가야됨
    //객실 크기 몇평인지 정보도 넣어야됨
}
