package testRedis;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.SortingParams;



public class RedisClient {

    private Jedis jedis;//非切片额客户端连接
    private JedisPool jedisPool;//非切片连接池
    private ShardedJedis shardedJedis;//切片额客户端连接
    private ShardedJedisPool shardedJedisPool;//切片连接池
    
    public RedisClient() 
    { 
        initialPool(); 
        initialShardedPool(); 
        shardedJedis = shardedJedisPool.getResource(); 
        jedis = jedisPool.getResource(); 
        
        
    } 
 
    /**
     * 初始化非切片池
     */
    private void initialPool() 
    { 
        // 池基本配置 
        JedisPoolConfig config = new JedisPoolConfig(); 
        config.setMaxActive(20); 
        config.setMaxIdle(5); 
        config.setMaxWait(1000l); 
        config.setTestOnBorrow(false); 
        
        jedisPool = new JedisPool(config,"127.0.0.1",6379);
    }
    
    /** 
     * 初始化切片池 
     */ 
    private void initialShardedPool() 
    { 
        // 池基本配置 
        JedisPoolConfig config = new JedisPoolConfig(); 
        config.setMaxActive(20); 
        config.setMaxIdle(5); 
        config.setMaxWait(1000l); 
        config.setTestOnBorrow(false); 
        // slave链接 
        List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>(); 
        shards.add(new JedisShardInfo("127.0.0.1", 6379, "master")); 

        // 构造池 
        shardedJedisPool = new ShardedJedisPool(config, shards); 
    } 

    public void show() {     
        KeyOperate(); 
        StringOperate(); 
        ListOperate(); 
        SetOperate();
        SortedSetOperate();
        HashOperate(); 
        jedisPool.returnResource(jedis);
        shardedJedisPool.returnResource(shardedJedis);
    } 

      private void KeyOperate() {
    	  System.out.println("新增key001,value001键值对："+shardedJedis.set("key001", "value001")); 
          System.out.println("判断key001是否存在："+shardedJedis.exists("key001"));
          // 输出系统中所有的key
          System.out.println("新增key002,value002键值对："+shardedJedis.set("key002", "value002"));
          System.out.println("系统中所有键如下：");
          Set<String> keys = jedis.keys("*"); 
      }

      private void StringOperate() {
    	  System.out.println("======================String_1=========================="); 
          // 清空数据 
          System.out.println("清空库中所有数据："+jedis.flushDB());
          
          System.out.println("=============增=============");
          jedis.set("key001","value001");
          jedis.set("key002","value002");
          System.out.println("已新增的键值对如下：");
          System.out.println(jedis.get("key001"));
          System.out.println(jedis.get("key002"));
      }

      private void ListOperate() {
         
      }

      private void SetOperate() {
        
      }

      private void SortedSetOperate() {
        
      }
    
      private void HashOperate() {
         
      }
}