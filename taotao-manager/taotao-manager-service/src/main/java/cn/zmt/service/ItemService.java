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
    TaotaoResult createItem(TbItem item,String desc,String itemParam) throws Exception;

    /**
     * 修改商品
     * @param item
     * @param desc
     * @param itemParam
     * @return
     */
    TaotaoResult updateItem(TbItem item,String desc,String itemParam) throws Exception;

    /**
     * 删除商品
     * @param itemId
     * @return
     * @throws Exception
     */
    TaotaoResult deleteItem(Long[] itemId)throws Exception;

    /**
     * 上下架商品
     * @return
     */
    TaotaoResult upOrDownItem(Long[] ids,Integer or) throws Exception;

}
