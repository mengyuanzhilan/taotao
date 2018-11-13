package cn.zmt.search.mapper;

import cn.zmt.search.pojo.Item;

import java.util.List;

/**
 * @author zmt
 * @date 2018/11/13 - 4:02
 */
public interface ItemMapper {

    /**
     * 查询所有商品
     * @return
     */
    List<Item> getItemList();
}
