package com.yiqiandewo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class RedisUtils {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * keys pattern  查看所有符合规则的key
     * @param pattern
     * @return
     */
    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    //String  字符串
    /**
     * set key value
     * @param key
     * @param value
     */
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * get key
     * @param key
     * @return
     */
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * exists key
     * @param key
     * @return
     */
    public boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * incr key
     * value + 1
     * @param key
     */
    public void incr(String key) {
        redisTemplate.opsForValue().increment(key);
    }

    /**
     * incr key l
     * value + l
     * @param key
     * @param l 步长
     */
    public void incr(String key, Long l) {
        redisTemplate.opsForValue().increment(key, l);
    }

    /**
     * decr key
     * value - 1
     * @param key
     */
    public void decr(String key) {
        redisTemplate.opsForValue().decrement(key);
    }

    /**
     * decr key l
     * value - l
     * @param key
     * @param l
     */
    public void decr(String key, Long l) {
        redisTemplate.opsForValue().decrement(key, l);
    }

    /**
     * set if not exist
     * @param key
     * @param value
     */
    public void setNX(String key, Object value) {
        redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    /**
     * del key
     * @param key
     */
    public void del(String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    //List  列表

    /**
     * lpush list value
     * 将值插入列表的头部即左边
     * @param list
     * @param value
     */
    public void lPush(String list, Object value) {
        redisTemplate.opsForList().leftPush(list, value);
    }

    /**
     * rpush list value
     * 将值插入列表的尾部即右边
     * @param list
     * @param value
     */
    public void rPush(String list, Object value) {
        redisTemplate.opsForList().rightPush(list, value);
    }

    /**
     * lrange list l1 l2
     * 当l1为0 l2为-1 即为获取列表所有元素
     * 通过区间获取具体的值
     * @param list
     * @param l1
     * @param l2
     * @return
     */
    public List<Object> lRange(String list, Long l1, Long l2) {
        return redisTemplate.opsForList().range(list, l1, l2);
    }

    /**
     * lpop list
     * 移除list的第一个元素（最左边）
     * @param list
     */
    public void lPop(String list) {
        if (redisTemplate.opsForList().size(list) > 0) {
            redisTemplate.opsForList().leftPop(list);
        }
    }

    /**
     * rpop list
     * 移除list的最后一个元素（最右边）
     * @param list
     */
    public void rPop(String list) {
        if (redisTemplate.opsForList().size(list) > 0) {
            redisTemplate.opsForList().rightPop(list);
        }
    }

    //Set  集合 不能重复

    /**
     * sadd set value
     * @param set
     * @param value
     */
    public void sAdd(String set, Object value) {
        redisTemplate.opsForSet().add(set, value);
    }

    /**
     * smembers set
     * @param set
     * @return
     */
    public Set<Object> sMembers(String set) {
        return redisTemplate.opsForSet().members(set);
    }

    /**
     * sdiff set1 set2
     * 差集 以set1为基准
     * @param set1
     * @param set2
     * @return
     */
    public Set<Object> sDiff(String set1, String set2) {
        return redisTemplate.opsForSet().difference(set1, set2);
    }

    /**
     *
     * sinter set1 set2
     * 交集
     * @param set1
     * @param set2
     * @return
     */
    public Set<Object> sInter(String set1, String set2) {
        return redisTemplate.opsForSet().intersect(set1, set2);
    }

    /**
     * sunion set1 set2
     * 并集
     * @param set1
     * @param set2
     * @return
     */
    public Set<Object> sUnion(String set1, String set2) {
        return redisTemplate.opsForSet().union(set1, set2);
    }

    //Hash  哈希 key-map <key, <key, value>>

    /**
     * hset key hashkey value
     * @param key
     * @param hashKey
     * @param value
     */
    public void hSet(String key, Object hashKey, Object value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    /**
     * hget key hashkey
     * 获取一个字段的值
     * @param key
     * @param hashKey
     */
    public void hGet(String key, Object hashKey) {
        redisTemplate.opsForHash().get(key, hashKey);
    }

    /**
     * hgeall key
     * 获取所有的数据
     * @param key
     * @return
     */
    public Map<Object, Object> hGetAll(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 删除指定的key 对应的value也被删除
     * @param key
     */
    public void hDel(String key) {
        redisTemplate.opsForHash().delete(key);
    }

    //Zset  有序集合

    /**
     * zadd key score value
     * 添加一个值 score
     * @param key
     * @param value
     * @param score
     */
    public void zAdd(String key, Object value, Double score) {
        redisTemplate.opsForZSet().add(key, value, score);
    }

    /**
     * zincrby key delta value
     * 给value增加delta
     * @param key
     * @param value
     */
    public void zIncrby(String key, Object value) {
        redisTemplate.opsForZSet().incrementScore(key, value, 1);
    }

    /**
     * zrange key min max withscores
     * 返回在[min, max]这个区间中的所有值 从小到大
     * @param key
     * @return
     */
    public Set<ZSetOperations.TypedTuple<Object>> zRangeWithScores(String key, long min, long max) {
        return redisTemplate.opsForZSet().rangeWithScores(key, min, max);
    }

    /**
     * zrevrange key min max withscores
     * 返回在[min, max]这个区间中的所有值 从小到大
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Set<ZSetOperations.TypedTuple<Object>> zRevRangeWithScores(String key, long min, long max) {
        return redisTemplate.opsForZSet().reverseRangeWithScores(key, min, max);
    }

    /**
     * zrem key value
     * 移除指定key value
     * @param key
     * @param value
     */
    public void remove(String key, Object value) {
        redisTemplate.opsForZSet().remove(key, value);
    }

}
