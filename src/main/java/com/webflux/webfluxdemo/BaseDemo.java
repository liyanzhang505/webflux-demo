package com.webflux.webfluxdemo;

import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

public class BaseDemo {
    public static void main(String[] args) {
        test1();
        test2();
        test3();
        test4();
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

    public static void test3() {
        Mono.just("====Test3====").subscribe(System.out::println);
        Flux.range(1, 6)
//                .doOnRequest(n -> System.out.println("Request: " + n ))
                .subscribe(new BaseSubscriber<Integer>() {
                    @Override
                    protected void hookOnSubscribe(Subscription subscription) {
                        System.out.println("Subscribed");
                        super.hookOnSubscribe(subscription);
                    }

                    @Override
                    protected void hookOnNext(Integer value) {
                        try {
                            TimeUnit.SECONDS.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("Get value : " + value);
                    }
                });
    }
    public static void test4() {
        Mono.just("====Test4====").subscribe(System.out::println);
        Mono.just("HelloWorld")
                .subscribe(new BaseSubscriber<String>() {
                    @Override
                    protected void hookOnNext(String value) {
                        System.out.println("Get value : " + value);
                    }
                });
    }
}
