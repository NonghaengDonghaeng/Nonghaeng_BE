package tour.nonghaeng.domain.member.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import tour.nonghaeng.global.infra.enums.area.AreaCode;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = SellerJoinDto.class, name = "seller"),
        @JsonSubTypes.Type(value = UserJoinDto.class, name = "user")
})
@Getter
public class JoinDto {
    private AreaCode areaCode;
    private String phoneNumber;
    private String name;
    private String email;
    private String username;
    private String password;
    private String checkPassword;

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
