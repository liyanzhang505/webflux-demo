package com.webflux.webfluxdemo;

import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class BaseDemo {
    public static void main(String[] args) throws Exception {
        test1();
        test2();
        test3();

        Flux<Integer> t4 = test4();
        t4.subscribe(x -> System.out.println("Test4 value:" + x));

        Flux<Integer> t5 = test5();
        t5.subscribe(x -> System.out.println("Test5 value:" + x));

        System.out.println("Wait for test4 to complete");
        Thread.sleep(6000);
    }

    public static void test1() {
        System.out.println("====Test1 Subscribe====");
        Flux<Integer> a = Flux.just(1, 2, 3);
        a.subscribe(x -> System.out.println(x));
        a.subscribe(System.out::println);

        a.subscribe(x -> System.out.println(x + 2),
                System.out::println,
                () -> System.out.println("Completed"));
    }

    public static void test2() {
        System.out.println("====Test2 error====");
        Mono<Integer> a =  Mono.error(new Exception("My Exception..."));
        a.subscribe(System.out::println,
                x -> System.out.println("Exception caught:" + x),
                () -> System.out.println("Completed"));
    }

    public static void test3() {
        System.out.println("====Test3 BaseSubscriber====");
        Flux.range(1, 8)
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

    public static Flux<Integer>  test4() {
        System.out.println("====Test4 Delay Print====");
        return Flux.range(1, 8)
                .delayElements(Duration.ofMillis(500))
                .doOnComplete(() -> System.out.println("Test4 Completed"));
    }

    public static Flux<Integer> test5() {
        Mono.just("====Test5 doOnNext====").subscribe(System.out::println);
        return Flux.range(1, 3)
                .doOnNext(x-> System.out.println("Test5 next:" + x * 3))
                .doOnComplete(() -> System.out.println("Test5 Completed"));

    }
}
