package com.example.demo.pools;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.example.demo.entity.Capable;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "pool")
@Data
public class MyConfig {
    private List<String> standard;
}
