package com.spring.jpastudy.chap06_querydsl.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.jpastudy.chap06_querydsl.entity.Group;
import com.spring.jpastudy.chap06_querydsl.entity.Idol;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.spring.jpastudy.chap06_querydsl.entity.QIdol.idol;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
//@Rollback(false)
class QueryDslSortTest {

    @Autowired
    IdolRepository idolRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    JPAQueryFactory factory;


    @BeforeEach
    void setUp() {

        //given
        Group leSserafim = new Group("르세라핌");
        Group ive = new Group("아이브");

        groupRepository.save(leSserafim);
        groupRepository.save(ive);

        Idol idol1 = new Idol("김채원", 24, leSserafim);
        Idol idol2 = new Idol("사쿠라", 26, leSserafim);
        Idol idol3 = new Idol("가을", 22, ive);
        Idol idol4 = new Idol("리즈", 20, ive);
        Idol idol5 = new Idol("장원영", 20, ive);

        idolRepository.save(idol1);
        idolRepository.save(idol2);
        idolRepository.save(idol3);
        idolRepository.save(idol4);


    }

    @Test
    @DisplayName("QueryDSL로 기본 정렬하기")
    void sortingTest() {
        //given

        //when
        List<Idol> sortedIdols = factory
                .selectFrom(idol)
                .orderBy(idol.age.desc())
                .fetch();
        //then
        assertFalse(sortedIdols.isEmpty());

        System.out.println("\n\n\n");
        sortedIdols.forEach(System.out::println);
        System.out.println("\n\n\n");

        // 추가 검증 예시: 첫 번째 아이돌이 나이가 가장 많고 이름이 올바르게 정렬되었는지 확인
        assertEquals("사쿠라", sortedIdols.get(0).getIdolName());
        assertEquals(26, sortedIdols.get(0).getAge());
    }





}