package com.example.miaosha.db.controller;

import com.example.miaosha.db.error.BusinessException;
import com.example.miaosha.db.error.EmBusinessError;
import com.example.miaosha.db.response.CommonReturnType;
import com.example.miaosha.db.service.OrderService;
import com.example.miaosha.db.service.model.OrderModel;
import com.example.miaosha.db.service.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author FanQie
 * @date 2019/8/27 14:32
 */
@Controller("order")
@RequestMapping("/order")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class OrderController extends BaseController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private HttpServletRequest httpServletRequest;


    @RequestMapping(value = "/createorder", method = RequestMethod.POST, consumes = CONTENT_TYPE_FORMED)
    @ResponseBody
    public CommonReturnType createOrder(@RequestParam(name="itemId") Integer itemId,
                                        @RequestParam(name = "amount") Integer amount) throws BusinessException {


        //判断用户是否登录
        Boolean isLogin = (Boolean) httpServletRequest.getSession().getAttribute("IS_LOGIN");
        if (isLogin == null || !isLogin.booleanValue()) {
            throw new BusinessException(EmBusinessError.USER_NOT_LOGIN, "用户还没登录，无法下单");
        }
        //获取登录的用户信息
        UserModel userModel = (UserModel) httpServletRequest.getSession().getAttribute("LOGIN_USER");
        orderService.createOrder(userModel.getId(), itemId,  amount);

        return CommonReturnType.create(null);
    }
}
