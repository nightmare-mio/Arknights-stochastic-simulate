/*
 * @Author: nightmare-mio wanglongwei2009@qq.com
 * @Date: 2023-08-26 21:24:32
 * @LastEditTime: 2023-09-02 13:30:13
 * @Description: 
 */
package com.example.demo.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.Capable;
import com.example.demo.mapper.CapableMapper;
import com.example.demo.service.CapableServiceInter;

import lombok.Data;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author nightmare
 * @since 2023-08-26
 */

@Service
@Lazy
@Data
public class CapableServiceImpl extends ServiceImpl<CapableMapper, Capable> implements CapableServiceInter {

    private Map<Capable, Integer> pool5 = new HashMap<Capable, Integer>();
    private Map<Capable, Integer> pool4 = new HashMap<Capable, Integer>();
    private Map<Capable, Integer> pool0123 = new HashMap<Capable, Integer>();
    private Map<Capable, Integer> up5 = new HashMap<Capable, Integer>();
    private Map<Capable, Integer> up4 = new HashMap<Capable, Integer>();
    /* 所有干员信息 */
    private List<Capable> list;
    /* 抽卡计数 需要重数据源获取 */
    private Integer count = 0;

    private final CapableMapper capableMapper;

    @Value("${pool.np5}")
    private String np5;
    @Value("${pool.np4}")
    private String np4;
    @Value("${pool.np3}")
    private String np3;
    @Value("${pool.np2}")
    private String np2;
    Map<Capable, Integer> p5 = new HashMap<>();
    Map<Capable, Integer> p4 = new HashMap<>();
    Map<Capable, Integer> p3 = new HashMap<>();
    Map<Capable, Integer> p2 = new HashMap<>();
    Map<Map<Capable, Integer>, Integer> pools = new HashMap<>();

    public CapableServiceImpl(CapableMapper capableMapper, List<Capable> generatedInitLocation) {
        this.list = generatedInitLocation != null ? generatedInitLocation : capableMapper.selectList(null);
        this.capableMapper = capableMapper;

        list.forEach(item -> {
            switch (item.getRarity()) {
                case "5":
                    pool5.put(item, 1);
                    break;
                case "4":
                    pool4.put(item, 1);
                    break;
                default:
                    pool0123.put(item, 1);
                    break;
            }
        });
    }

    @Bean
    public ApplicationRunner initPools() {
        return args -> {
            Set<String> sp5 = new HashSet<>(Arrays.asList(np5.replaceAll("\\s+", " ").split("\\|")));
            Set<String> sp4 = new HashSet<>(Arrays.asList(np4.replaceAll("\\s+", " ").split("\\|")));
            Set<String> sp3 = new HashSet<>(Arrays.asList(np3.replaceAll("\\s+", " ").split("\\|")));
            Set<String> sp2 = new HashSet<>(Arrays.asList(np2.replaceAll("\\s+", " ").split("\\|")));

            p5 = createPool(sp5);
            p4 = createPool(sp4);
            p3 = createPool(sp3);
            p2 = createPool(sp2);

            pools.put(p5, 20);
            pools.put(p4, 80);
            pools.put(p3, 500);
            pools.put(p2, 400);
        };
    }
    /*
     * 基于2023年8月30日 特选干员定向
     * 每50抽提升2%
     */
    public Capable upPool() {
        if (count >= 50) {
            pools.put(p5, pools.get(p5) + 20);
            int maxValue = 0;
            Map<Capable, Integer> maxKey = null;
            for (Map.Entry<Map<Capable, Integer>, Integer> e : pools.entrySet()) {
                if (!e.getKey().equals(p5) && maxValue < e.getValue()) {
                    maxValue = e.getValue();
                    maxKey = e.getKey();
                }
            }
            pools.put(maxKey, maxValue - 20);
            count = 0 + 1;
        }
        return clearCount(getCapable(pools));
    }

    /*
     * 重置计数
     */
    public Capable clearCount(Capable capable) {
        if (capable.getRarity().equals("5")) {
            count = 0;
        }
        return capable;
    }

    /*
     * 创建池子
     * 
     */
    public Map<Capable, Integer> createPool(Set<String> names) {
        return list.stream()
                .filter(it -> names.contains(it.getName()))
                .collect(Collectors.toConcurrentMap(Function.identity(), value -> 1));
    }

    /*
     * 抽卡
     */
    public Capable getCapable(Map<Map<Capable, Integer>, Integer> pool) {
        return getRandomValueM(pool);
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