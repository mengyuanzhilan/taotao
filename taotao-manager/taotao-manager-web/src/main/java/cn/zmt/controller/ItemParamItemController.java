package cn.zmt.controller;

import cn.zmt.TaotaoResult;
import cn.zmt.service.ItemParamItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zmt
 * @date 2018/11/7 - 22:30
 */
@Controller
@RequestMapping("/items")
public class ItemParamItemController {
    @Autowired
    private ItemParamItemService itemParamItemService;
    @RequestMapping("/param/item/{itemId}")
    public String getItemParamItemById(@PathVariable Long itemId, Model model) {
        String itemParamItem = itemParamItemService.getItemParamByItemId(itemId);
        model.addAttribute("param1", itemParamItem);
        return "item-param-show";
    }


    @ResponseBody
    @RequestMapping("/param/item/query/{itemId}")
    public TaotaoResult getItemParamItemByIdPojo(@PathVariable Long itemId){
        return itemParamItemService.getItemParamByItemIdPojo(itemId);
    }
}
