package tour.nonghaeng.domain.member.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = SellerJoinDto.class, name = "seller"),
        @JsonSubTypes.Type(value = UserJoinDto.class, name = "user")
})
public abstract class JoinDto {

    public abstract String toString();

}
