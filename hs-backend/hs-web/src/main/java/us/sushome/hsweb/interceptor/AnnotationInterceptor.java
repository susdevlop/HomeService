package us.sushome.hsweb.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import us.sushome.hsweb.annotation.RequiresPermissions;

import java.lang.reflect.Method;


import java.lang.reflect.Method;
import java.util.Arrays;

//@Component
//public class AnnotationInterceptor implements HandlerInterceptor {
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        if (handler instanceof HandlerMethod) {
//            HandlerMethod handlerMethod = (HandlerMethod) handler;
//            Method method = handlerMethod.getMethod();
//
//            // 检查方法上是否有 @RequiresPermissions 注解
//            RequiresPermissions annotation = method.getAnnotation(RequiresPermissions.class);
//            if (annotation != null) {
//                System.out.println("当前 controller 存在requiresPermissions注解"+ annotation.value());
//                // 将注解的值保存到请求属性中
//                request.setAttribute("RequiresPermissionsAttribute", annotation.value());
//            }
//        }
//        return true;
//    }
//}
