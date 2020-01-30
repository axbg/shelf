package com.axbg.shelf.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest httpServletRequest, @NonNull HttpServletResponse httpServletResponse,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        String jwt = parseJwt(httpServletRequest);

        if (jwt != null && JwtUtils.validateJwtToken(jwt)) {
            String email = JwtUtils.getEmailFromJwt(jwt);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, null, null);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private String parseJwt(HttpServletRequest request) {
        Cookie jwtCookie = WebUtils.getCookie(request, "X-AUTH");
        String jwt = jwtCookie != null ? jwtCookie.getValue() : null;

        if (jwt != null && !jwt.isEmpty()) {
            return jwt;
        }

        return null;
    }
}
