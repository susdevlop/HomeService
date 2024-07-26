package us.sushome.hsweb.config;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.CorsFilter;
import us.sushome.common.constants.SecurityConstants;
import us.sushome.common.constants.UserRoleConstants;
import us.sushome.common.utils.JwtUtils;
import us.sushome.hsweb.filter.JwtAuthorizationFilter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import us.sushome.hsweb.handler.CustomAccessDeniedHandler;
import us.sushome.hsweb.handler.CustomEntryPoint;

import java.util.Collection;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {
    private static final Logger Logger = LoggerFactory.getLogger(SecurityConfiguration.class);
    @Autowired
    private CorsFilter corsFilter;

    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    @Autowired
    private AuthenticationConfiguration authenticationManager;

    @Autowired
    private CustomEntryPoint customEntryPoint;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //AuthenticationManager am = authenticationManager.getAuthenticationManager();
        JwtAuthorizationFilter jwtAuthorizationFilter = new JwtAuthorizationFilter();

        http
            .anonymous(anonymous -> anonymous.authorities(UserRoleConstants.GUEST_USER))
            .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
            //.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling(exceptions ->
                exceptions
                    .authenticationEntryPoint(customEntryPoint)
                    .accessDeniedHandler(customAccessDeniedHandler)
            )
            .csrf(csrf -> csrf.disable())
            .headers(headers ->
                headers
                    .addHeaderWriter((request, response) -> {
                        response.setHeader("X-Frame-Options", "DENY");
                    })
            )
            //.logout(logout -> logout.logoutUrl("/openApi/logout"))
            .authorizeHttpRequests(register -> register
                .requestMatchers("/").permitAll()
                .requestMatchers("/favicon.ico").permitAll()
                .requestMatchers("/error").permitAll()
                .requestMatchers(HttpMethod.POST, SecurityConstants.AUTH_LOGIN_URL).permitAll()
                .requestMatchers(HttpMethod.POST, "/openApi/register").permitAll()
                .anyRequest().access((authentication, object) -> {
                    //表示请求的 URL 地址和数据库的地址是否匹配上了
                    boolean isMatch = false;
                    //获取当前请求的 URL 地址
                    HttpServletRequest request = object.getRequest();
                    String requestURI = request.getRequestURI();
                    System.out.println("requestURI:"+requestURI);

                    // 从 HTTP 请求中获取 token
                    String token = jwtAuthorizationFilter.getTokenFromHttpRequest(request);
                    Logger.info("请求中的 token："+token);

                    // 验证 token 是否有效
                    if (StringUtils.hasText(token)) {
                        try{
                            if(JwtUtils.validateToken(token)){
                                // 获取认证信息
                                Authentication auth = JwtUtils.getAuthentication(token);
                                // 将认证信息存入 Spring 安全上下文中
                                SecurityContextHolder.getContext().setAuthentication(auth);

                                //获取当前登录用户的角色
                                Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
                                System.out.println("\n 当前用户角色：\n"+authorities+"\n");
                                return new AuthorizationDecision(true);
                            }
                        }catch (RuntimeException e) {
                            Logger.info("dofilterInternal exception:"+e.getMessage());
                            //new CustomEntryPoint().commence(request,response,);
                            throw new AuthenticationException(e.getMessage()) {};
                            //throw new AuthenticationException(e.getMessage()) {};
                        }
                    }else{
                        throw new AuthenticationException("缺少token") {};
                    }
                    return new AuthorizationDecision(false);
                }))
            .sessionManagement(sessionManagement ->
                //不会创建或使用 HTTP 会话
                sessionManagement
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            );
        return http.build();
    }
}
