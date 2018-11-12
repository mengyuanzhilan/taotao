package cn.zmt.rest.controller;

import cn.zmt.pojo.TaotaoResult;
import cn.zmt.rest.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 缓冲同步Controller
 * @author 赵铭涛
 * @creation time 2018/11/12 - 8:51
 */
@Controller
@RequestMapping("/cache/sync")
public class RedisController {
    @Autowired
    private RedisService redisService;

    @RequestMapping("/content/{contentCid}")
    @ResponseBody
    public TaotaoResult contentCacheSync(@PathVariable Long contentCid){
        return redisService.syncContent(contentCid);
    }
}
