package cn.zmt.portal.service.impl;

import cn.zmt.pojo.TaotaoResult;
import cn.zmt.pojo.TbItem;
import cn.zmt.portal.pojo.CartItem;
import cn.zmt.portal.service.CartService;
import cn.zmt.utils.CookieUtils;
import cn.zmt.utils.HttpClientUtil;
import cn.zmt.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

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
    @Value("${TT_CART}")
    private String TT_CART;
    /**
     * 添加购物车商品
     * @param itemId
     * @param num
     * @return
     */
    @Override
    public TaotaoResult addCartItem(long itemId, int num,HttpServletRequest request,HttpServletResponse response) {
        //取商品信息
        CartItem cartItem = null;
        //取购物车商品列表
        List<CartItem> itemList = getCartItemList(request);
        //判断购物车商品列表中是否存在此商品
        for (CartItem cItem : itemList) {
            //如果存在此商品
            if (cItem.getId() == itemId) {
                //增加商品数量
                cItem.setNum(cItem.getNum() + num);
                cartItem = cItem;
                break;
            }
        }
        if (cartItem == null) {
            cartItem = new CartItem();
            //根据商品id查询商品基本信息。
            String json = HttpClientUtil.doGet(REST_BASE_URL + ITME_INFO_URL + itemId);
            //把json转换成java对象
            TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, TbItem.class);
            if (taotaoResult.getStatus() == 200) {
                TbItem item = (TbItem) taotaoResult.getData();
                cartItem.setId(item.getId());
                cartItem.setTitle(item.getTitle());
                cartItem.setImage(item.getImage() == null ? "":item.getImage().split(",")[0]);
                cartItem.setNum(num);
                cartItem.setPrice(item.getPrice());
            }
            //添加到购物车列表
            itemList.add(cartItem);
        }
        //把购物车列表写入cookie
        CookieUtils.setCookie(request, response, TT_CART, JsonUtils.objectToJson(itemList), true);

        return TaotaoResult.ok();
    }

    /**
     * 从cookie中取商品列表
     * @return
     */
    private List<CartItem>  getCartItemList(HttpServletRequest request){
        //从cookie中取商品列表
        String cartJson = CookieUtils.getCookieValue(request, TT_CART, true);
        if(cartJson == null){
            return new ArrayList<>();
        }
        //把json转换成商品列表
        try {
            List<CartItem> list = JsonUtils.jsonToList(cartJson, CartItem.class);
            return list;
        }catch (Exception e){
            e.printStackTrace();
        }
        return  new ArrayList<>();
    }

    /**
     * 获得购物车列表
     * @param request
     * @param response
     * @return
     */
    @Override
    public List<CartItem> getCartItemList(HttpServletRequest request, HttpServletResponse response) {
        return getCartItemList(request);
    }

    /**
     * 从cookie中删除商品
     * @param itemId
     * @param request
     * @param response
     * @return
     */
    @Override
    public TaotaoResult deleteCartItemList(Long itemId, HttpServletRequest request, HttpServletResponse response) {
        //从cookie中获得商品列表
        List<CartItem> list = getCartItemList(request);
        //从列表中找到商品
        for (CartItem cartItem : list) {
            if (cartItem.getId()==itemId){
                list.remove(cartItem);
                break;
            }
        }
        //把购物车列表重新写入cookie
        CookieUtils.setCookie(request,response,TT_CART,JsonUtils.objectToJson(list),true);
        return TaotaoResult.ok();
    }
}
