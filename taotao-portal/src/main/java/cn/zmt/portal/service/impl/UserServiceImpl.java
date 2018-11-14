package cn.zmt.portal.service.impl;

import cn.zmt.pojo.TaotaoResult;
import cn.zmt.pojo.TbUser;
import cn.zmt.portal.service.UserService;
import cn.zmt.utils.HttpClientUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 用户管理Service
 * @author 赵铭涛
 * @creation time 2018/11/14 - 14:24
 */
@Service
public class UserServiceImpl implements UserService {

    @Value("${SSO_BASE_URL}")
    public String SSO_BASE_URL;
    @Value("${SSO_USER_TOKEN}")
    public String SSO_USER_TOKEN;
    @Value("${SSO_PAGE_LOGIN}")
    public String SSO_PAGE_LOGIN;
    @Value("${TT_TOKEN}")
    public String TT_TOKEN;
    @Override
    public TbUser getUserByToken(String token) {
        try {
            //调用sso系统的服务，根据token取用户信息
            String json = HttpClientUtil.doGet(SSO_BASE_URL+SSO_USER_TOKEN+token);
            //把json转换成TaotaoResult
            TaotaoResult result = TaotaoResult.formatToPojo(json,TbUser.class);
            if (result.getStatus()==200){
                TbUser user = (TbUser) result.getData();
                return user;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
