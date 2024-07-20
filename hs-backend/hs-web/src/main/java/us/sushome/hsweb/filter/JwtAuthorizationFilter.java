package us.sushome.hsweb.filter;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import us.sushome.common.constants.SecurityConstants;
import us.sushome.common.utils.JwtUtils;
import us.sushome.hsweb.annotation.RequiresPermissions;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * JwtAuthorizationFilter 用户请求授权过滤器
 *
 * <p>
 * 提供请求授权功能。用于处理所有 HTTP 请求，并检查是否存在带有正确 token 的 Authorization 标头。
 * 如果 token 有效，则过滤器会将身份验证数据添加到 Spring 的安全上下文中，并授权此次请求访问资源。</p>
 *
 * @author star
 */
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final AuthenticationManager authenticationManager;
    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, jakarta.servlet.FilterChain filterChain) throws jakarta.servlet.ServletException, IOException {
        // 从 HTTP 请求中获取 token
        String token = getTokenFromHttpRequest(request);
        logger.info("请求中的 token："+token);
        // 验证 token 是否有效
        if (StringUtils.hasText(token) && JwtUtils.validateToken(token)) {
            // 获取认证信息
            Authentication authentication = JwtUtils.getAuthentication(token);
            // 将认证信息存入 Spring 安全上下文中
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        // 获取当前请求的处理方法
        Object handler = request.getAttribute(HandlerMapping.BEST_MATCHING_HANDLER_ATTRIBUTE);

        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();

            // 检查方法上是否有 @RequiresPermissions 注解
            RequiresPermissions annotation = method.getAnnotation(RequiresPermissions.class);
            if (annotation != null) {
                // 将注解的值保存到请求属性中
                request.setAttribute("RequiresPermissionsAttribute", annotation.value());
            }
        }
        // 放行请求
        filterChain.doFilter(request, response);
    }
    /**
     * 从 HTTP 请求中获取 token
     *
     * @param request HTTP 请求
     * @return 返回 token
     */
    private String getTokenFromHttpRequest(jakarta.servlet.http.HttpServletRequest request) {
        String authorization = request.getHeader(SecurityConstants.TOKEN_HEADER);
        if (authorization == null || !authorization.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            return null;
        }
        // 去掉 token 前缀
        return authorization.replace(SecurityConstants.TOKEN_PREFIX, "");
    }


}
