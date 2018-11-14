package cn.zmt.portal.service;

import cn.zmt.pojo.TaotaoResult;

/**
 * @author 赵铭涛
 * @creation time 2018/11/14 - 16:31
 */
public interface CartService {
    TaotaoResult addCartItem(long itemId,int num);
}
