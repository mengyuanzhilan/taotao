package cn.zmt.sso.controller;

import cn.zmt.pojo.TaotaoResult;
import cn.zmt.pojo.TbUser;
import cn.zmt.sso.service.UserService;
import cn.zmt.utils.ExceptionUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 赵铭涛
 * @creation time 2018/11/14 - 10:27
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 校验
     * @param param
     * @param type
     * @param callback
     * @return
     */
    @ResponseBody
    @RequestMapping("/check/{param}/{type}")
    public Object checkData(@PathVariable("param") String param,@PathVariable("type") Integer type,String callback){

        TaotaoResult result = null;

        //参数有效性校验
        if (StringUtils.isBlank(param)) {
            result = TaotaoResult.build(400, "校验内容不能为空");
        }
        if (type == null) {
            result = TaotaoResult.build(400, "校验内容类型不能为空");
        }
        if (type != 1 && type != 2 && type != 3 ) {
            result = TaotaoResult.build(400, "校验内容类型错误");
        }
        //校验出错
        if (null != result) {
            if (null != callback) {
                MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
                mappingJacksonValue.setJsonpFunction(callback);
                return mappingJacksonValue;
            } else {
                return result;
            }
        }
        //调用服务
        try {
            result = userService.checkDate(param, type);

        } catch (Exception e) {
            result = TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }

        if (null != callback) {
            MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
            mappingJacksonValue.setJsonpFunction(callback);
            return mappingJacksonValue;
        } else {
            return result;
        }
    }

    /**
     * 创建用户
     * @param user
     * @return
     */
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult createUser(TbUser user){
        try {
            return userService.createUser(user);
        }catch (Exception e){
            return TaotaoResult.build(500,ExceptionUtil.getStackTrace(e));
        }
    }

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult userLogin(String username, String password, HttpServletRequest request, HttpServletResponse response){
        try {
            return userService.userLogin(username,password,request,response);
        }catch (Exception e){
            e.printStackTrace();
            return TaotaoResult.build(500,ExceptionUtil.getStackTrace(e));
        }
    }


    /**
     *
     * @param token
     * @param callback
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/token/{token}",method = RequestMethod.GET)
    public Object getUserBytoken(@PathVariable("token") String token,String callback){
        TaotaoResult result = null;
        try {
            result = userService.getUserByToken(token);
        }catch (Exception e){
            e.printStackTrace();
            result = TaotaoResult.build(500,ExceptionUtil.getStackTrace(e));
        }
        //判断是否为jsonp调用
        if(StringUtils.isBlank(callback)){
            return result;
        }else{
            MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
            mappingJacksonValue.setJsonpFunction(callback);
            return mappingJacksonValue;
        }
    }
}
