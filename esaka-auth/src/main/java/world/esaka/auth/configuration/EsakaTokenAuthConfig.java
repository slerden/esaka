package world.esaka.auth.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;
import world.esaka.auth.filter.EsakaTokenAuthenticationFilter;

@Configuration
@Order(3)
public class EsakaTokenAuthConfig extends WebSecurityConfigurerAdapter {

    private static final String PATTERN = "/user/**";
    private final AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> userDetailsService;

    @Autowired
    public EsakaTokenAuthConfig(@Qualifier("esakaTokenAuthenticationUserDetailsService") AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(PATTERN).access("hasRole('U')")
                .antMatchers(HttpMethod.POST, "/user").anonymous()
                .and()
                .addFilterBefore(authFilter(), RequestHeaderAuthenticationFilter.class)
                .authenticationProvider(preAuthProvider())
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable();
    }


    @Bean
    public EsakaTokenAuthenticationFilter authFilter() throws Exception {
        EsakaTokenAuthenticationFilter esakaTokenAuthenticationFilter = new EsakaTokenAuthenticationFilter();
        esakaTokenAuthenticationFilter.setAuthenticationManager(authenticationManager());
        return esakaTokenAuthenticationFilter;
    }

    @Bean
    public AuthenticationProvider preAuthProvider() {
        PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
        provider.setPreAuthenticatedUserDetailsService(userDetailsService);
        return provider;
    }
}
