package com.webflux.webfluxdemo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Test1 {
    public static void main(String[] args) {
        test1();
        test2();
    }


    public static void test1() {
        Mono.just("====Test1====").subscribe(System.out::println);
        Flux<Integer> a = Flux.just(1, 2, 3);
        a.subscribe(x -> System.out.println(x));
        a.subscribe(System.out::println);

        a.subscribe(x -> System.out.println(x + 2),
                System.out::println,
                () -> System.out.println("Completed"));
    }

    public static void test2() {
        Mono.just("====Test2====").subscribe(System.out::println);
        Mono<Integer> a =  Mono.error(new Exception("My Exception..."));
        a.subscribe(System.out::println,
                x -> System.out.println("Exception caught:" + x),
                () -> System.out.println("Completed"));
    }
}
