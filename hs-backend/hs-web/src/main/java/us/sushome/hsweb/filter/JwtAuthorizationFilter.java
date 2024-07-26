package us.sushome.hsweb.filter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import us.sushome.common.constants.SecurityConstants;
import us.sushome.common.utils.JwtUtils;
import us.sushome.hsweb.handler.CustomEntryPoint;

import java.io.IOException;
import java.rmi.RemoteException;

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

    //private RequestMappingHandlerMapping requestMappingHandlerMapping;
    //
    //private final AuthenticationManager authenticationManager;
    //public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
    //    this.authenticationManager = authenticationManager;
    //    this.requestMappingHandlerMapping = new RequestMappingHandlerMapping();
    //}

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, jakarta.servlet.FilterChain filterChain) throws jakarta.servlet.ServletException, IOException {
        // 从 HTTP 请求中获取 token
        //String token = getTokenFromHttpRequest(request);
        //logger.info("请求中的 token："+token);
        //
        //// 验证 token 是否有效
        //if (StringUtils.hasText(token)) {
        //    try{
        //        if(JwtUtils.validateToken(token)){
        //            // 获取认证信息
        //            Authentication authentication = JwtUtils.getAuthentication(token);
        //            // 将认证信息存入 Spring 安全上下文中
        //            SecurityContextHolder.getContext().setAuthentication(authentication);
        //        }
        //    }catch (RuntimeException e) {
        //        logger.info("dofilterInternal exception:"+e.getMessage());
        //        new CustomEntryPoint().commence(request,response,new AuthenticationException(e.getMessage()) {});
        //        return;
        //        //throw new AuthenticationException(e.getMessage()) {};
        //    }
        //}
        // 放行请求
        filterChain.doFilter(request, response);
    }
    /**
     * 从 HTTP 请求中获取 token
     *
     * @param request HTTP 请求
     * @return 返回 token
     */
    public String getTokenFromHttpRequest(jakarta.servlet.http.HttpServletRequest request) {
        String authorization = request.getHeader(SecurityConstants.TOKEN_HEADER);
        if (authorization == null || !authorization.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            return null;
        }
        // 去掉 token 前缀
        return authorization.replace(SecurityConstants.TOKEN_PREFIX, "");
    }


}
