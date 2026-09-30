package com.sp.jobportal.security;

import com.sp.jobportal.entity.JobPortalUser;
import com.sp.jobportal.repository.JobPortalUserRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Profile("!prod")
public class JobPortalNonProdUserNamePwdAuthenticationProvider implements AuthenticationProvider {

    private final PasswordEncoder passwordEncoder;
    private final JobPortalUserRepository jobPortalUserRepository;

    @Override
    public @Nullable Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        // Implement your authentication logic here (e.g., check against a database)
        JobPortalUser jobPortalUser = (jobPortalUserRepository.findJobPortalUserByEmail(username)).stream().findFirst()
                .orElseThrow(() -> new BadCredentialsException("user details not found for email: " + username));
        List<SimpleGrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority(jobPortalUser.getRole().getName()));
        return new UsernamePasswordAuthenticationToken(jobPortalUser, null, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }

}
