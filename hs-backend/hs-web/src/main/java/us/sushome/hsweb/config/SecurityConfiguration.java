package us.sushome.hsweb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;
import us.sushome.common.constants.SecurityConstants;
import us.sushome.hsweb.filter.JwtAuthorizationFilter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import us.sushome.hsweb.handler.CustomAccessDeniedHandler;

import java.util.Collection;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

    @Autowired
    private CorsFilter corsFilter;

    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    @Autowired
    private AuthenticationConfiguration authenticationManager;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        AuthenticationManager am = authenticationManager.getAuthenticationManager();
        JwtAuthorizationFilter jwtAuthorizationFilter = new JwtAuthorizationFilter(am);

        http
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exceptions ->
                        exceptions
                                //.authenticationEntryPoint(new Http403ForbiddenEntryPoint())
                                .accessDeniedHandler(customAccessDeniedHandler)
                )
                .csrf(csrf -> csrf.disable())
                .headers(headers ->
                        headers
                                .addHeaderWriter((request, response) -> {
                                    response.setHeader("X-Frame-Options", "DENY");
                                })
                )
                .logout(logout -> logout.logoutUrl("/openApi/logout"))
                //.authorizeRequests(authorizeRequests ->
                //        authorizeRequests
                //                .requestMatchers("/").permitAll()
                //                .requestMatchers(HttpMethod.POST, SecurityConstants.AUTH_LOGIN_URL).permitAll()
                //                .requestMatchers(HttpMethod.POST, "/openApi/register").permitAll()
                //                .anyRequest().authenticated()
                //)
                .authorizeHttpRequests(register -> register
                        .requestMatchers("/").permitAll()
                        .requestMatchers(HttpMethod.POST, SecurityConstants.AUTH_LOGIN_URL).permitAll()
                        .requestMatchers(HttpMethod.POST, "/openApi/register").permitAll()
                        .anyRequest().access((authentication, object) -> {
                            //表示请求的 URL 地址和数据库的地址是否匹配上了
                            boolean isMatch = false;
                            //获取当前请求的 URL 地址
                            String requestURI = object.getRequest().getRequestURI();
                            System.out.println("requestURI:"+requestURI);
                            //获取当前登录用户的角色
                            Collection<? extends GrantedAuthority> authorities = authentication.get().getAuthorities();
                            System.out.println("\n 当前用户角色：\n"+authorities+"\n");
                            return new AuthorizationDecision(false);
                        }))
                .sessionManagement(sessionManagement ->
                        sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );
        return http.build();
    }

}
