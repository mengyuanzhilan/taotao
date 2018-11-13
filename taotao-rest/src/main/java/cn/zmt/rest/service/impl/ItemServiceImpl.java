package cn.zmt.rest.service.impl;

import cn.zmt.mapper.TbItemDescMapper;
import cn.zmt.mapper.TbItemMapper;
import cn.zmt.mapper.TbItemParamItemMapper;
import cn.zmt.pojo.*;
import cn.zmt.rest.dao.JedisClient;
import cn.zmt.rest.service.ItemService;
import cn.zmt.utils.ExceptionUtil;
import cn.zmt.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zmt
 * @date 2018/11/14 - 1:47
 */
@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private TbItemMapper itemMapper;

    @Autowired
    private JedisClient jedisClient;

    @Autowired
    private TbItemDescMapper itemDescMapper;

    @Autowired
    private TbItemParamItemMapper itemParamItemMapper;

    @Value("${REDIS_ITEM_KEY}")
    private String REDIS_ITEM_KEY;
    @Value("${REDIS_ITEM_EXPIRE}")
    private int REDIS_ITEM_EXPIRE;
    /**
     * 根据id查询商品
     * @param itemId
     * @return
     */
    @Override
    public TaotaoResult getItemBaseInfo(long itemId) {
        //添加缓冲逻辑
        try {
            //从缓冲中取商品id，商品id对于的信息
            String json = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":base");
            if (!StringUtils.isBlank(json)) {
                //把json转换成Java对象
                TbItem item = JsonUtils.jsonToPojo(json, TbItem.class);
                return TaotaoResult.ok(item);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        //根据id查询商品
        TbItem item = itemMapper.selectByPrimaryKey(itemId);

        try {
            //把商品信息写入缓冲
            jedisClient.set(REDIS_ITEM_KEY+":"+item.getId()+":base", JsonUtils.objectToJson(item));
            //设置key的有效期
            jedisClient.expire(REDIS_ITEM_KEY+":"+item.getId()+":base",REDIS_ITEM_EXPIRE);
        }catch (Exception e){
            e.printStackTrace();
        }
        return TaotaoResult.ok(item);
    }

    /**
     * 根据商品ip获得商品描述
     * @param itemId
     * @return
     */
    @Override
    public TaotaoResult getItemDesc(long itemId) {
        //添加缓冲逻辑
        try {
            //从缓冲中取商品id，商品id对于的信息
            String json = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":desc");
            if (!StringUtils.isBlank(json)) {
                //把json转换成Java对象
                TbItemDesc itemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
                return TaotaoResult.ok(itemDesc);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        //创建查询条件
        TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(itemId);

        try {
            //把商品信息写入缓冲
            jedisClient.set(REDIS_ITEM_KEY+":"+itemId+":desc", JsonUtils.objectToJson(itemDesc));
            //设置key的有效期
            jedisClient.expire(REDIS_ITEM_KEY+":"+itemId+":desc",REDIS_ITEM_EXPIRE);
        }catch (Exception e){
            e.printStackTrace();
        }
        return TaotaoResult.ok(itemDesc);
    }

    /**
     * 根据商品id查询规格参数
     * @param itemId
     * @return
     */
    public TaotaoResult getItemParamItem(long itemId){
        //添加缓冲
        try {
            //添加缓冲逻辑
            //从缓冲中取商品信息，商品id对应的信息
            String json = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":param");
            //判断是否有值
            if (!StringUtils.isBlank(json)) {
                //把json转成java对象
                return TaotaoResult.ok(JsonUtils.jsonToPojo(json,TbItemParamItem.class));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        //根据商品id查询规格参数
        TbItemParamItemExample example = new TbItemParamItemExample();
        TbItemParamItemExample.Criteria criteria = example.createCriteria();
        criteria.andItemIdEqualTo(itemId);
        List<TbItemParamItem> itemParamItemList = itemParamItemMapper.selectByExampleWithBLOBs(example);
        if(itemParamItemList!=null&&itemParamItemList.size()>0){
            try {
                //把商品信息写入缓冲
                jedisClient.set(REDIS_ITEM_KEY+":"+itemId+":param", JsonUtils.objectToJson(itemParamItemList.get(0)));
                //设置key的有效期
                jedisClient.expire(REDIS_ITEM_KEY+":"+itemId+":param",REDIS_ITEM_EXPIRE);
            }catch (Exception e){
                e.printStackTrace();
            }
            return TaotaoResult.ok(itemParamItemList.get(0));
        }
        return TaotaoResult.build(400, "无此商品参数");
    }
}
