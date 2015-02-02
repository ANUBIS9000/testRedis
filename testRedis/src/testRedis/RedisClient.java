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

    private Jedis jedis;//����Ƭ��ͻ�������
    private JedisPool jedisPool;//����Ƭ���ӳ�
    private ShardedJedis shardedJedis;//��Ƭ��ͻ�������
    private ShardedJedisPool shardedJedisPool;//��Ƭ���ӳ�
    
    public RedisClient() 
    { 
        initialPool(); 
        initialShardedPool(); 
        shardedJedis = shardedJedisPool.getResource(); 
        jedis = jedisPool.getResource(); 
        
        
    } 
 
    /**
     * ��ʼ������Ƭ��
     */
    private void initialPool() 
    { 
        // �ػ������� 
        JedisPoolConfig config = new JedisPoolConfig(); 
        config.setMaxActive(20); 
        config.setMaxIdle(5); 
        config.setMaxWait(1000l); 
        config.setTestOnBorrow(false); 
        
        jedisPool = new JedisPool(config,"127.0.0.1",6379);
    }
    
    /** 
     * ��ʼ����Ƭ�� 
     */ 
    private void initialShardedPool() 
    { 
        // �ػ������� 
        JedisPoolConfig config = new JedisPoolConfig(); 
        config.setMaxActive(20); 
        config.setMaxIdle(5); 
        config.setMaxWait(1000l); 
        config.setTestOnBorrow(false); 
        // slave���� 
        List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>(); 
        shards.add(new JedisShardInfo("127.0.0.1", 6379, "master")); 

        // ����� 
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
    	  System.out.println("����key001,value001��ֵ�ԣ�"+shardedJedis.set("key001", "value001")); 
          System.out.println("�ж�key001�Ƿ���ڣ�"+shardedJedis.exists("key001"));
          // ���ϵͳ�����е�key
          System.out.println("����key002,value002��ֵ�ԣ�"+shardedJedis.set("key002", "value002"));
          System.out.println("ϵͳ�����м����£�");
          Set<String> keys = jedis.keys("*"); 
      }

      private void StringOperate() {
    	  System.out.println("======================String_1=========================="); 
          // ������� 
          System.out.println("��տ����������ݣ�"+jedis.flushDB());
          
          System.out.println("=============��=============");
          jedis.set("key001","value001");
          jedis.set("key002","value002");
          System.out.println("�������ļ�ֵ�����£�");
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