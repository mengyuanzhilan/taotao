package cn.zmt.rest.service.impl;

import cn.zmt.pojo.TaotaoResult;
import cn.zmt.rest.dao.JedisClient;
import cn.zmt.rest.service.RedisService;
import cn.zmt.utils.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author 赵铭涛
 * @creation time 2018/11/12 - 8:38
 */
@Service
public class RedisServiceImpl implements RedisService {
    @Autowired
    private JedisClient jedisClient;
    @Value("${INDEX_CONTENT_REDIS_KEY}")
    private String  INDEX_CONTENT_REDIS_KEY;

    /**
     *
     * @param contentCid
     * @return
     */
    @Override
    public TaotaoResult syncContent(long contentCid) {
        try {
            jedisClient.hdel(INDEX_CONTENT_REDIS_KEY,contentCid+"");
        }catch (Exception e){
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
        return TaotaoResult.ok();
    }
}
