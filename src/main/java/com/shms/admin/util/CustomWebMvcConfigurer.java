package com.shms.admin.util;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Configuration

public class CustomWebMvcConfigurer implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/*");
    }

    public static class LoginInterceptor implements HandlerInterceptor {
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
            request.setCharacterEncoding("utf-8");
            response.setCharacterEncoding("utf-8");
            HttpSession session = request.getSession();
            //移除错误提示
            session.removeAttribute("alert_msg");
            //登录拦截
//            String uri = request.getRequestURI().substring(request.getRequestURI().lastIndexOf("/"));
//            String[] access = new String[]{"login", "logout", "register", ".css", ".js", ".png", ".jpg", "validationCode"};
//            for (String action : access) {
//                if (uri.toLowerCase().contains(action.toLowerCase())) {
//                    return true;
//                }
//            }
            Cookie[] cookies = request.getCookies();
            if (cookies == null) {
//                response.sendRedirect("/");
                session.setAttribute("alert_msg", "错误：请先登录！");
                return false;
            }
            for(Cookie cookie:cookies){
                if(cookie.getName().equals("userid")){
                    return true;
                }
            }
            response.sendRedirect("/");
            session.setAttribute("alert_msg", "错误：请先登录！");
            return false;
        }
    }
}