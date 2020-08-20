package cn.madeai.teach_case_share_website_v2.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        判断session里面是否有用户，没有的话重定向到error页面，给拦截掉
        if (request.getSession().getAttribute("user") == null) {
            request.setAttribute("message","您没有权限访问/admin,请登录。");
            request.getRequestDispatcher("/error-page").forward(request,response);
            return false;
        }
        return true;
    }
}
