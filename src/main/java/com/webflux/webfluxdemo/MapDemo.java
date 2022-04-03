package com.webflux.webfluxdemo;

import com.webflux.model.Student;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class MapDemo {
    public static void main(String[] args) throws Exception {
        test1();
        test2();
        Flux<String > t3 = test3();
        t3.subscribe(x -> System.out.println("Test3 get:" + x));
        test4();

        test5();
        Flux<String > t6 = test6();
        t6.subscribe(x -> System.out.println("Test6 get:" + x));
        System.out.println("Wait for tests to complete");
        Thread.sleep(3000);


    }

    public static void test1() {
        Mono.just("====Test1 Test Map====").subscribe(System.out::println);
        Flux<Integer> a = Flux.just(1, 2, 3)
                .log()
                .map(i -> i * i);

     a.subscribe(x -> System.out.println("Test1 get:" + x),
                System.out::println,
                () -> System.out.println("Test1 Completed"));
    }

    public static void test2() {
        Mono.just("====Test2 Test flatMap====").subscribe(System.out::println);
        Flux<Integer> t2 = Flux.range(1, 5)
                .log()
                .flatMap(x -> {
                    return Flux.just(x * x);
                })
                .doOnComplete(() -> System.out.println("t2 completed"));
        t2.subscribe(System.out::println);

    }

    public static Flux<String> test3() {
        System.out.println("====Test3 Test flatMap return ====");
        return Flux.range(1, 5)
                .log()
                .flatMap(x -> {
                    return Flux.just("Str" + String.valueOf(x)).delayElements(Duration.ofMillis(1000));
                })
                .doOnComplete(() -> System.out.println("t3 completed"));
    }


    public static void test4() {
        System.out.println("====Test4 Test flatMap async====");
        // The elements in final Flux object returned by flatMap is unordered.
        Flux.range(1, 5)
                .log()
                .flatMap(x -> {
                    return Flux.just(x * 2).delayElements(Duration.ofMillis(1000));
                })
                .doOnComplete(() -> System.out.println("Test4 completed"))
                .subscribe(x -> System.out.println("Test4 get:" + x));

        try {
            Thread.sleep(6000);
        } catch (Exception e) {

        }
    }

    public static void test5() {
        Mono.just("====Test5 Test Map====").subscribe(System.out::println);
        Flux<Student> a = Flux.just(1, 2, 3)
                .map(id -> new Student(String.valueOf(id), "str" + id));
        a.subscribe(System.out::println);
    }

    public static Flux<String> test6() {
        System.out.println("====Test6 Test map return ====");
        return Flux.range(1, 5)
                .log()
                .map(x -> {
                    return ("Str" + String.valueOf(x));
                })
                .doOnComplete(() -> System.out.println("t6 completed"));
    }
}
