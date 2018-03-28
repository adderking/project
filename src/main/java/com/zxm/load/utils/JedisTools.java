package com.zxm.load.utils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Iterator;
import java.util.Map;

public class JedisTools {

    private static JedisPool pool = new JedisPool(new JedisPoolConfig(), SystemConfig.getString(Constants.REDIS_HOST));
    private static Jedis jedis;
    private static Integer overtime;

    static {
        String v = SystemConfig.getString(Constants.MAC_CAR_PAIR_OVERTIME);
        overtime = Integer.parseInt(v);
        jedis = pool.getResource();

    }

    public static void addCarMacPair(Map<String, Integer> carMapPairs) {

        Iterator<String> it = carMapPairs.keySet().iterator();
        while(it.hasNext()) {
            String key = it.next();
            String jValue = jedis.get(key);
            if(jValue != null) {
                int sum = Integer.parseInt(jValue);
                if(sum > 3) {
                    System.out.println("====>>> " + key + "  " + sum);
                }
                System.out.println("====>>> " + key + "  " + sum);
                jedis.set(key, String.valueOf(sum+carMapPairs.get(key)));
                jedis.expire(key, overtime);
            } else {
                jedis.set(key, String.valueOf(carMapPairs.get(key)));
            }
        }
    }

    public static void setOffset(String equipmentId, String fileName, int lineIndex) {
        jedis.set("offset_" + equipmentId, fileName+":"+lineIndex);
        jedis.close();
    }

    public static String getOffset(String equipmentId) {
        String offset = jedis.get("offset_" + equipmentId);
        return offset;
    }

    public void close() {
        if(jedis != null) jedis.close();
        if(pool != null) pool.close();
    }

}
