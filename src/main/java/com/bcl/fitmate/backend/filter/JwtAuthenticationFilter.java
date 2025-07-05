package com.bcl.fitmate.backend.filter;

import com.bcl.fitmate.backend.common.constants.ResponseCode;
import com.bcl.fitmate.backend.common.constants.ResponseMessage;
import com.bcl.fitmate.backend.config.security.UserPrincipal;
import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.provider.JwtProvider;
import com.bcl.fitmate.backend.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final static Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private final static ObjectMapper mapper = new ObjectMapper();
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws IOException, ServletException {
        try {
            String authorizationHeader = request.getHeader("Authorization");
            String token = (authorizationHeader != null && authorizationHeader.startsWith("Bearer "))
                    ? jwtProvider.removeBearer(authorizationHeader)
                    : null;

            if (token != null) {
                jwtProvider.validateJwtToken(token);

                Long userId = jwtProvider.getUserIdFromJwtToken(token);
                userRepository.findById(userId).ifPresent(user -> {
                    UserPrincipal userPrincipal = new UserPrincipal(user);
                    setAuthenticationContext(request, userPrincipal);
                });
            }
        } catch (Exception e) {
            String message = e.getMessage();
            String code = message.equals(ResponseMessage.TOKEN_EXPIRED)
                    ? ResponseCode.TOKEN_EXPIRED
                    : ResponseCode.INVALID_TOKEN;
            String jsonResponse = mapper.writeValueAsString(ResponseDto.fail(code, message));

            log.error("JWT 인증 필터 예외 발생: {}", e.getMessage(), e);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json; charset=utf-8");
            response.getWriter().write(jsonResponse);
        }

        filterChain.doFilter(request, response);
    }

    private void setAuthenticationContext(HttpServletRequest request, UserPrincipal userPrincipal) {
        Collection<? extends GrantedAuthority> authorities
                = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + userPrincipal.getRole().getName().name()));

        AbstractAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(userPrincipal, null, authorities);

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authenticationToken);
        SecurityContextHolder.setContext(securityContext);
    }
}
