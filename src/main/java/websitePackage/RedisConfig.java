package websitePackage;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisConfig{
    private static final String REDIS_HOST="localhost";
    private static final int REDIS_PORT=6379;

    private static JedisPool jedisPool;

    static {
        JedisPoolConfig poolConfig=new JedisPoolConfig();
        jedisPool=new JedisPool(poolConfig,REDIS_HOST,REDIS_PORT);
    }

    public static Jedis getResource(){
        return jedisPool.getResource();
    }

    public static void returnResource(Jedis jedis){
        if (jedis!=null){
            jedis.close();
        }
    }
}

