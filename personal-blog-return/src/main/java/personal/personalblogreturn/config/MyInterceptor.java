package personal.personalblogreturn.config;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Ljh
 */

public class MyInterceptor implements HandlerInterceptor {
    /**
     * 拦截器
     *
     * @param request
     * @param response
     * @param handler
     * @return 是否通过拦截器
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object info = request.getSession().getAttribute("userInfo");
        if (info==null){
            RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
            redirectAttributes.addFlashAttribute("powerError", "没有权限请先登录");
            response.sendRedirect("/admin/login.html");
            return false;
        }

        return true;
    }




}
