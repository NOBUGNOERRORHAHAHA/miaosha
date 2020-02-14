package com.example.miaosha.db.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.example.miaosha.db.dao.UserInfoDOMapper;
import com.example.miaosha.db.dao.UserPasswordDOMapper;
import com.example.miaosha.db.entity.UserInfoDO;
import com.example.miaosha.db.entity.UserPasswordDO;
import com.example.miaosha.db.error.BusinessException;
import com.example.miaosha.db.error.EmBusinessError;
import com.example.miaosha.db.service.UserService;
import com.example.miaosha.db.service.model.UserModel;
import com.example.miaosha.db.validator.ValidationResult;
import com.example.miaosha.db.validator.ValidatorImplement;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author FanQie
 * @date 2019/8/8 15:36
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserInfoDOMapper userInfoDOMapper;

    @Autowired
    private UserPasswordDOMapper userPasswordDOMapper;

    @Autowired
    private ValidatorImplement validator;

    @Override
    public UserModel getUserById(Integer id) {
        UserInfoDO userInfoDO = userInfoDOMapper.selectByPrimaryKey(id);
        if (userInfoDO == null) {
            return null;
        }
        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(userInfoDO.getId());
        return convertFromEntity(userInfoDO, userPasswordDO);

    }

    public UserModel convertFromEntity(UserInfoDO userInfoDO, UserPasswordDO userPasswordDO) {
        if (userInfoDO == null) {
            return null;
        }
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userInfoDO, userModel);
        if (userPasswordDO != null) {
            userModel.setEncrptPassword(userPasswordDO.getEncrptPassword());
        }
        return userModel;
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void register(UserModel userModel) throws BusinessException {
        if (userModel == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
//        if (StringUtils.isEmpty(userModel.getName())
//                || userModel.getGender() == null
//                || userModel.getAge() == null
//                || StringUtils.isEmpty(userModel.getTelphone()) )  {
//            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
//        }

        ValidationResult result = validator.validate(userModel);
        if (result.isHasErrors()) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }

        UserInfoDO userInfoDO = convertFromModel(userModel);
        try {
            userInfoDOMapper.insertSelective(userInfoDO);
        } catch (DuplicateKeyException ex) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"手机号已重复注册");
        }

        userModel.setId(userInfoDO.getId());

        UserPasswordDO userPasswordDO = convertPasswordFromModel(userModel);
        userPasswordDOMapper.insertSelective(userPasswordDO);

        return;

    }

    public UserInfoDO convertFromModel(UserModel userModel) {
        if (userModel == null) {
            return null;
        }
        UserInfoDO userInfoDO = new UserInfoDO();
        BeanUtils.copyProperties(userModel,userInfoDO);
        return userInfoDO;
    }

    private UserPasswordDO convertPasswordFromModel(UserModel userModel) {
        if (userModel == null) {
            return null;
        }
        UserPasswordDO userPasswordDO = new UserPasswordDO();
        userPasswordDO.setEncrptPassword(userModel.getEncrptPassword());
        userPasswordDO.setUserId(userModel.getId());
        return userPasswordDO;
    }

    @Override
    public UserModel validateLogin(String telphone, String encrptPassword) throws BusinessException {
        //通过用户手机获取用户登录信息
        UserInfoDO userInfoDO = userInfoDOMapper.selectByTelphone(telphone);
        if (userInfoDO == null) {
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
        }
        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(userInfoDO.getId());
        UserModel userModel = convertFromEntity(userInfoDO, userPasswordDO);

        //比对用户信息内加密的密码是否和传输进来的密码相匹配
        if (!StringUtils.equals(encrptPassword, userModel.getEncrptPassword())) {
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
        }
        return userModel;
    }
}
