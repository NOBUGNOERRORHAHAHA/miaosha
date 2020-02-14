package com.example.miaosha.db.service;

import com.example.miaosha.db.service.model.PromoModel;

/**
 * @author FanQie
 * @date 2019/8/27 16:51
 */
public interface PromoService {
    PromoModel getPromoByItemId(Integer itemId);
}
