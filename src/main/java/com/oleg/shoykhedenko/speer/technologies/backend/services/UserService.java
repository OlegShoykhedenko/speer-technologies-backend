package com.oleg.shoykhedenko.speer.technologies.backend.services;

import com.oleg.shoykhedenko.speer.technologies.backend.dto.UserDto;
import com.oleg.shoykhedenko.speer.technologies.backend.entities.User;
import com.oleg.shoykhedenko.speer.technologies.backend.repositories.UserRepository;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public String registerUser(UserDto userDto) {
        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new IllegalArgumentException();
        }
        var user = User.builder()
                .username(userDto.getUsername())
                .password(new BCryptPasswordEncoder().encode(userDto.getPassword()))
                .build();
        user = userRepository.save(user);
        String jwt = createJwt(user.getId());
        user.setJwt(jwt);
        userRepository.save(user);
        return jwt;
    }

    private String createJwt(Long id) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary("SECRET_KEY");
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder().setId(id.toString())
                .setIssuedAt(now)
                .signWith(signatureAlgorithm, signingKey);

        //if it has been specified, let's add the expiration

        long expMillis = nowMillis + 20000000L;
        Date exp = new Date(expMillis);
        builder.setExpiration(exp);


        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }

    public String loginUser(UserDto user) {
        var savedUser = userRepository.findByUsername(user.getUsername())
                .orElseThrow(IllegalArgumentException::new);
        if (!BCrypt.checkpw(user.getPassword(), savedUser.getPassword())) {
            throw new IllegalArgumentException();
        }
        String token = createJwt(savedUser.getId());
        savedUser.setJwt(token);
        userRepository.save(savedUser);
        return token;

    }
}
