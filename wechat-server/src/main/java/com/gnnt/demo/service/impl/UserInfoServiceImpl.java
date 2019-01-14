package com.gnnt.demo.service.impl;

import org.springframework.stereotype.Service;

import com.gnnt.demo.entity.UserInfo;
import com.gnnt.demo.repositoty.UserInfoDao;
import com.gnnt.demo.service.UserInfoService;

import javax.annotation.Resource;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Resource
    private UserInfoDao userInfoDao;
    @Override
    public UserInfo findByUsername(String username) {
        System.out.println("UserInfoServiceImpl.findByUsername()");
        return userInfoDao.findByUsername(username);
    }
}