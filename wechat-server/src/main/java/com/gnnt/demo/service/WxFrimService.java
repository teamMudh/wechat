package com.gnnt.demo.service;

import com.gnnt.demo.entity.WxFrim;
import com.gnnt.demo.repositoty.WxFrimRepositoty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;


/**
 * Created by Song on 2017/2/15.
 * User
 */
@Service
public class WxFrimService {
	
	@Autowired
    private WxFrimRepositoty wxFrimRepositoty;

    public void saveWxFrim(WxFrim frim){
        try{
            wxFrimRepositoty.save(frim);


        }catch (Exception e){}
    }

    public boolean existsWxFrimByOpenId(String openid){
        boolean isFlag = false;
        try{
            isFlag = wxFrimRepositoty.exists(openid);
//            wxFrimRepositoty.


        }catch (Exception e){}
        return isFlag;

    }

}
