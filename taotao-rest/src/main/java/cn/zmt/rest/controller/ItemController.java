package cn.zmt.rest.controller;

import cn.zmt.pojo.TaotaoResult;
import cn.zmt.rest.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 商品
 * @author zmt
 * @date 2018/11/14 - 1:51
 */
@Controller
@RequestMapping("/item")
public class ItemController {
    @Autowired
    private ItemService itemService;

    /**
     * 根据id查询商品
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/info/{itemId}")
    public TaotaoResult getItemBaseInfo(@PathVariable("itemId") Long id){
        return itemService.getItemBaseInfo(id);
    }

    /**
     * 根据商品ip获得商品描述
     * @param itemId
     * @return
     */
    @ResponseBody
    @RequestMapping("/desc/{itemId}")
    public TaotaoResult getItemDesc(@PathVariable("itemId") Long itemId){
        return itemService.getItemDesc(itemId);
    }
    /**
     * 根据商品id查询规格参数
     * @param itemId
     * @return
     */
    @ResponseBody
    @RequestMapping("/param/{itemId}")
    public TaotaoResult getItemParamItem(@PathVariable("itemId") Long itemId){
        return itemService.getItemParamItem(itemId);
    }
}
