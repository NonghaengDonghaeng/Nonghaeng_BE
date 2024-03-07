package tour.nonghaeng.domain.etc.experienceType;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ExperienceType {
    RURAL("1","농촌체험"),
    CRAFTING("2","만들기체험"),
    CULTURE("3","문화체험"),
    LEISURE_SPORTS("4","레포츠체험"),
    ETC("5","그 외")
    ;

    private final String name;
    @JsonValue
    private final String code;

    public static ExperienceType ofCode(String code){
        if(code==null){
            throw new IllegalArgumentException();
        }
        for(ExperienceType et : ExperienceType.values()){
            if(et.getCode().equals(code)){
                return et;
            }
        }
        throw new IllegalArgumentException("일치하는 지역번호가 없습니다.");
    }
}
