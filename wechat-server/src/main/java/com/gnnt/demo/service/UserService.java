package com.gnnt.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.gnnt.demo.entity.User;
import com.gnnt.demo.repositoty.UserRepositoty;


/**
 * Created by Song on 2017/2/15.
 * User
 */
@Service
public class UserService {
	
	@Autowired
    private UserRepositoty userRepositoty;

    public User findUserByName(String name){
        User user = null;
        try{
            user = userRepositoty.findByUserName(name);
            int page=1,size=10;

            Sort sort = new Sort(Direction.DESC, "id");
            Pageable pageable = new PageRequest(page, size, sort);
            
            userRepositoty.findAll(pageable);

        }catch (Exception e){}
        return user;
    }
}
