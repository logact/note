package com.cynovan.neptune.oauth.base.utils;

import com.cynovan.neptune.oauth.base.context.SpringContext;
import com.fasterxml.jackson.databind.JsonNode;
import org.redisson.api.*;

import java.util.concurrent.TimeUnit;

public class RedisUtils {

    public static RedissonClient redissonClient() {
        RedissonClient redissonClient = SpringContext.getBean(RedissonClient.class);
        return redissonClient;
    }

    public static boolean getRateLimiter(String key, Long time, RateIntervalUnit timeUnit) {
        RedissonClient redissonClient = redissonClient();
        RRateLimiter rRateLimiter = redissonClient.getRateLimiter(key);
        if (rRateLimiter.isExists() == false) {
            rRateLimiter.trySetRate(RateType.OVERALL, 1, time, timeUnit);
        }
        return rRateLimiter.tryAcquire(1, 5L, TimeUnit.MILLISECONDS);
    }

    public static boolean has(String key) {
        RedissonClient redissonClient = redissonClient();
        RBucket<String> bucket = redissonClient.getBucket(key);
        return bucket.isExists();
    }

    public static void set(String key, String value) {
        RedissonClient redissonClient = redissonClient();
        RBucket<String> bucket = redissonClient.getBucket(key);
        bucket.set(value);
    }

    public static void set(String key, String hkey, String value) {
        RedissonClient redissonClient = redissonClient();
        RMap<String, String> map = redissonClient.getMap(key);
        map.fastPut(hkey, value);
    }

    public static boolean has(String key, String hkey) {
        return get(key, hkey) != null;
    }

    public static void set(String key, String value, Long l, TimeUnit timeUnit) {
        RedissonClient redissonClient = redissonClient();
        RBucket<String> bucket = redissonClient.getBucket(key);
        bucket.set(value, l, timeUnit);
    }

    public static void delete(String key) {
        RedissonClient redissonClient = redissonClient();
        RBucket<String> bucket = redissonClient.getBucket(key);
        bucket.delete();
    }

    public static void delete(String key, String hkey) {
        RedissonClient redissonClient = redissonClient();
        RMap<String, String> map = redissonClient.getMap(key);

        if (StringLib.isNotEmpty(hkey)) {
            map.remove(hkey);
        } else {
            map.delete();
        }
    }

    public static String get(String key) {
        RedissonClient redissonClient = redissonClient();
        RBucket<String> bucket = redissonClient.getBucket(key);
        return bucket.get();
    }

    public static String get(String key, String hkey) {
        if (StringLib.isEmpty(key) || StringLib.isEmpty(hkey)) {
            return null;
        }
        RedissonClient redissonClient = redissonClient();
        RMap<String, String> map = redissonClient.getMap(key);
        return map.get(hkey);
    }

    public static JsonNode getJSON(String key) {
        String value = get(key);
        if (StringLib.isNotEmpty(value)) {
            return JsonLib.parseJSON(value);
        }
        return null;
    }

    public static void setHeartBeat(String heartBeatKey, String key, String value, Long time, TimeUnit timeUnit) {
        RedissonClient redissonClient = redissonClient();
        RMapCache<String, String> rmap = redissonClient.getMapCache(heartBeatKey);
        rmap.put(key, value, time, timeUnit);
    }

    /**
     * 删除 heartBeatKey 中的 key-value 元素
     */
    public static void removeFromHeartBeat(String heartBeatKey, String key) {
        RedissonClient redissonClient = redissonClient();
        RMapCache<String, String> rmap = redissonClient.getMapCache(heartBeatKey);
        rmap.fastRemove(key);
    }

    public static String getHeartBeat(String heartBeatKey, String key) {
        if (StringLib.isEmpty(heartBeatKey) || StringLib.isEmpty(key)) {
            return null;
        }
        RedissonClient redissonClient = redissonClient();
        RMapCache<String, String> rmap = redissonClient.getMapCache(heartBeatKey);
        return rmap.get(key);
    }
}
