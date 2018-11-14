package cn.zmt.portal.service.impl;

import cn.zmt.pojo.TaotaoResult;
import cn.zmt.pojo.TbItem;
import cn.zmt.portal.pojo.CatrItem;
import cn.zmt.portal.service.CartService;
import cn.zmt.utils.HttpClientUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author 赵铭涛
 * @creation time 2018/11/14 - 16:32
 */
@Service
public class CartServiceImpl implements CartService {

    @Value("${ITME_INFO_URL}")
    private String ITME_INFO_URL;
    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;
    /**
     * 添加购物车商品
     * @param itemId
     * @param num
     * @return
     */
    @Override
    public TaotaoResult addCartItem(long itemId, int num) {
        //先取购物车商品列表

        //取商品信息
        CatrItem catrItem = new CatrItem();
        //根据商品id查询商品基本信息
        String json = HttpClientUtil.doGet(REST_BASE_URL+ITME_INFO_URL+itemId);
        //把json转换成java对象
        TaotaoResult result = TaotaoResult.formatToPojo(json, TbItem.class);
        if (result.getStatus()==200) {
            TbItem item = (TbItem) result.getData();
            catrItem.setId(item.getId());
            catrItem.setImage(item.getImage() ==null ? "" : item.getImage().split(",")[0]);
            catrItem.setPrice(item.getPrice());
            catrItem.setTitle(item.getTitle());
            catrItem.setNum(num);
        }
        return null;
    }
}
