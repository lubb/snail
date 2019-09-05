package com.zlll.winner.interceptor;

import com.zlll.winner.LoginUser;
import com.zlll.winner.business.model.user.SysUser;
import com.zlll.winner.util.ThreadLocalUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    private final String LOGIN_USER_SESSION_KEY = "user";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        return super.preHandle(request, response, handler);
        System.out.println(request.getRequestURI());
        if (handler instanceof HandlerMethod) {
            //注解在方法上
            LoginUser loginUserAnnotation = ((HandlerMethod) handler).getMethodAnnotation(LoginUser.class);
            //注解在类上
            LoginUser classLoginUserAnnotation = ((HandlerMethod) handler).getMethod().getDeclaringClass().getAnnotation(LoginUser.class);
            if (ObjectUtils.anyNotNull(loginUserAnnotation, classLoginUserAnnotation)) {
                HttpSession session = request.getSession(false);
                //session不为空
                if (ObjectUtils.allNotNull(session)) {
                    SysUser user = (SysUser)session.getAttribute(LOGIN_USER_SESSION_KEY);
//                    String loginUser = (String) session.getAttribute(LOGIN_USER_SESSION_KEY);
                    if (ObjectUtils.allNotNull(user)) {
                        System.out.println("当前登陆用户为：" + user);
                        //将当前用户的信息存入threadLocal中
                        ThreadLocalUtils.put(Thread.currentThread().getName(),user);
                    } else {
                        System.out.println("用户不存在");
                        throw new LoginException("用户不存在，请登录");
                    }
                } else {
                    //session为空，用户未登录
                    throw new LoginException("未登录，请登录");
                }
            }
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ThreadLocalUtils.remove(Thread.currentThread().getName());
    }
}
