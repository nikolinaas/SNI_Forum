package org.unibl.etf.ip.sni_projekat.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.unibl.etf.ip.sni_projekat.model.JWTUser;
import org.unibl.etf.ip.sni_projekat.model.Role;
import org.unibl.etf.ip.sni_projekat.services.JWTService;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {


    @Value("${authorization.token.secret}")
    private String tokenSecret;

    @Autowired
    private final JWTService jwtService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;
        if (StringUtils.isEmpty(authHeader) || !StringUtils.startsWith(authHeader, "Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.replace("Bearer", "");
        String user = jwtService.extractClaim(token, Claims::getSubject);
        System.out.print(user);
        if (user != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            try {
                Claims claims = Jwts.parserBuilder().setSigningKey(tokenSecret).build().parseClaimsJws(token)
                        .getBody();

                JWTUser jwtUser = new JWTUser(Integer.valueOf(claims.getId()), claims.getSubject(), null, Role.valueOf(claims.get("role", String.class)));
                if (jwtService.isTokenValid(token, jwtUser)) {
                    System.out.println("validan token");
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(jwtUser, null, jwtUser.getAuthorities());
                    authentication.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    System.out.println(authentication.isAuthenticated());
                }else{
                    System.out.println("nije validan token");
                }


            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

        }
        filterChain.doFilter(request, response);
    }
}
