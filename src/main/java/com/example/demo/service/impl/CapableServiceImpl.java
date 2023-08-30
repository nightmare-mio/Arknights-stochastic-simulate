/*
 * @Author: nightmare-mio wanglongwei2009@qq.com
 * @Date: 2023-08-26 21:24:32
 * @LastEditTime: 2023-08-30 18:06:33
 * @Description: 
 */
package com.example.demo.service.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.Capable;
import com.example.demo.mapper.CapableMapper;
import com.example.demo.service.CapableServiceInter;

import lombok.AllArgsConstructor;
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