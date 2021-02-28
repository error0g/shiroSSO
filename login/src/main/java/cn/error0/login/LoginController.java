package cn.error0.login;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class LoginController {



    @RequestMapping("/login")
    @ResponseBody
    public String login(String username, String password) {

        if(username==null||password==null)
            return "请填写登录账号密码";

        String result="ok";
        System.out.println(username+" "+password);
        UsernamePasswordToken  UsernamePasswordToken=new UsernamePasswordToken(username,password);
        SecurityUtils.getSubject().getSession().setAttribute("token",UsernamePasswordToken);
       try {
           SecurityUtils.getSubject().login(UsernamePasswordToken);
       }catch (UnknownAccountException u)
       {
           result="登录失败！";
           return result;
       }
       System.out.println( SecurityUtils.getSubject().getSession().getId());
        return result;
    }

    @RequestMapping("/logout")
    @ResponseBody
    public String logout() {

        String result="ok";
        SecurityUtils.getSubject().logout();
        return result;
    }

}
