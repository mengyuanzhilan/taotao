package cn.zmt.portal.controller;

import cn.zmt.pojo.TaotaoResult;
import cn.zmt.portal.pojo.CartItem;
import cn.zmt.portal.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author 赵铭涛
 * @creation time 2018/11/15 - 9:04
 */
@Controller
@RequestMapping("/cart")
public class CartItemController {
    @Autowired
    private CartService cartService;

    /**
     * 添加商品列表
     * @param itemId
     * @param num
     * @param response
     * @param request
     * @return
     */
    @RequestMapping("/add/{itemId}")
    public String addCartItem(@PathVariable("itemId")Long itemId, @RequestParam(defaultValue = "1") Integer num,
                              HttpServletResponse response, HttpServletRequest request){
            cartService.addCartItem(itemId,num,request,response);
        return "redirect:/cart/success.html";
    }
    @RequestMapping("/success")
    public String showSuccess(){
        return "cartSuccess";
    }

    /**
     * 获得商品列表
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/cart")
    public String getCartItemList(HttpServletRequest request, HttpServletResponse response, Model model){
        List<CartItem> cartItemList = cartService.getCartItemList(request, response);
        model.addAttribute("cartList",cartItemList);
        return "cart";
    }

    /**
     * 删除商品
     * @return
     */
    @RequestMapping("/delete/{itemId}")
    public String deleteCartItemList(@PathVariable("itemId") Long itemId, HttpServletRequest request, HttpServletResponse response){
        cartService.deleteCartItemList(itemId,request,response);
        return "redirect:/cart/cart.html";
    }
}
