package com.zlll.winner.controller;

import com.zlll.winner.common.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class IndexController extends BaseController {

    @RequestMapping(value = {"", "/",})
    @ResponseBody
    public Object index(HttpServletResponse response) throws IOException {
        Map<String, Object> map = new HashMap<>();
        map.put("time", System.currentTimeMillis());
        map.put("message", "web项目启动成功");
        response.sendRedirect("main");
        return map;
    }

    @RequestMapping("main")
    public String main(){
        return "redirect:index.html";
    }

    @RequestMapping("/heartbeat")
    @ResponseBody
    public Object heartbeat(){
        Object userInfo = session.getAttribute("user");
        if(userInfo == null){
            return 1;
        }else{
            return 0;
        }
    }
}
