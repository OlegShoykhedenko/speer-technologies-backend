package com.oleg.shoykhedenko.speer.technologies.backend.filters;

import com.oleg.shoykhedenko.speer.technologies.backend.entities.User;
import com.oleg.shoykhedenko.speer.technologies.backend.models.UserPrincipal;
import com.oleg.shoykhedenko.speer.technologies.backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;


@Component
@RequiredArgsConstructor
public class JwtSecurityFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtSecurityFilter.class);
    private final UserRepository userRepository;

    private static final String JWT_PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain) throws ServletException, IOException {

        try {
            String token = getJwt(request);

            if (token.isBlank()) {
                throw new RuntimeException("Authorization token not provided");
            }

            User user = userRepository.findById(1L).get();
//            IdentityUser user = new IdentityUser(token);

            if (user == null) {
                throw new RuntimeException("uid missing on token");
            }

            UserPrincipal principal = new UserPrincipal(user);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(principal, token, principal.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (Exception ex) {
            LOGGER.warn("Exception while authenticating request: ", ex);
        }

        chain.doFilter(request, response);
    }

    private String getJwt(HttpServletRequest request) {

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader.isBlank() || !authHeader.startsWith(JWT_PREFIX)) {
            return "";
        }

        return authHeader.replaceFirst(Pattern.quote(JWT_PREFIX), "");
    }
}
