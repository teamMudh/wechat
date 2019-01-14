package com.gnnt.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gnnt.demo.util.ResultJson;

import javax.servlet.http.HttpServletRequest;

@Controller
public class TestController {
   

    @RequestMapping("/testData")
    @ResponseBody
    public ResultJson login(HttpServletRequest request) throws Exception{
        System.out.println("HomeController.login()");
        ResultJson ret = new ResultJson(request);
		ret.setReturnCode(1l);
		ret.setMsg("收藏成功");
        return ret;
    }

   

}