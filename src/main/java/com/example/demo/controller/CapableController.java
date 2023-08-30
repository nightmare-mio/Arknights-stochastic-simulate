/*
 * @Author: nightmare-mio wanglongwei2009@qq.com
 * @Date: 2023-08-26 21:24:32
 * @LastEditTime: 2023-08-30 20:52:41
 * @Description: 
 */
package com.example.demo.controller;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Capable;
import com.example.demo.service.impl.CapableServiceImpl;

import lombok.AllArgsConstructor;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author nightmare
 * @since 2023-08-26
 */
@RestController
@RequestMapping("/random")
@AllArgsConstructor
@Validated
public class CapableController {

    private final CapableServiceImpl service;

    @Bean
    public ApplicationRunner executeOnStartup() {
        return args -> {
        };
    }

    /* 
     * 
     */
    @RequestMapping(path = "/{times}", method = RequestMethod.GET)
    public List<Capable> getCapable(
            @NotNull @Positive(message = "大于0") @Max(value = 10, message = "小于10") @PathVariable Integer times) {
        Map<Map<Capable, Integer>, Integer> pool = new HashMap<>();
        pool.put(service.getPool5(), 15);
        pool.put(service.getPool4(), 300);
        pool.put(service.getPool0123(), 685);
        List<Capable> result = new LinkedList<>();
        while (times-- >= 0) {
            Capable capable = service.getCapable(pool);
            result.add(capable);
        }
        return result;
    }

}
