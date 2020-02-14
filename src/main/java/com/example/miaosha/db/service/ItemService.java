package com.example.miaosha.db.service;

import com.example.miaosha.db.error.BusinessException;
import com.example.miaosha.db.service.model.ItemModel;

import java.sql.BatchUpdateException;
import java.util.List;

/**
 * @author FanQie
 * @date 2019/8/11 21:05
 */
public interface ItemService {

    //创建商品
    ItemModel createItem(ItemModel itemModel) throws BusinessException;


    //商品列表的浏览
    List<ItemModel> listItem();

    //商品详情浏览
    ItemModel getItemById(Integer id);

    //库存扣减
    boolean decreaseStock(Integer itemId, Integer amount) throws BusinessException;

    //商品销量增加
    void increaseSales(Integer itemId, Integer amount) throws BusinessException;

}
