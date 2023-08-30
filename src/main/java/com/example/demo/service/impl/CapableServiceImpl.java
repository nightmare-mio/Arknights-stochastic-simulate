/*
 * @Author: nightmare-mio wanglongwei2009@qq.com
 * @Date: 2023-08-26 21:24:32
 * @LastEditTime: 2023-08-30 23:05:21
 * @Description: 
 */
package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    private List<Capable> list = new LinkedList<>();

    private final CapableMapper capableMapper;

    public CapableServiceImpl(CapableMapper capableMapper) {
        this.capableMapper = capableMapper;
        list = capableMapper.selectList(null);
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
    /* 
     * 基于2023年8月30日 特选干员定向
     * 
     */
    public Capable upPool() {
        Map<Map<Capable, Integer>, Integer> pools = new HashMap<>();

        Map<Capable, Integer> p5 = new HashMap<>();
        Map<Capable, Integer> p4 = new HashMap<>();
        Map<Capable, Integer> p3 = new HashMap<>();
        Map<Capable, Integer> p2 = new HashMap<>();

        String np5 = "老鲤|鸿雪|玛恩纳|斥罪";
        String np4 = "赤冬|极光|和弦|洋灰|玫拉    |絮雨";
        String np3 = "夜烟|远山|杰西卡|流星|白雪|清道夫|红豆 |杜宾|缠丸|霜叶|慕斯|砾|暗索|末药|调香师 |角峰|蛇屠箱|古米|深海色|地灵|阿消|猎蜂|格雷伊 |苏苏洛|桃金娘|红云|梅|安比尔|宴|刻刀|波登可|卡达|孑|酸糖|芳汀|泡泡|杰克|松果|豆苗|深靛 |罗比菈塔|褐果|铅踝|休谟斯";
        String np2 = "芬|香草|翎羽|玫兰莎|卡缇|米格鲁|克洛丝|炎熔|芙蓉|安赛尔|史都华德|梓兰|空爆|月见夜|斑点|泡普卡";

        Set<String> sp5 = new HashSet<>(Arrays.asList(np5.trim()));
        Set<String> sp4 = new HashSet<>(Arrays.asList(np4.trim()));
        Set<String> sp3 = new HashSet<>(Arrays.asList(np3.trim()));
        Set<String> sp2 = new HashSet<>(Arrays.asList(np2.trim()));

        p5 = createPool(sp5);
        p4 = createPool(sp4);
        p3 = createPool(sp3);
        p2 = createPool(sp2);
        
        pools.put(p5, 20);
        pools.put(p4, 80);
        pools.put(p3, 500);
        pools.put(p2, 400);

        return getCapable(pools);
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