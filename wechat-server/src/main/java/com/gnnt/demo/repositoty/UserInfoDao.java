package com.gnnt.demo.repositoty;

import org.springframework.data.repository.CrudRepository;

import com.gnnt.demo.entity.UserInfo;

public interface UserInfoDao extends CrudRepository<UserInfo,Long> {
    /**通过username查找用户信息;*/
    public UserInfo findByUsername(String username);
    
}