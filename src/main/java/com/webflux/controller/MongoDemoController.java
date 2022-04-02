package com.webflux.controller;


import com.webflux.model.Student;
import com.webflux.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequestMapping("/student")
public class MongoDemoController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/hello")
    public Mono<String> hello() {
        return Mono.just("HelloWorld");
    }

    @PostMapping("/save")
    public Mono<Student> save(@RequestBody Student student) {
        return this.studentService.save(student);
    }

    @GetMapping(value = "/all")
    public Flux<Student> findAll() {
        return this.studentService.findAll().delayElements(Duration.ofSeconds(1));
    }
}