package cn.zmt.rest.service;

import cn.zmt.pojo.TaotaoResult;

/**
 * @author zmt
 * @date 2018/11/14 - 1:46
 */
public interface ItemService {

    /**
     * 根据id查询商品
     * @param itemId
     * @return
     */
    TaotaoResult getItemBaseInfo(long itemId);

    /**
     * 根据商品ip获得商品描述
     * @param itemId
     * @return
     */
    TaotaoResult getItemDesc(long itemId);

    /**
     * 根据商品id查询规格参数
     * @param itemId
     * @return
     */
    TaotaoResult getItemParamItem(long itemId);
}
