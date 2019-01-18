package com.gnnt.demo.controller;

import com.gnnt.demo.entity.WxFrim;
import com.gnnt.demo.entity.NewYearEvent;
import com.gnnt.demo.service.WxFrimService;
import com.gnnt.demo.util.HttpGetUtil;
import com.gnnt.demo.util.ResultJson;
import com.gnnt.demo.util.WxConstants;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.*;

@Controller
public class WxFrimController {


    @Autowired
    private WxFrimService wxFrimService;
   

    @RequestMapping("/getOpenId")
    @ResponseBody
    public ResultJson getOpenId(HttpServletRequest request,@RequestParam(value = "js_code") String js_code) throws Exception{
        System.out.println("enter   getOpenId");

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
        System.out.println("enter   frimAdd");

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

    @RequestMapping("/countFrim")
    @ResponseBody
    public ResultJson countFrim(HttpServletRequest request) throws Exception{
        System.out.println("enter   countFrim");

        ResultJson ret = new ResultJson(request);
        ret.setReturnCode(-1l);
        ret.setMsg("操作失败");
        try{
            Long countFrim= wxFrimService.countFrim();
            System.out.println("countFrim==="+countFrim);
            Map data = new HashMap();
            data.put("countFrim",countFrim);
            ret.setReturnCode(1l);
            ret.setData(data);
            ret.setMsg("操作成功");
        }catch (Exception e){
            e.printStackTrace();
        }
        return ret;
    }


    @RequestMapping("/eventAdd")
    @ResponseBody
    public ResultJson eventAdd(HttpServletRequest request,String eventJson) throws Exception{
        System.out.println("enter   eventAdd");

        ResultJson ret = new ResultJson(request);
        ret.setReturnCode(-1l);
        ret.setMsg("操作失败");
        try{

            JSONObject jsonObject = JSONObject.fromObject(eventJson);
            NewYearEvent event = (NewYearEvent)JSONObject.toBean(jsonObject,NewYearEvent.class);
            event.setCreateTime( new Date());
            Long  eventId=wxFrimService.eventAdd(event);
            Map data = new HashMap();
            data.put("eventId",eventId);
            ret.setData(data);
            ret.setReturnCode(1l);
            ret.setMsg("操作成功");
        }catch (Exception e){
            e.printStackTrace();
        }
        return ret;
    }

    @RequestMapping("/eventAddByType")
    @ResponseBody
    public ResultJson eventAddByType(HttpServletRequest request,String Type,String openId,String parentId,String context) throws Exception{
        System.out.println("enter   eventAddByType");

        ResultJson ret = new ResultJson(request);
        ret.setReturnCode(-1l);
        ret.setMsg("操作失败");
        try{

            NewYearEvent result = wxFrimService.eventAddByType(Integer.parseInt(Type),openId,Long.parseLong(parentId),context);

            ret.setReturnCode(1L);
            ret.setMsg("操作成功");
        }catch (Exception e){
            e.printStackTrace();
        }
        return ret;
    }

    @RequestMapping("/eventUpdateByStatus")
    @ResponseBody
    public ResultJson eventUpdateByStatus(HttpServletRequest request,String eventId,String status) throws Exception{
        System.out.println("enter  ===== eventUpdateByStatus");

        ResultJson ret = new ResultJson(request);
        ret.setReturnCode(-1l);
        ret.setMsg("操作失败");
        try{

            NewYearEvent event =new NewYearEvent();
            event.setId(Long.parseLong(eventId));
            event.setStatus(Integer.parseInt(status));
             wxFrimService.eventUpdateByStatus(event);

                    ret.setReturnCode(1l);
            ret.setMsg("操作成功");
        }catch (Exception e){
            e.printStackTrace();
        }
        return ret;
    }


    @RequestMapping("/eventUpdateByContext")
    @ResponseBody
    public ResultJson eventUpdateByContext(HttpServletRequest request,String eventId,String context) throws Exception{
        System.out.println("enter   eventUpdateByContext");

        ResultJson ret = new ResultJson(request);
        ret.setReturnCode(-1l);
        ret.setMsg("操作失败");
        try{
            //修改祝福
            NewYearEvent event =new NewYearEvent();
            event.setId(Long.parseLong(eventId));
            event.setContent(context);
            int  status = wxFrimService.eventUpdateByContext(event);
            Map data = new HashMap();
            data.put("status",status);
            ret.setData(data);

            ret.setReturnCode(1l);
            ret.setMsg("操作成功");
        }catch (Exception e){
            e.printStackTrace();
        }
        return ret;
    }



    @RequestMapping("/findEventListByOpenId")
    @ResponseBody
    public ResultJson findEventListByOpenId(HttpServletRequest request,String openId) throws Exception{
        System.out.println("enter   findEventListByOpenId");
        System.out.println("openId=="+openId);
        ResultJson ret = new ResultJson(request);
        ret.setReturnCode(-1l);
        ret.setMsg("操作失败");
        try{
            NewYearEvent event = new NewYearEvent();
            event.setOpenId(openId);
            List<NewYearEvent> list = wxFrimService.findSerach(event);
            Map data = new HashMap();
//            for (NewYearEvent event1:list) {
//                if(event1.getType() == 3){//当类型为接受者则 剔除他
//                    list.remove(event1);
//                }
//            }
            data.put("pageinfo",list);
            ret.setData(data);
            ret.setReturnCode(1l);
            ret.setMsg("操作成功");
        }catch (Exception e){
            e.printStackTrace();
        }
        return ret;
    }


    @RequestMapping("/findEventListByParentId")
    @ResponseBody
    public ResultJson findEventListByParentId(HttpServletRequest request,String parentId,String type,String openId) throws Exception{
        System.out.println("enter   findEventListByParentId");

        ResultJson ret = new ResultJson(request);
        ret.setReturnCode(-1l);
        ret.setMsg("操作失败");
        try{

            boolean isExist =false;
            System.out.println("parentId=="+parentId);
            System.out.println("type=="+type);
            System.out.println("openId=="+openId);

            NewYearEvent event = new NewYearEvent();
            event.setParentId(Long.parseLong(parentId));
            List<NewYearEvent> list = wxFrimService.findSerach(event);

            List<NewYearEvent> listCall = new ArrayList<NewYearEvent>();

            NewYearEvent eventMe= new NewYearEvent();//当前对象所属的祝福
            NewYearEvent  eventUserinfo = new NewYearEvent();
            Map blessMap = new HashMap();
            for (NewYearEvent event1: list) {
                blessMap.put(event1.getNickName(),event1.getContent());
                if(event1.getOpenId().equals(openId)){
                    eventMe =     event1;
                    isExist = true;
                }

                if(event1.getType() ==0 ){
                    eventUserinfo =  event1;
                }

                if(event1.getStatus() >= 2){
                    listCall.add(event1);
                }

            }

            System.out.println(eventMe.getNickName()+"==="+listCall.size());

            if(!isExist && Integer.parseInt(type) ==1){
                eventMe= wxFrimService.eventAddByType(Integer.parseInt(type),openId,Long.parseLong(parentId),"");

            }
            Map data = new HashMap();
            data.put("pageinfo",listCall);
            data.put("eventUserinfo",eventUserinfo);
            data.put("blessMap",blessMap);
            data.put("countFrim",listCall.size());
            data.put("status",eventMe.getStatus());
            data.put("type",eventMe.getType());
            data.put("eventId",eventMe.getId());
            ret.setData(data);
            ret.setReturnCode(1l);
            ret.setMsg("操作成功");
        }catch (Exception e){
            e.printStackTrace();
        }
        return ret;
    }



   

}