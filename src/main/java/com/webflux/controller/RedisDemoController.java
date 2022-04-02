package com.webflux.controller;


import com.webflux.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/redis")
public class RedisDemoController {

    @Autowired
    private ReactiveStringRedisTemplate redisTemplate;

    @Autowired
    private ReactiveRedisOperations<String, Product> productOps;

    @PostMapping("/save/str")
    public Mono<Boolean> saveStr(@RequestBody Product product) {
        return redisTemplate.opsForValue().set(product.getId(), product.getName());
    }

    @GetMapping("/get")
    public Mono<String> get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @PostMapping("save/object")
    public Mono<Boolean> saveObject(@RequestBody Product product) {
        return productOps.opsForValue().set(product.getId(), product);
    }

}