package novianto.anggoro.spring.catalog.security.model;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class JWTAuthenticationToken extends AbstractAuthenticationToken {

    private RawAccessJWTToken rawAccessJWTToken;
    private UserDetails userDetails;

    public JWTAuthenticationToken(RawAccessJWTToken rawAccessJWTToken) {
        super(null);
        this.rawAccessJWTToken = rawAccessJWTToken;
        this.setAuthenticated(false);
    }

    public JWTAuthenticationToken(UserDetails userDetails, Collection<? extends GrantedAuthority> authorities ) {
        super(authorities);
        this.eraseCredentials();
        this.userDetails = userDetails;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return this.rawAccessJWTToken;
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        this.rawAccessJWTToken = null;
    }

    @Override
    public Object getPrincipal() {
        return this.userDetails;
    }
}
