package HelpTodo.helptodoBackend.config;

import HelpTodo.helptodoBackend.service.MemberService;
import HelpTodo.helptodoBackend.util.JwtUtil;
import java.io.IOException;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final MemberService memberService;
    private final String secretKey;

    @Override
    protected void doFilterInternal(
        HttpServletRequest request, HttpServletResponse response, FilterChain filterChain
    ) throws ServletException, IOException {

        // 토큰 받기
        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info("authorization : {}", authorization);

        if (authorization == null || !authorization.startsWith("bearer ")) {

            log.error("authorization 올바르지 않음");
            filterChain.doFilter(request, response);
            return;
        }

        //토큰 꺼냄
        String token = authorization.split(" ")[1];

        //토큰 Expire 체크
        if (JwtUtil.isExpired(token, secretKey)) {

            log.error("token 만료됨");
            filterChain.doFilter(request, response);
            return;
        }

        //userName 토큰 꺼냄
        String userName = JwtUtil.getUserName(token, secretKey);
        log.info("userName: {}", userName);

        //권한 부여
        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(userName,
                                                    null,
                                                    List.of(new SimpleGrantedAuthority("USER")));

        //Detail 삽입
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}
