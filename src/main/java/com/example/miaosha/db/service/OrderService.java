package com.example.miaosha.db.service;

import com.example.miaosha.db.error.BusinessException;
import com.example.miaosha.db.service.model.OrderModel;

/**
 * @author FanQie
 * @date 2019/8/27 13:33
 */
public interface OrderService {

    OrderModel createOrder(Integer useId, Integer itemId, Integer amount) throws BusinessException;

}
