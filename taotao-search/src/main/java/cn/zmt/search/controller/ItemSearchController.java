package cn.zmt.search.controller;

import cn.zmt.pojo.TaotaoResult;
import cn.zmt.search.pojo.SearchResult;
import cn.zmt.search.service.ItemSearchService;
import cn.zmt.utils.ExceptionUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 商品查询Controller
 * @author zmt
 * @date 2018/11/13 - 21:24
 */
@Controller
public class ItemSearchController {
    @Autowired
    private ItemSearchService searchService;

    @RequestMapping(value = "/query",method = RequestMethod.GET)
    @ResponseBody
    public TaotaoResult search(@RequestParam("q")String queryString,
                               @RequestParam(defaultValue="1")Integer page,
                               @RequestParam(defaultValue="30")Integer rows){
        //判断查询条件不能为空
        if(StringUtils.isBlank(queryString)){
            return TaotaoResult.build(400,"查询条件不能为空");
        }
        SearchResult searchResult = null;
        try {
            //解决中文乱码
            queryString = new String(queryString.getBytes("iso8859-1"), "utf-8");
            System.out.println("搜索值："+queryString);
            searchResult = searchService.search(queryString, page, rows);
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
        return TaotaoResult.ok(searchResult);
    }
}
