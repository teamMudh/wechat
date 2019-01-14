package com.gnnt.demo.controller;

import com.gnnt.demo.entity.WxFrim;
import com.gnnt.demo.service.WxFrimService;
import com.gnnt.demo.util.HttpGetUtil;
import com.gnnt.demo.util.ResultJson;
import com.gnnt.demo.util.WxConstants;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Controller
public class WxFrimController {


    @Autowired
    private WxFrimService wxFrimService;
   

    @RequestMapping("/getOpenId")
    @ResponseBody
    public ResultJson getOpenId(HttpServletRequest request,@RequestParam(value = "js_code") String js_code) throws Exception{
        ResultJson ret = new ResultJson(request);
        ret.setReturnCode(-1l);
        ret.setMsg("操作失败");
        try{
//            String js_code = (String) request.getAttribute("js_code");
            System.out.println("js_code========="+js_code);
            Map params = new HashMap();
            params.put("appid", WxConstants.APPID);
            params.put("secret", WxConstants.APPSECRET);
            params.put("grant_type", "authorization_code");
            params.put("js_code", js_code);
            //https://api.weixin.qq.com/sns/jscode2session?appid=wx07b174d25ac98128&secret=5a9a63f4547d010bc3bbb7d3f73caabd&js_code=033b3QQz0dWFPe1g1YPz0K5HQz0b3QQF&grant_type=authorization_code
            String result = HttpGetUtil.httpRequestToString(
                    "https://api.weixin.qq.com/sns/jscode2session", params);
            if(result==null ||  result.equals("")){//判断请求结果是否为空
                System.out.println("result========="+result);
            }
            JSONObject jsonObject = JSONObject.fromObject(result);

            Object openid = jsonObject.get("openid");
            System.out.println(openid);

            Map data = new HashMap();
            data.put("openid",openid);


            ret.setReturnCode(1l);
            ret.setMsg("操作成功");
            ret.setData(data);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ret;
    }


    @RequestMapping("/frimAdd")
    @ResponseBody
    public ResultJson frimAdd(HttpServletRequest request,String openid,String frimJson) throws Exception{
        ResultJson ret = new ResultJson(request);
        ret.setReturnCode(-1l);
        ret.setMsg("操作失败");
        try{
            boolean isExists= wxFrimService.existsWxFrimByOpenId(openid);
            if(isExists){
                ret.setMsg("该用户已存在不用重复添加");
                return ret;
            }
            System.out.println(request.getQueryString());
            JSONObject jsonObject = JSONObject.fromObject(frimJson);
            WxFrim frim = (WxFrim)JSONObject.toBean(jsonObject,WxFrim.class);
            frim.setOpenId(openid);
            System.out.println("openid==="+openid);
            System.out.println(frimJson);
            System.out.println(jsonObject.get("nickName"));

//
            wxFrimService.saveWxFrim(frim);
            ret.setReturnCode(1l);
            ret.setMsg("操作成功");
        }catch (Exception e){
            e.printStackTrace();
        }
        return ret;
    }

   

}