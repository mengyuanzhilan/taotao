package cn.zmt.controller;

import cn.zmt.pojo.EUDataGridResult;
import cn.zmt.pojo.TaotaoResult;
import cn.zmt.pojo.TbItem;
import cn.zmt.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 商品
 * @author zmt
 * @date 2018/10/27 - 23:09
 */
@Controller
@RequestMapping("/item")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @ResponseBody
    @RequestMapping("/item/{itemId}")
    public TbItem getItemById(@PathVariable Long itemId){
        return itemService.getItemById(itemId);
    }

    /**
     * 获取item列表
     * @param page 当前页
     * @param rows 显示多少条数据
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public EUDataGridResult getItemList(Integer page,Integer rows){
        EUDataGridResult result = itemService.getItemList(page,rows);
        return result;
    }

    /**
     * 添加商品
     * @param item 商品信息
     * @param desc 商品描述（富文本编辑器的值）
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult createItem(TbItem item,String desc,String itemParams)throws Exception{
        return itemService.createItem(item,desc,itemParams);
    }

    /**
     * 修改商品信息
     * @param item 商品信息
     * @param desc 商品描述
     * @param itemParams 商品参数
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/update")
    public TaotaoResult updateItem(TbItem item,String desc,String itemParams)throws Exception{
        return itemService.updateItem(item,desc,itemParams);
    }

    /**
     * 删除商品
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping("/delete")
    public TaotaoResult deleteItem(Long[] ids)throws Exception{
        return itemService.deleteItem(ids);
    }

    /**
     * 下架商品
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping("/instock")
    public TaotaoResult instockItem(Long[] ids)throws Exception{
        return itemService.upOrDownItem(ids,2);
    }
    /**
     * 上架商品
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping("/reshelf")
    public TaotaoResult reshelfItem(Long[] ids)throws Exception{
        return itemService.upOrDownItem(ids,1);
    }
}
