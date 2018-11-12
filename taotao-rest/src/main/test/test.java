import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author 赵铭涛
 * @creation time 2018/11/12 - 10:00
 */
public class test {

    @Test
    public void test(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-jedis.xml");
        JedisPool jedisPool = (JedisPool) applicationContext.getBean("redisClient");
        Jedis jedis = jedisPool.getResource();
        String str = jedis.get("key1");
        System.out.println(str);
        jedis.close();
        jedisPool.close();
    }
}
