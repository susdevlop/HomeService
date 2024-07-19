package us.sushome.hsweb.handler;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {


    @Override
    public void handle(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, jakarta.servlet.ServletException {
        // 自定义处理逻辑，例如返回 403 错误码和消息
        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
    }
}
