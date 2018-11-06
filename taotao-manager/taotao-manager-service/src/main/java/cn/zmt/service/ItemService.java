package cn.zmt.service;

import cn.zmt.EUDataGridResult;
import cn.zmt.TaotaoResult;
import cn.zmt.pojo.TbItem;
import org.springframework.stereotype.Service;

/**
 * @author zmt
 * @date 2018/10/27 - 23:04
 */
@Service
public interface ItemService {
    public TbItem getItemById(long itemId);

    /**
     * 商品列表查询
     * @param page
     * @param rows
     * @return
     */
    EUDataGridResult getItemList(int page,int rows);

    /**
     *创建商品
     * @param item
     * @return
     */
    TaotaoResult createItem(TbItem item);
}
