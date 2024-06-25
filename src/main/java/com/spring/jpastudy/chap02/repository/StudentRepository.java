package com.spring.jpastudy.chap02.repository;

import com.spring.jpastudy.chap02.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, String> {

    // 쿼리메서드 : 메서드 이름에 특별한 규칙을 적용하면 SQL이 규칙에 맞게 생성된다.
    List<Student> findByName(String name);  //카멜케이스 적용해야 함. findBy + 어쩌구(stu_name쓰면 안됨)
    // Optional findByName(String name); => 단일조회
}
