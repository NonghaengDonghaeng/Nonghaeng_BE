package tour.nonghaeng.domain.member.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import tour.nonghaeng.global.infra.enums.area.AreaCode;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = SellerJoinDto.class, name = "seller"),
        @JsonSubTypes.Type(value = UserJoinDto.class, name = "user"),
        @JsonSubTypes.Type(value = AdminJoinDto.class, name = "admin")
})
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
@Getter
public class JoinDto {
    private final AreaCode areaCode;
    private final String phoneNumber;
    private final String name;
    private final String email;
    private final String username;
    private final String password;
    private final String checkPassword;

    public JoinDto(AreaCode areaCode, String phoneNumber, String name, String email, String username, String password, String checkPassword) {
        this.areaCode = areaCode;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.checkPassword = checkPassword;
    }
}
