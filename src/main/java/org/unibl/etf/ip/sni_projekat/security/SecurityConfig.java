package org.unibl.etf.ip.sni_projekat.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.unibl.etf.ip.sni_projekat.model.Role;
import org.unibl.etf.ip.sni_projekat.services.JWTUserDetailsService;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@EnableWebSecurity
@Configuration
public class SecurityConfig  {

    private final JWTUserDetailsService jwtUserDetailsService;

    private final JWTAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JWTUserDetailsService jwtUserDetailsService, JWTAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }
    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(jwtUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }




    @Bean

    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        System.out.println(http.toString());



        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(HttpMethod.POST,"/api/authentication/login").permitAll().
                         requestMatchers(HttpMethod.POST,"/api/authentication/register").permitAll().
                        requestMatchers(HttpMethod.GET,"/api/authentication/**").permitAll().
                         requestMatchers(HttpMethod.GET,"/api/themes/get").hasAuthority("ADMIN").
                        requestMatchers(HttpMethod.GET,"/api/comments/notApproved").hasAnyAuthority("ADMIN","MODERATOR").
                        requestMatchers(HttpMethod.PUT,"/api/comments/approveComment/**").hasAnyAuthority("ADMIN","MODERATOR").
                        requestMatchers(HttpMethod.GET,"/api/comments/notApproved").hasAnyAuthority("ADMIN","MODERATOR").
                        requestMatchers(HttpMethod.GET,"/api/comments/approved/**").hasAnyAuthority("ADMIN","MODERATOR","USER").
                        requestMatchers(HttpMethod.POST,"/api/comments").hasAnyAuthority("ADMIN","MODERATOR","USER").
                        requestMatchers(HttpMethod.PUT,"/api/comments/**").hasAnyAuthority("ADMIN","MODERATOR","USER").
                        requestMatchers(HttpMethod.GET,"/api/users/**").hasAnyAuthority("ADMIN","MODERATOR","USER").
                        requestMatchers(HttpMethod.GET,"/api/users/unactive").hasAuthority("ADMIN").
                        requestMatchers(HttpMethod.PUT,"/api/users/**").hasAuthority("ADMIN").
                        requestMatchers(HttpMethod.GET,"/api/permissions/**").hasAuthority("ADMIN").
                        requestMatchers(HttpMethod.POST,"/api/authentication/verifyCode/**").permitAll().
                        requestMatchers(HttpMethod.POST,"/api/authentication/oauth2").permitAll()


                )
                .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
                .authenticationProvider(daoAuthenticationProvider())
                .addFilterBefore(
                        jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }





}
