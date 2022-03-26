package com.webflux.service;

import com.webflux.model.Student;
import com.webflux.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Flux<Student> findAll() {
        return studentRepository.findAll();
    }

    public Mono<Student> save(Student student) {
        System.out.println("StudentRepository:" + studentRepository);
        return studentRepository.save(student)
                .doOnError(System.out::println);
    }
}
