package tour.nonghaeng.global.auth.oauth;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import tour.nonghaeng.domain.etc.role.Role;

import java.util.Collection;
import java.util.Map;

@Getter
public class CustomOAuth2User extends DefaultOAuth2User {

    private String number;
    private Role role;

    public CustomOAuth2User(Collection<? extends GrantedAuthority> authorities,
                            Map<String, Object> attributes, String nameAttributeKey,
                            String number, Role role) {
        super(authorities, attributes, nameAttributeKey);
        this.number = number;
        this.role = role;
    }
}
