package com.axbg.shelf.security;

import com.axbg.shelf.entity.User;
import com.axbg.shelf.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthTokenFilter extends OncePerRequestFilter {
    private final UserService userService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest httpServletRequest, @NonNull HttpServletResponse httpServletResponse,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        String jwt = parseJwt(httpServletRequest);
        if (jwt != null && JwtUtils.validateJwtToken(jwt)) {
            String email = JwtUtils.getEmailFromJwt(jwt);
            Instant expirationDate = JwtUtils.getIssuedAt(jwt).toInstant();

            if (isAvailable(expirationDate, email)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, null, null);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(authentication);

                if (expirationDate.isBefore(LocalDateTime.now().plusDays(2).toInstant(ZoneOffset.ofHours(0)))) {
                    httpServletResponse.addCookie(JwtUtils.generateJwtCookie(JwtUtils.generateJwt(email)));
                }
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private String parseJwt(HttpServletRequest request) {
        Cookie jwtCookie = WebUtils.getCookie(request, "X-AUTH");
        return jwtCookie != null ? jwtCookie.getValue() : null;
    }

    private boolean isAvailable(Instant issuedAt, String email) {
        Optional<User> user = userService.findUserByEmail(email);
        return user.isPresent() && user.get().getLastReset().isBefore(issuedAt);
    }
}
