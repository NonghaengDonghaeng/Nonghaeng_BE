package tour.nonghaeng.domain.etc.area;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum AreaCode {
    SEOUL("02","서울"),
    GYEONGGI("031","경기"),
    INCHEON("032","인천"),
    GANGWON("033","강원"),
    CHUNGNAME("041","충남"),
    CHUNGBUK("043","충북"),
    DAEJEON("042","대전"),
    SEJONG("044","세종"),
    BUSAN("051","부산"),
    ULSAN("052","울산"),
    DAEGU("053","대구"),
    GYEONGBUK("054","경북"),
    GYEONGNAM("055","경남"),
    JEONNAM("061","전남"),
    GWANGJU("062","광주"),
    JEONBUK("063","전북"),
    JEJU("064","제주"),
    ETC("000","선택안함")
    ;

    @JsonValue
    private final String code;
    private final String areaName;

    public static AreaCode ofCode(String code){
        if(code==null){
            throw new IllegalArgumentException();
        }
        for(AreaCode ac : AreaCode.values()){
            if(ac.getCode().equals(code)){
                return ac;
            }
        }
        throw new IllegalArgumentException("일치하는 지역번호가 없습니다.");
    }
}
