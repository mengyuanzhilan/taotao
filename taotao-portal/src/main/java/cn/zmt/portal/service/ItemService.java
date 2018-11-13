package cn.zmt.portal.service;

import cn.zmt.portal.pojo.ItemInfo;

/**
 * @author zmt
 * @date 2018/11/14 - 3:13
 */
public interface ItemService {

    ItemInfo getItemById(long itemId);

    String getItemDescById(long itemId);

    String getItemParamItemById(long itemId);
}
