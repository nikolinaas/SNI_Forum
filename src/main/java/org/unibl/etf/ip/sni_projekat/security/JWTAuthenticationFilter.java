package org.unibl.etf.ip.sni_projekat.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.unibl.etf.ip.sni_projekat.model.JWTUser;
import org.unibl.etf.ip.sni_projekat.model.Role;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter  extends OncePerRequestFilter {


    @Value("${authorization.token.secret}")
    private String tokenSecret;


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
        try{
            Claims claims = Jwts.parserBuilder().setSigningKey(tokenSecret).build().parseClaimsJws(token)
                    .getBody();

            JWTUser jwtUser = new JWTUser(Integer.valueOf(claims.getId()),claims.getSubject(), null, Role.valueOf(claims.get("role",String.class)));
            Authentication authentication = new UsernamePasswordAuthenticationToken(jwtUser,null,jwtUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }catch(Exception e){

        }
        filterChain.doFilter(request,response);
    }
}
