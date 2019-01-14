package com.gnnt.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


import com.gnnt.demo.entity.User;
import com.gnnt.demo.service.UserService;

/**
 * Created by Song on 2017/2/15.
 * User
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/index")
    public String index(){
    	System.out.println(111);
        return "user/index";
    }

    @RequestMapping(value = "/show")
    @ResponseBody
    public String show(@RequestParam(value = "name")String name){
        User user = userService.findUserByName(name);
        if(null != user)
            return user.getId()+"/"+user.getName()+"/"+user.getPassword();
        else return "null";
    }
    
    
    @RequestMapping(value = "/test")
    public ModelAndView test(@RequestParam(value = "name")String name){
    	ModelAndView demo = new ModelAndView("user/test");
        User user = userService.findUserByName(name);
        demo.addObject(user);
        return demo;
    }
    
    
}