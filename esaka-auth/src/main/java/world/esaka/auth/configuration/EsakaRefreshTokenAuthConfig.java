package world.esaka.auth.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;
import world.esaka.auth.filter.EsakaTokenAuthenticationFilter;

@Configuration
@Order(2)
public class EsakaRefreshTokenAuthConfig extends WebSecurityConfigurerAdapter {

    private final AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> service;

    @Autowired
    public EsakaRefreshTokenAuthConfig(@Qualifier("esakaRefreshTokenUserDetailsService") AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> service) {
        this.service = service;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .antMatcher("/token/refresh")
                .authorizeRequests()
                .anyRequest()
                .authenticated()
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
        provider.setPreAuthenticatedUserDetailsService(service);
        return provider;
    }

}
