package novianto.anggoro.spring.catalog.security.filter;

import novianto.anggoro.spring.catalog.security.model.AnonymousAuthentication;
import novianto.anggoro.spring.catalog.security.model.JWTAuthenticationToken;
import novianto.anggoro.spring.catalog.security.model.RawAccessJWTToken;
import novianto.anggoro.spring.catalog.security.util.TokeExtractor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final TokeExtractor tokeExtractor;
    private final AuthenticationFailureHandler failureHandler;

    public JWTAuthenticationFilter(TokeExtractor tokeExtractor,RequestMatcher requiresAuthenticationRequestMatcher, AuthenticationFailureHandler failureHandler) {
        super(requiresAuthenticationRequestMatcher);
        this.tokeExtractor = tokeExtractor;
        this.failureHandler = failureHandler;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String tokenPayload = request.getHeader("Authorization"); //bearer zzz.yyy.zzz
        RawAccessJWTToken token = new RawAccessJWTToken(tokeExtractor.extract(tokenPayload));
        return this.getAuthenticationManager().authenticate(new JWTAuthenticationToken(token));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authResult);
        SecurityContextHolder.setContext(context);
        chain.doFilter(request, response);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        SecurityContextHolder.getContext().setAuthentication(new AnonymousAuthentication());
        failureHandler.onAuthenticationFailure(request, response, failed);
    }
}
