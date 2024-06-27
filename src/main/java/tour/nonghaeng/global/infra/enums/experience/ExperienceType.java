package tour.nonghaeng.global.infra.enums.experience;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import tour.nonghaeng.global.infra.exception.GlobalException;
import tour.nonghaeng.global.infra.exception.error.GlobalErrorCode;

@RequiredArgsConstructor
@Getter
public enum ExperienceType {
    RURAL("1","농촌체험"),
    CRAFTING("2","만들기체험"),
    CULTURE("3","문화체험"),
    LEISURE_SPORTS("4","레포츠체험"),
    ETC("5","그 외")
    ;

    @JsonValue
    private final String code;
    private final String name;

    public static ExperienceType ofCode(String code){
        if(code==null){
            throw new GlobalException(GlobalErrorCode.CODE_IS_NULL_ERROR);
        }
        for(ExperienceType et : ExperienceType.values()){
            if(et.getCode().equals(code)){
                return et;
            }
        }
        throw new GlobalException(GlobalErrorCode.NO_MATCH_EXPERIENCE_TYPE);
    }
}
