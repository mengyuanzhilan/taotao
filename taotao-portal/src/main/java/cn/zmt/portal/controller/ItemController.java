package cn.zmt.portal.controller;

import cn.zmt.portal.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 商品详情页面展示
 * @author zmt
 * @date 2018/11/14 - 3:21
 */
@Controller

public class ItemController {
    @Autowired
    private ItemService itemService;


    /**
     * 商品详情
     * @param itemId
     * @param model
     * @return
     */
    @RequestMapping("/item/{itemId}")
    public String showItem(@PathVariable("itemId") Long itemId, Model model){
        model.addAttribute("item",itemService.getItemById(itemId));
        return "item";
    }

    /**
     * 获得商品描述
     * @param itemId
     * @return
     */
    @RequestMapping(value = "/item/desc/{itemId}",produces = MediaType.TEXT_HTML_VALUE+";charset=utf-8")
    @ResponseBody
    public String getItemDesc(@PathVariable("itemId") Long itemId){
        return itemService.getItemDescById(itemId);
    }

    /**
     * 商品参数
     * @param itemId
     * @return
     */
    @ResponseBody
    @RequestMapping("/item/param/{itemId}")
    public Object getItemParamItem(@PathVariable("itemId") Long itemId){
        return itemService.getItemParamItemById(itemId);
    }
}
