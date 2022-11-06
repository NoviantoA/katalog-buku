package novianto.anggoro.spring.catalog.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import novianto.anggoro.spring.catalog.security.filter.JWTAuthenticationFilter;
import novianto.anggoro.spring.catalog.security.filter.UsernamePasswordAuthenticationField;
import novianto.anggoro.spring.catalog.security.provider.JWTAuthenticationProvider;
import novianto.anggoro.spring.catalog.security.provider.UsernamePasswordAuthenticationProvider;
import novianto.anggoro.spring.catalog.security.util.JWTHeaderTokenExtractor;
import novianto.anggoro.spring.catalog.security.util.SkipPathRequestMatcher;
import novianto.anggoro.spring.catalog.security.util.TokeExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.Arrays;
import java.util.List;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String V1_URL = "/v1/**";
    private static final String V2_URL = "/v2/**";

    @Autowired
    private AuthenticationFailureHandler failureHandler;

    @Autowired
    private AuthenticationSuccessHandler successHandler;

    @Autowired
    private UsernamePasswordAuthenticationProvider authenticationProvider;

    @Autowired
    private JWTAuthenticationProvider jwtAuthenticationProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JWTHeaderTokenExtractor tokenExtractor;

    @Bean
    @Lazy
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(appUserService).passwordEncoder(passwordEncoder());
        auth.authenticationProvider(authenticationProvider);
        auth.authenticationProvider(jwtAuthenticationProvider);
    }

    protected JWTAuthenticationFilter buildJwtAuthFilter(List<String> pathToSkip, List<String> patternList){
        // get matcher
        SkipPathRequestMatcher matches = new SkipPathRequestMatcher(pathToSkip, patternList);
        JWTAuthenticationFilter filter = new JWTAuthenticationFilter(tokenExtractor, matches, failureHandler);
        filter.setAuthenticationManager(authenticationManager);
        return filter;
    }

    protected UsernamePasswordAuthenticationField buildUsernamePasswordAuthField(String loginEntryPoint){
        UsernamePasswordAuthenticationField filter = new UsernamePasswordAuthenticationField(loginEntryPoint, successHandler, failureHandler, objectMapper);
        filter.setAuthenticationManager(authenticationManager);
        return filter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // path yang bisa di skip
        List<String> permitAllEndpointList = Arrays.asList(
                "/v1/login"
        );
        // endpoint yang perlu proses authentikasi
        List<String> authenticateEndpointList = Arrays.asList(
                V1_URL,
                V2_URL
        );

        http.authorizeRequests().antMatchers(V1_URL, V2_URL).authenticated()
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(buildUsernamePasswordAuthField("/v1/login"), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(buildJwtAuthFilter(permitAllEndpointList, authenticateEndpointList), UsernamePasswordAuthenticationFilter.class);
    }
}
