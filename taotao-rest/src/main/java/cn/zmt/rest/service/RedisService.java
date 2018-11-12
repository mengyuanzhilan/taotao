package cn.zmt.rest.service;

import cn.zmt.pojo.TaotaoResult;

/**
 * @author 赵铭涛
 * @creation time 2018/11/12 - 8:37
 */
public interface RedisService {

    TaotaoResult syncContent(long contentCid);
}
