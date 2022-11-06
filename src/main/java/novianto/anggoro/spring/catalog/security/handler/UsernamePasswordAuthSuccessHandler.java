package novianto.anggoro.spring.catalog.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import novianto.anggoro.spring.catalog.security.model.AccessJWTToken;
import novianto.anggoro.spring.catalog.security.util.JWTTokenFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@AllArgsConstructor
public class UsernamePasswordAuthSuccessHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper objectMapper;
    private final JWTTokenFactory tokenFactory;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        AccessJWTToken token = tokenFactory.createAccessJWT(userDetails.getUsername(), userDetails.getAuthorities());
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("token", token.getToken());
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getWriter(), resultMap);
        clearAuthenticationAttribute(request);
    }

    protected final void clearAuthenticationAttribute(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session ==  null){
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
}
