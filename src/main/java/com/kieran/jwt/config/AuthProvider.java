package com.kieran.jwt.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.kieran.jwt.dto.UserDto;
import com.kieran.jwt.entity.User;
import com.kieran.jwt.exception.AppException;
import com.kieran.jwt.mapper.UserMapper;
import com.kieran.jwt.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Collections;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class AuthProvider {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    @Value("${security.jwt.token.secret-key:secret-key}")
    private String secretKey;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(UserDto dto) {
        var now = new Date();
        var valid = new Date(now.getTime() + 3_600_000);
        return JWT.create()
                .withIssuer(dto.getUsername())
                .withIssuedAt(now)
                .withExpiresAt(valid)
                .withClaim("firstName", dto.getFirstName())
                .withClaim("lastName", dto.getLastName())
                .sign(Algorithm.HMAC256(secretKey));
    }

    public Authentication validateToken(String token) {
        var algorithm = Algorithm.HMAC256(secretKey);
        var verifier = JWT.require(algorithm).build();
        var decoder = verifier.verify(token);
        var user = UserDto.builder()
                .username(decoder.getIssuer())
                .firstName(decoder.getClaim("firstName").asString())
                .lastName(decoder.getClaim("lastName").asString())
                .build();
        return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
    }

    public Authentication validateTokenStrong(String token) {
        var algorithm = Algorithm.HMAC256(secretKey);
        var verifier = JWT.require(algorithm).build();
        var decoder = verifier.verify(token);
        User user = userRepository.findByUsername(decoder.getIssuer())
                .orElseThrow(() -> new AppException("unknown user", HttpStatus.NOT_FOUND));
        return new UsernamePasswordAuthenticationToken(userMapper.toUserDto(user),
                null, Collections.emptyList());
    }
}
