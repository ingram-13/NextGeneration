package personal.personalblogreturn.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import personal.personalblogreturn.pojo.User;
import personal.personalblogreturn.service.UserService;
import personal.personalblogreturn.service.UserServiceImpl;

import javax.servlet.http.HttpSession;

/**
 * Created by Ljh
 */


@Controller
@RequestMapping("/admin")
public class loginController {

    @Autowired
    private UserService userService;

    /**
     * 校验管理员登录方法
     *
     * @param username 用户名
     * @param password 密码
     * @param session
     * @param redirectAttributes
     * @param model
     * @return 根据结果跳转至对应页面
     */
    @PostMapping("/goIndex")
    public String checkUser(@RequestParam(name = "username",required = false) String username,
                            @RequestParam(name = "password",required = false) String password,
                            HttpSession session,
                            RedirectAttributes redirectAttributes,
                            Model model){

        User user = userService.checkUser(username,password );
        System.out.println(user+"<=================");
        if (user!=null){
            user.setPassword(null);
            session.setAttribute("userInfo",user);
            redirectAttributes.addFlashAttribute("username",user.getNickName());
            return "redirect:/admin/index.html";
        }
        else {
            redirectAttributes.addFlashAttribute("loginError","用户名或密码错误" );
            return "redirect:/admin/login.html";
        }


    }


    /**
     * 管理员注销
     *
     * @param session
     * @return 重定向至登录页
     */
    @GetMapping("/logout")
    public String logout(HttpSession session){

        session.removeAttribute("userInfo");
        return "redirect:login.html";
    }
}
