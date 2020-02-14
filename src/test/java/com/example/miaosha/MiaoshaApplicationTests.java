package com.example.miaosha;

import com.example.miaosha.db.controller.ItemController;
import com.example.miaosha.db.dao.ItemDOMapper;
import com.example.miaosha.db.dao.UserInfoDOMapper;
import com.example.miaosha.db.entity.UserInfoDO;
import com.example.miaosha.db.error.BusinessException;
import com.example.miaosha.db.response.CommonReturnType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MiaoshaApplicationTests {

    @Autowired
    private UserInfoDOMapper userInfoDOMapper;

    @Autowired
    private ItemController itemController;

    @Test
    public void contextLoads() {
        UserInfoDO userInfoDO = userInfoDOMapper.selectByPrimaryKey(1);
        System.out.println(userInfoDO);
    }

    @Test
    public void testItem() throws BusinessException {
        CommonReturnType commonReturnType = itemController.createItem("手机", "好用",   BigDecimal.valueOf(999), "http://img3.imgtn.bdimg.com/it/u=394841827,3228350768&fm=15&gp=0.jpg", 100);
    }

}
