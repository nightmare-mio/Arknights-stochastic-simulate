/*
 * @Author: nightmare-mio wanglongwei2009@qq.com
 * @Date: 2023-09-02 23:49:49
 * @LastEditTime: 2023-09-03 15:12:15
 * @Description: 
 */
package com.example.demo.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Configuration;

import com.example.demo.entity.Capable;

@Configuration
public class PoolUtils {

    private static List<Capable> list;

    public PoolUtils(List<Capable> generatedInitLocation) {
        list = generatedInitLocation;
    }

    /*
     * 创建池子
     * 
     */
    public static Map<Capable, Integer> createPool(Set<String> names) {
        return list.stream()
                .filter(it -> names.contains(it.getName()))
                .collect(Collectors.toConcurrentMap(Function.identity(), value -> 1));
    }

    /*
     * 抽卡 概率
     */
    public static Capable getCapable(Map<Map<Capable, Integer>, Integer> pools) {
        return getRandomValueM(pools);
    }

    /* 
     * 抽卡 百分比概率
     */
    public static Capable getCapablePercentage(Map<Map<Capable, Integer>, Float> pools) {
        Map<Map<Capable, Integer>, Integer> iPools = new HashMap<>();
        for (Entry<Map<Capable, Integer>, Float> pool : pools.entrySet()) {
            iPools.put(pool.getKey(), Integer.valueOf((int) Math.round(pool.getValue() * 1000)));
        }
        return getRandomValueM(iPools);
    }

    /*
     * 随机套随机
     */
    public static <T> T getRandomValueM(Map<Map<T, Integer>, Integer> awardMap) {
        // 获取权重和
        int totalWeight = (int) awardMap.values().stream()
                .collect(Collectors.summarizingInt(i -> (int) i)).getSum();
        // 生成一个随机数
        int randNum = new Random().nextInt(totalWeight);
        int prev = 0;

        // 按照权重计算中奖区间
        for (Map.Entry<Map<T, Integer>, Integer> e : awardMap.entrySet()) {
            if (randNum >= prev && randNum < prev + (int) e.getValue()) {
                return getRandomValue((Map<T, Integer>) e.getKey());
            }
            prev = prev + (int) e.getValue();
        }
        return null;
    }

    /*
     * 随机
     */
    public static <T> T getRandomValue(Map<T, Integer> awardMap) {
        // 获取权重和
        int totalWeight = (int) awardMap.values().stream()
                .collect(Collectors.summarizingInt(i -> i)).getSum();
        // 生成一个随机数
        int randNum = new Random().nextInt(totalWeight);
        int prev = 0;
        T choosedAward = null;
        // 按照权重计算中奖区间
        for (Map.Entry<T, Integer> e : awardMap.entrySet()) {
            if (randNum >= prev && randNum < prev + e.getValue()) {
                choosedAward = e.getKey();
                break;
            }
            prev = prev + e.getValue();
        }
        return choosedAward;
    }
}
