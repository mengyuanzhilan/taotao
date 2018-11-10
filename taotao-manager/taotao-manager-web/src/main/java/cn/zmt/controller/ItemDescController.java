package cn.zmt.controller;

import cn.zmt.pojo.TaotaoResult;
import cn.zmt.service.ItemDescService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 商品描述
 * @author 赵铭涛
 * @creation time 2018/11/8 - 9:05
 */
@Controller
@RequestMapping("/rest/item")
public class ItemDescController {
    @Autowired
    ItemDescService itemDescService;

    @ResponseBody
    @RequestMapping("/query/item/desc/{itemId}")
    public TaotaoResult getItemDescByItemId(@PathVariable Long itemId){
        return itemDescService.getItemDescByItemId(itemId);
    }
}
