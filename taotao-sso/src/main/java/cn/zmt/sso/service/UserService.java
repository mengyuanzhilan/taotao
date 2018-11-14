package cn.zmt.sso.service;

import cn.zmt.pojo.TaotaoResult;
import cn.zmt.pojo.TbUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 赵铭涛
 * @creation time 2018/11/14 - 10:17
 */
public interface UserService {

    TaotaoResult checkDate(String content,Integer type);

    TaotaoResult createUser(TbUser user);

    TaotaoResult userLogin(String username, String password,HttpServletRequest request, HttpServletResponse response);

    TaotaoResult getUserByToken(String token);
}
