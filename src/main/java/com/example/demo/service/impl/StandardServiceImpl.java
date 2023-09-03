/*
 * @Author: nightmare-mio wanglongwei2009@qq.com
 * @Date: 2023-09-02 14:48:23
 * @LastEditTime: 2023-09-03 15:24:16
 * @Description: 
 */
package com.example.demo.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Capable;
import com.example.demo.pools.MyConfig;
import com.example.demo.util.PoolUtils;

@Service
@DependsOn("poolUtils")
public class StandardServiceImpl {

    private List<String> standard;
    Map<Map<Capable, Integer>, Float> pools = new HashMap<>();

    Map<Capable, Integer> u5 = new HashMap<>();
    Map<Capable, Integer> u4 = new HashMap<>();
    Map<Capable, Integer> p5 = new HashMap<>();
    Map<Capable, Integer> p4 = new HashMap<>();
    Map<Capable, Integer> p3 = new HashMap<>();
    Map<Capable, Integer> p2 = new HashMap<>();

    /* 抽卡计数 需要重数据源获取 */
    private Integer count = 0;

    public StandardServiceImpl(MyConfig myConfig) {
        this.standard = myConfig.getStandard();
        List<String> s1 = Arrays.asList(standard.get(0).replaceAll("\\s+", " ").split("\\|"));
        List<String> s3 = Arrays.asList(standard.get(1).replaceAll("\\s+", " ").split("\\|"));
        List<String> s2 = Arrays.asList(standard.get(2).replaceAll("\\s+", " ").split("\\|"));
        List<String> s4 = Arrays.asList(standard.get(3).replaceAll("\\s+", " ").split("\\|"));
        List<String> s5 = Arrays.asList(standard.get(4).replaceAll("\\s+", " ").split("\\|"));
        List<String> s6 = Arrays.asList(standard.get(5).replaceAll("\\s+", " ").split("\\|"));

        pools.put(u5 = initUp5(), 0.01F);
        pools.put(u4 = initUp4(), 0.04F);
        pools.put(p5 = initpool5(), 0.01F);
        pools.put(p4 = initpool4(), 0.04F);
        pools.put(p3 = initpool3(), 0.5F);
        pools.put(p2 = initpool2(), 0.4F);
    }

    public Capable getCapable() {
        if (count >= 50) {
            pools.put(u5, pools.get(u5) + 0.01F);
            pools.put(p5, pools.get(p5) + 0.01F);
            Float maxValue = 0F;
            Map<Capable, Integer> maxKey = null;
            for (Map.Entry<Map<Capable, Integer>, Float> e : pools.entrySet()) {
                if (!e.getKey().equals(p5) && maxValue < e.getValue()) {
                    maxValue = e.getValue();
                    maxKey = e.getKey();
                }
            }
            pools.put(maxKey, maxValue - 0.02F);
            count = 0 + 1;
        }
        return clearCount(PoolUtils.getCapablePercentage(pools));
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

    private Map<Capable, Integer> initUp5() {
        return PoolUtils.createPool(new HashSet<>(Arrays.asList(standard.get(0).replaceAll("\\s+", " ").split("\\|"))));
    }

    private Map<Capable, Integer> initUp4() {
        return PoolUtils.createPool(new HashSet<>(Arrays.asList(standard.get(1).replaceAll("\\s+", " ").split("\\|"))));
    }

    private Map<Capable, Integer> initpool5() {
        return PoolUtils.createPool(new HashSet<>(Arrays.asList(standard.get(2).replaceAll("\\s+", " ").split("\\|"))));
    }

    private Map<Capable, Integer> initpool4() {
        return PoolUtils.createPool(new HashSet<>(Arrays.asList(standard.get(3).replaceAll("\\s+", " ").split("\\|"))));
    }

    private Map<Capable, Integer> initpool3() {
        return PoolUtils.createPool(new HashSet<>(Arrays.asList(standard.get(4).replaceAll("\\s+", " ").split("\\|"))));
    }

    private Map<Capable, Integer> initpool2() {
        return PoolUtils.createPool(new HashSet<>(Arrays.asList(standard.get(5).replaceAll("\\s+", " ").split("\\|"))));
    }
}
