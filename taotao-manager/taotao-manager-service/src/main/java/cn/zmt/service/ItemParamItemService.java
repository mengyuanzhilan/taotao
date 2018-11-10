package cn.zmt.service;

import cn.zmt.pojo.TaotaoResult;

/**
 * @author zmt
 * @date 2018/11/7 - 21:18
 */
public interface ItemParamItemService {
    /**
     *
     * @param itemId
     * @return
     */
    String getItemParamByItemId(Long itemId);

    /**
     * 通过id获取
     * @param itemId
     * @return
     */
    TaotaoResult getItemParamByItemIdPojo(Long itemId);
}
