package com.oleg.shoykhedenko.speer.technologies.backend.filters;

import com.oleg.shoykhedenko.speer.technologies.backend.entities.User;
import com.oleg.shoykhedenko.speer.technologies.backend.models.UserPrincipal;
import com.oleg.shoykhedenko.speer.technologies.backend.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
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
import javax.xml.bind.DatatypeConverter;
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
            System.out.println(token);

            Claims claims = decodeJWT(token);
            Long userId = Long.valueOf(claims.getId());
            User user = userRepository.findById(userId).orElseThrow();

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

    public Claims decodeJWT(String jwt) {
        //This line will throw an exception if it is not a signed JWS (as expected)
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary("SECRET_KEY"))
                .parseClaimsJws(jwt).getBody();
        return claims;
    }

    private String getJwt(HttpServletRequest request) {

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader.isBlank() || !authHeader.startsWith(JWT_PREFIX)) {
            return "";
        }

        return authHeader.replaceFirst(Pattern.quote(JWT_PREFIX), "");
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return "/user/register".equals(path);
    }
}
