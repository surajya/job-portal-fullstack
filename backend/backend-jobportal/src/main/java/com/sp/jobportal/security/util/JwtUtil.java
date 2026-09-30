package com.sp.jobportal.security.util;

import com.sp.jobportal.constant.ApplicationConstants;
import com.sp.jobportal.entity.JobPortalUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@PropertySource(value = {"classpath:jwt.properties"})
public class JwtUtil {

    private final Environment env;

    @Value("${jwt.issuerName:Job Portal}")
    private final String issuerName;

    @Value("${jwt.subjectName:JWT Token}")
    private final String subjectName;

    @Value("${jwt.expirationTime:1}")
    private final long expirationDefaultTime;

    @Value("${jwt.prod.expirationTime:1}")
    private final long expirationProdTime;

    public String generateJwtToken(Authentication authentication) {
        List<String> listOfProfiles = Arrays.asList(env.getActiveProfiles());
        long expirationTime = expirationDefaultTime;
        if (listOfProfiles.contains("prod")) {
            expirationTime = expirationProdTime;
        }
        String jwtToken;
        String secret = env.getProperty(ApplicationConstants.JWT_SECRET_KEY,
                ApplicationConstants.JWT_SECRET_DEFAULT_VALUE);
        SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        var fetchedUser = (JobPortalUser) authentication.getPrincipal();
        jwtToken = Jwts.builder().issuer(issuerName).subject(subjectName)
                .claim("name", fetchedUser.getName())
                .claim("email", fetchedUser.getEmail())
                .claim("mobileNumber", fetchedUser.getMobileNumber())
                .claim("roles", authentication.getAuthorities().stream().map(
                        GrantedAuthority::getAuthority).collect(Collectors.joining(",")))
                .issuedAt(new java.util.Date())
                .expiration(new java.util.Date((new java.util.Date()).getTime() + expirationTime * 60 * 60 * 1000))
                .signWith(secretKey).compact();
        return jwtToken;
    }
}
