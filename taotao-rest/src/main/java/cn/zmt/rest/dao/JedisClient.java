package cn.zmt.rest.dao;

/**
 * @author zmt
 * @date 2018/11/12 - 7:29
 */
public interface JedisClient {

    String get(String key);
    String set(String key,String value);
    String hget(String hkey,String key);
    String hset(String hkey,String key,String value);
    long incr(String key);
    long expire(String key,long second);
    long ttl(String key);
}
