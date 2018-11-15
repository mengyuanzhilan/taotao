package cn.zmt.portal.service;

import cn.zmt.pojo.TaotaoResult;
import cn.zmt.portal.pojo.CartItem;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author 赵铭涛
 * @creation time 2018/11/14 - 16:31
 */
public interface CartService {
    TaotaoResult addCartItem(long itemId, int num, HttpServletRequest request,HttpServletResponse response);
    List<CartItem> getCartItemList(HttpServletRequest request, HttpServletResponse response);
    TaotaoResult deleteCartItemList(@PathVariable Long itemId, HttpServletRequest request, HttpServletResponse response);
}
