package novianto.anggoro.spring.catalog.security.provider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.AllArgsConstructor;
import novianto.anggoro.spring.catalog.security.model.JWTAuthenticationToken;
import novianto.anggoro.spring.catalog.security.model.RawAccessJWTToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class JWTAuthenticationProvider implements AuthenticationProvider {

    private final Key key;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        RawAccessJWTToken token = (RawAccessJWTToken) authentication.getCredentials();
        Jws<Claims> claimsJws = token.parseClaim(key);
        String subject = claimsJws.getBody().getSubject();
        List<String> scopes = claimsJws.getBody().get("scopes", List.class);
        List<GrantedAuthority> authorities = scopes.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        UserDetails userDetails = new UserDetails() {

            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return authorities;
            }

            @Override
            public String getPassword() {
                return null;
            }

            @Override
            public String getUsername() {
                return subject;
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }
        };
        return new JWTAuthenticationToken(userDetails, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (JWTAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
