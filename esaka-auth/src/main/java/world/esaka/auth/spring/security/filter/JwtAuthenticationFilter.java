package world.esaka.auth.spring.security.filter;

import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import world.esaka.auth.service.jwt.JwtService;

import javax.servlet.http.HttpServletRequest;

public class JwtAuthenticationFilter extends AbstractPreAuthenticatedProcessingFilter {


    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        return JwtService.getTokenFromAuthorizationHeader(request.getHeader(JwtService.AUTHORIZATION_HEADER_KEY));
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return JwtService.getTokenFromAuthorizationHeader(request.getHeader(JwtService.AUTHORIZATION_HEADER_KEY));
    }


}
