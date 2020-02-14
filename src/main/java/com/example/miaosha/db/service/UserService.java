package com.example.miaosha.db.service;

import com.alibaba.druid.util.StringUtils;
import com.example.miaosha.db.error.BusinessException;
import com.example.miaosha.db.service.model.UserModel;

/**
 * @author FanQie
 * @date 2019/8/8 15:36
 */
public interface UserService {
    UserModel getUserById (Integer id);

    void register(UserModel userModel) throws BusinessException;

    /*
      telphone : 用户注册手机
      password: 用户加密后的密码
     */
    UserModel validateLogin(String telphone, String encrptPassword) throws BusinessException;
}