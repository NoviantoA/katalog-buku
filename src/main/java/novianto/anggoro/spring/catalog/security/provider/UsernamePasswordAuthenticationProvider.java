package novianto.anggoro.spring.catalog.security.provider;

import lombok.AllArgsConstructor;
import novianto.anggoro.spring.catalog.service.AppUserService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {

    private final AppUserService appUserService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        UserDetails appUser = appUserService.loadUserByUsername(username);
        if(!passwordEncoder.matches(password, appUser.getPassword())){
            throw new BadCredentialsException("invalid.username.password");
        }
        return new UsernamePasswordAuthenticationToken(appUser, null, appUser.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
