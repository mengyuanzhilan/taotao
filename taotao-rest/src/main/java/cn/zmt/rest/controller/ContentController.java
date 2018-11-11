package cn.zmt.rest.controller;

import cn.zmt.pojo.TaotaoResult;
import cn.zmt.pojo.TbContent;
import cn.zmt.rest.service.ContentService;
import cn.zmt.utils.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author zmt
 * @date 2018/11/12 - 4:09
 */
@Controller
@RequestMapping("/content")
public class ContentController {
    @Autowired
    private ContentService contentService;

    /**
     * 根据内容分类id查询内容列表
     * @param contentCategoryId
     * @return
     */
    @RequestMapping("/list/{contentCategoryId}")
    @ResponseBody
    public TaotaoResult getContentList(@PathVariable Long contentCategoryId){
        try {
            List<TbContent> list = contentService.getContentList(contentCategoryId);
            return TaotaoResult.ok(list);
        }catch (Exception e){
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }

    }

}
