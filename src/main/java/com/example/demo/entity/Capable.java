/*
 * @Author: nightmare-mio wanglongwei2009@qq.com
 * @Date: 2023-08-26 21:24:32
 * @LastEditTime: 2023-08-29 20:43:17
 * @Description: 
 */
package com.example.demo.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author nightmare
 * @since 2023-08-26
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "capable") // 创建的表名
public class Capable implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    // 名字
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String name;
    // 稀有度 0-5
    private String rarity;
    // 职业
    private String profession;
    // 头像
    private String ico;

}
