package cn.zmt.controller;

import cn.zmt.pojo.EUTreeNode;
import cn.zmt.pojo.TaotaoResult;
import cn.zmt.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author 赵铭涛
 * @creation time 2018/11/10 - 13:45
 */
@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {
    @Autowired
    private ContentCategoryService contentCategoryService;

    /**
     * 跟去id查询列表
     * @param parentId
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public List<EUTreeNode> getContentCatList(@RequestParam(value = "id",defaultValue = "0") Long parentId){
        return contentCategoryService.getCategoryList(parentId);
    }

    /**
     * 添加
     * @param parentId
     * @param name
     * @return
     */
    @RequestMapping("/create")
    public TaotaoResult createContentCategory(Long parentId,String name){
        return contentCategoryService.insertContentCategory(parentId,name);
    }

    /**
     * 删除
     * @param parentId
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public TaotaoResult deleteContentCategory(@RequestParam(value = "id",defaultValue = "0") Long parentId,Long id){
        return contentCategoryService.deleteContentCategory(parentId,id);
    }

    /**
     * 修改节点名
     * @param id
     * @param name
     * @return
     */
    @RequestMapping("/update")
    @ResponseBody
    public TaotaoResult updateContentCategoryName(Long id,String name){
        return contentCategoryService.updateContentCategoryName(id,name);
    }
}
