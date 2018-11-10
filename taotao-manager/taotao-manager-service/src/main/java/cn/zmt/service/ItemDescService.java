package cn.zmt.service;

import cn.zmt.pojo.TaotaoResult;

/**
 * 商品描述
 * @author 赵铭涛
 * @creation time 2018/11/8 - 8:52
 */
public interface ItemDescService {
    /**
     * 通过商品id获得商品描述
     * @param itemId
     * @return
     */
    TaotaoResult getItemDescByItemId(Long itemId);
}
