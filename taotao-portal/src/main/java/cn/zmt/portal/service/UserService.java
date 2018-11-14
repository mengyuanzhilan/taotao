package cn.zmt.portal.service;

import cn.zmt.pojo.TbUser;

/**
 * @author 赵铭涛
 * @creation time 2018/11/14 - 14:23
 */
public interface UserService {
    TbUser getUserByToken(String token);
}
