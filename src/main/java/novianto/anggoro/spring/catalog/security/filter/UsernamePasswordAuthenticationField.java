package novianto.anggoro.spring.catalog.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import novianto.anggoro.spring.catalog.dto.LoginRequestDTO;
import novianto.anggoro.spring.catalog.exception.BadRequestException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UsernamePasswordAuthenticationField extends AbstractAuthenticationProcessingFilter {

    private final AuthenticationSuccessHandler successHandler;
    private final AuthenticationFailureHandler failureHandler;
    private final ObjectMapper objectMapper;

    public UsernamePasswordAuthenticationField(String defaultFilterProcessesUrl, AuthenticationSuccessHandler successHandler, AuthenticationFailureHandler failureHandler, ObjectMapper mapper) {
        super(defaultFilterProcessesUrl);
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
        this.objectMapper = mapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        LoginRequestDTO dto = objectMapper.readValue(request.getReader(), LoginRequestDTO.class);
        if (StringUtils.isBlank(dto.getUsername()) || StringUtils.isBlank(dto.getPassword())){
            throw new BadRequestException("username.password.mustbe.provided");
        }
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());
        return this.getAuthenticationManager().authenticate(token);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        this.successHandler.onAuthenticationSuccess(request, response, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        this.failureHandler.onAuthenticationFailure(request, response, failed);
    }
}
