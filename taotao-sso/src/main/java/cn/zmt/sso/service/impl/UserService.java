package cn.zmt.sso.service.impl;

import cn.zmt.mapper.TbUserMapper;
import cn.zmt.pojo.TaotaoResult;
import cn.zmt.pojo.TbUser;
import cn.zmt.pojo.TbUserExample;
import cn.zmt.sso.dao.JedisClient;
import cn.zmt.utils.CookieUtils;
import cn.zmt.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author 赵铭涛
 * @creation time 2018/11/14 - 10:19
 */
@Service
public class UserService implements cn.zmt.sso.service.UserService {
    @Autowired
    private TbUserMapper userMapper;
    @Autowired
    private JedisClient jedisClient;
    @Value("${REDIS_USER_SESSION_KEY}")
    private String REDIS_USER_SESSION_KEY;
    @Value("${SSO_SESSION_EXPIRE}")
    private int SSO_SESSION_EXPIRE;
    @Value("${TT_TOKEN}")
    private String TT_TOKEN;
    /**
     * 判断username、phone、email是否重复
     * @param content
     * @param type
     * @return
     */
    @Override
    public TaotaoResult checkDate(String content, Integer type) {
        //创建查询条件
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        //对数据进行校验：1,2、3分别代表username、phone、email
        //用户名校验
        if (1==type) {
            criteria.andUsernameEqualTo(content);
            //电话校验
        }else if(2==type){
            criteria.andPhoneEqualTo(content);
            //email校验
        }else {
            criteria.andEmailEqualTo(content);
        }
        //执行查询
        List<TbUser> list = userMapper.selectByExample(example);
        if (list == null || list.size() == 0){
            return TaotaoResult.ok(true);
        }
        return TaotaoResult.ok(false);
    }

    /**
     * 添加用户
     * @param user
     * @return
     */
    @Override
    public TaotaoResult createUser(TbUser user) {
        //补全pojo
        user.setCreated(new Date());
        user.setUpdated(new Date());
        //md5加密
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        userMapper.insert(user);
        return TaotaoResult.ok();
    }

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    @Override
    public TaotaoResult userLogin(String username, String password, HttpServletRequest request, HttpServletResponse response) {
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<TbUser> userList = userMapper.selectByExample(example);
        //如果没有此用户名
        if (null==userList || userList.size()==0) {
            return TaotaoResult.build(400,"用户名或密码错误");
        }
        TbUser user = userList.get(0);
        //比对密码
        if (!DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())){
            return TaotaoResult.build(400,"用户名或密码错误");
        }
        //生成token
        String token = UUID.randomUUID().toString();
        //保存用户之前，吧用户对象中的密码清空
        user.setPassword(null);
        //把用户信息写入redis
        jedisClient.set(REDIS_USER_SESSION_KEY+":"+token, JsonUtils.objectToJson(user));
        //设置session的过期时间
        jedisClient.expire(REDIS_USER_SESSION_KEY+":"+token,SSO_SESSION_EXPIRE);
        //添加写入cookie,浏览器关闭cookie失效
        CookieUtils.setCookie(request,response,TT_TOKEN,token);
        System.out.println(CookieUtils.getCookieValue(request,TT_TOKEN));
        //返回token
        return TaotaoResult.ok(token);
    }


    /**
     * 查询redis session
     * @param token
     * @return
     */
    @Override
    public TaotaoResult getUserByToken(String token) {
        //根据token查询redis用户信息
        String json = jedisClient.get(REDIS_USER_SESSION_KEY+":"+token);
        //判断是否为空
        if(StringUtils.isBlank(json)){
            return TaotaoResult.build(400,"次session已过期，请重新登录。");
        }
        //更新过期时间
        jedisClient.expire(REDIS_USER_SESSION_KEY+":"+token,SSO_SESSION_EXPIRE);
        //返回用户信息
        return TaotaoResult.ok(json);
    }


}
