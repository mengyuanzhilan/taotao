package cn.zmt.portal.controller;

import cn.zmt.portal.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 赵铭涛
 * @creation time 2018/11/9 - 14:45
 */
@Controller
public class IndexController {
    @Autowired
    ContentService contentService;

    /**
     * 主页显示
     * @param model
     * @return
     */
    @RequestMapping("/index")
    public String showIndex(Model model){
        //大广告
        String adJson = contentService.getContentList();
        model.addAttribute("ad1",adJson);

        return "index";
    }
}
