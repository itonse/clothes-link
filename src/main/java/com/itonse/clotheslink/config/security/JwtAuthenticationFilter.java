package com.itonse.clotheslink.config.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.Authentication;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
/**
 * 모든 HTTP 요청에 대해 필터링을 한 번씩 수행합니다.
 * 주된 기능은 JWT 토큰의 유효성을 검사하고 인증 정보를 설정하는 것입니다.
 * 만약 권한이 없거나 유효하지 않은 토큰이 있으면 필터링 과정에서 처리됩니다.
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // Authorization(HEADER): Bearer(PREFIX) + eyJhbGci..(JWT토큰)
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = resolveToken(request);

        if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
            // 토큰이 존재하고 유효한 경우, 연결된 인증 정보 가져와서 현재 스레드의 SecurityContext에 인증 객체 설정
            Authentication auth = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
        } else {
            // 유효하지 않은 토큰이거나 토큰이 없는 경우, 해당 요청을 처리하는 스레드의 SecurityContext 초기화 (인증되지 않은 상태로 설정)
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }

    public String resolveToken(HttpServletRequest request) {
        // HTTP 요청의 RequestHeader 에 토큰이 포함되어 있다면 토큰 가져오기
        String token = request.getHeader(TOKEN_HEADER);

        if (!ObjectUtils.isEmpty(token) && token.startsWith(TOKEN_PREFIX)) {
            // Authorization: Bearer eyJhbGci.. 에서 Bearer 를 제외하고 토크값인 eyJhbGci.. 을 반환
            return token.substring(TOKEN_PREFIX.length());
        }

        return null;
    }
}
