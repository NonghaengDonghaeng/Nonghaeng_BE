package tour.nonghaeng.domain.member.data;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tour.nonghaeng.global.infra.enums.area.AreaCode;
import tour.nonghaeng.global.infra.enums.role.Role;

@Entity
@Table(name = "GUESTS")
@DiscriminatorValue("guest")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Guest extends Member{

    @Builder
    private Guest(Role role, String phoneNumber, String name, String email, String username, String password, String refreshToken, int point, AreaCode areaCode, boolean marketingConsent) {
        super(role, phoneNumber, name, email, username, password, refreshToken, point, areaCode, marketingConsent);
    }
}
