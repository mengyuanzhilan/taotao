package cn.zmt.rest.service.impl;

import cn.zmt.mapper.TbContentMapper;
import cn.zmt.pojo.TbContent;
import cn.zmt.pojo.TbContentExample;
import cn.zmt.rest.dao.JedisClient;
import cn.zmt.rest.service.ContentService;
import cn.zmt.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zmt
 * @date 2018/11/12 - 4:04
 */
@Service
public class ContentServiceImpl implements ContentService {
    @Autowired
    private TbContentMapper contentMapper;
    @Autowired
    private JedisClient jedisClient;
    @Value("${INDEX_CONTENT_REDIS_KEY}")
    private String INDEX_CONTENT_REDIS_KEY;
    /**
     * 根据内容分类id查询内容列表
     * @param contentCid
     * @return
     */
    @Override
    public List<TbContent> getContentList(long contentCid) {
        //从缓冲中取出内容
        try {
            String result = jedisClient.hget(INDEX_CONTENT_REDIS_KEY, contentCid + "");
            if(!StringUtils.isBlank(result)){
                //把字符串转换成list
                return JsonUtils.jsonToList(result,TbContent.class);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        //根据内容分类id查询内容列表
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(contentCid);
        //执行查询
        List<TbContent> list = contentMapper.selectByExampleWithBLOBs(example);
        //向缓冲添加内容
        try {
            //把list转换成字符串
            String cacheString = JsonUtils.objectToJson(list);
            jedisClient.hset(INDEX_CONTENT_REDIS_KEY,contentCid+"",cacheString);
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
}
