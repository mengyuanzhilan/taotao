package cn.zmt.controller;

import cn.zmt.pojo.EUDataGridResult;
import cn.zmt.pojo.TaotaoResult;
import cn.zmt.pojo.TbContent;
import cn.zmt.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zmt
 * @date 2018/11/12 - 3:35
 */
@Controller
@RequestMapping("/content")
public class ContentController {
    @Autowired
    ContentService contentService;


    /**
     * 根据categoryId分页查询
     * @param page
     * @param rows
     * @param categoryId
     * @return
     */
    @RequestMapping("/query/list")
    @ResponseBody
    public EUDataGridResult getContentList(int page, int rows, long categoryId){
        return contentService.getContentList(page,rows,categoryId);
    }

    /**
     * 添加
     * @param content
     * @return
     */
    @ResponseBody
    @RequestMapping("/save")
    public TaotaoResult insertContent(TbContent content){
        return contentService.insertContent(content);
    }

    /**
     * 修改
     * @param content
     * @return
     */
    @ResponseBody
    @RequestMapping("/edit")
    public TaotaoResult updateContent(TbContent content){
        return contentService.updateContent(content);
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping("/delete")
    public TaotaoResult deleteContent(long[] ids){
        return contentService.deleteContent(ids);
    }
}
