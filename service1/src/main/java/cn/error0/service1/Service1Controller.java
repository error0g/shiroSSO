package cn.error0.service1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Service1Controller {


    @ResponseBody
    @RequestMapping("/")
    public String Service1() {
        return  "enter Service1";
    }

}
