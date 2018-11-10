package cn.zmt.rest.controller;

import cn.zmt.JsonUtils;
import cn.zmt.rest.pojo.CatResult;
import cn.zmt.rest.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 商品分类列表
 * @author 赵铭涛
 * @creation time 2018/11/10 - 9:31
 */
@Controller
public class ItemCatController {
    @Autowired
    ItemCatService itemCatService;
    //解决中文乱码
    @RequestMapping(value = "/itemcat/list",produces = MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
    @ResponseBody
    public String getItemCatList(String callback){
        CatResult catResult = itemCatService.getitemCatList();
        //把pojp转换成字符串
        String json = JsonUtils.objectToJson(catResult);
        //拼装返回值
        String result = callback+"("+json+");";
        return result;
    }
    /*@RequestMapping(value = "/itemcat/list",produces = MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
    @ResponseBody
    public Object getItemCatList(String callback){
        CatResult catResult = itemCatService.getitemCatList();
        //springmvc 4.1以后新增的方法
        MappingJacksonValue jacksonValue = new MappingJacksonValue(catResult);
        jacksonValue.setJsonpFunction(callback);//回调
        return jacksonValue;
    }*/
}
