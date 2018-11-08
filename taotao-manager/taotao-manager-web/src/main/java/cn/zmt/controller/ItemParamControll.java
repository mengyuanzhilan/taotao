package cn.zmt.controller;

import cn.zmt.EUDataGridResult;
import cn.zmt.TaotaoResult;
import cn.zmt.pojo.TbItemParam;
import cn.zmt.service.ItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 商品规格参数模板管理
 * @author zmt
 * @date 2018/11/7 - 17:54
 */
@Controller
@RequestMapping("/item/param")
public class ItemParamControll {
    @Autowired
    private ItemParamService itemParamService;

    /**
     * 通过id获取参数模板的值
     * @param itemCatId id
     * @return
     */
    @ResponseBody
    @RequestMapping("/query/itemcatid/{itemCatId}")
    public TaotaoResult getItemParamByCid(@PathVariable Long itemCatId){
        return itemParamService.getTtemParamByCid(itemCatId);
    }

    /**
     * 保存参数模板
     * @param cid
     * @param paramData
     * @return
     */
    @ResponseBody
    @RequestMapping("/save/{cid}")
    public TaotaoResult insertItemParam(@PathVariable long cid,String paramData){
        System.out.println("cid:"+cid);
        //创建pojo对象
        TbItemParam itemParam = new TbItemParam();
        itemParam.setItemCatId(cid);
        itemParam.setParamData(paramData);
        return itemParamService.insertItemParam(itemParam);
    }

    /**
     * 分页获取参数模板
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public EUDataGridResult getItemParamlist(Integer page,Integer rows){
        return itemParamService.getItemParamList(page,rows);
    }

    /**
     * 删除参数模板列表
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping("/delete")
    public TaotaoResult deleteItemParam(Long[] ids)throws Exception{
        return itemParamService.deleteItemParam(ids);
    }
}
