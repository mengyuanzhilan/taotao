package cn.zmt.search.controller;

import cn.zmt.pojo.TaotaoResult;
import cn.zmt.search.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 索引库维护
 *
 * @author zmt
 * @date 2018/11/13 - 4:27
 */
@Controller
@RequestMapping("/manager")
public class ItemController {
    @Autowired
    ItemService itemService;

    @RequestMapping("/importall")
    @ResponseBody
    public TaotaoResult importAllItems(){
        return itemService.importAllItems();
    }

}
