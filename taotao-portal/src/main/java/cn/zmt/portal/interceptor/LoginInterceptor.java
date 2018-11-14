package cn.zmt.portal.interceptor;

import cn.zmt.pojo.TbUser;
import cn.zmt.portal.service.impl.UserServiceImpl;
import cn.zmt.utils.CookieUtils;
import com.fasterxml.jackson.databind.ser.impl.FailingSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 赵铭涛
 * @creation time 2018/11/9 - 13:40
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private UserServiceImpl userService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        //在Handler执行之前处理
        //返回值决定handler是否执行，true：执行，false：不执行
        //判断用户是否登录
        //1从cookie中取token
        String token = CookieUtils.getCookieValue(httpServletRequest, userService.TT_TOKEN);
        //2根据token换区用户信息，调用sso系统的接口
        TbUser user = userService.getUserByToken(token);
        //3如果取不到用户信息
        if (null==user) {
            //4跳转到登录页面，把用户请求的url作为参数传递给登录页面
            httpServletResponse.sendRedirect(userService.SSO_BASE_URL + userService.SSO_PAGE_LOGIN+"?redirect"+httpServletRequest.getRequestURI());
            //5返回false
            return false;
        }
        //娶到用户信息 放行
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        //Handler执行之后，返回ModelAndViem之前执行
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
