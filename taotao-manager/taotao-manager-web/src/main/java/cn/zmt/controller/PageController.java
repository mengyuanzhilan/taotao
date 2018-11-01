package cn.zmt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面跳转Controller
 * @author 赵铭涛
 * @creation time 2018/10/31 - 15:44
 */
@Controller
public class PageController {

    /**
     * 打开首页
     * */
    @RequestMapping("/")
    public String showIndex(){
        return "index";
    }
    /*
    * 展示其他页面
    * */
    @RequestMapping("/{page}")
    public String showPage(@PathVariable String page){
        return page;
    }
}
