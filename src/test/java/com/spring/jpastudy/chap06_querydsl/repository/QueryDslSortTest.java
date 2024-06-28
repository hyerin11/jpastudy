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


    @Test
    @DisplayName("페이징 처리하기")
    void pagingTest() {
        //given
        int pageNo = 1;
        int amount = 2;

        //when
        List<Idol> pagedIdols = factory
                .selectFrom(idol)
                .orderBy(idol.age.desc()) // 나이로 정렬
//                .offset(2) //2명씩이니까 2면 페이지, 다음 페이지 보려면 4
//                .limit(2) //1페이지에 2개씩 (0,2)
                .offset((pageNo-1)*amount)
                .limit(amount)
                .fetch();

        // 총 데이터 수
        Long totalCount = factory
                .select(idol.count())
                .from(idol)
                .fetchOne()
                ;

        //then
        System.out.println("\n\n\n");
        pagedIdols.forEach(System.out::println);
        System.out.println("\n\n\n");
        System.out.println("\ntotalCount = " + totalCount);
       assertTrue(totalCount==5);

    }

    //아이돌을 이름 기준으로 오름차순으로 정렬하여 조회하세요.
    @Test
    @DisplayName("아이돌 이름 기준 오름차순으로 정렬하기")
    void sortingByName() {
        //given

        //when
        List<Idol> sortedIdolName = factory
                .selectFrom(idol)
                .orderBy(idol.idolName.asc())
                .fetch();
        //then
        assertFalse(sortedIdolName.isEmpty());

        System.out.println("\n\n\n");
        sortedIdolName.forEach(System.out::println);
        System.out.println("\n\n\n");

    }


    //아이돌을 나이 기준으로 내림차순 정렬하고, 페이지당 3명씩 페이징 처리하여 1번째 페이지의 아이돌을 조회하세요.
    @Test
    @DisplayName("나이 기준 내림차 정렬하여 3명씩 페이징 처리해 1번째 페이지 조회")
    void pagingByNameTest() {

        //when
        List<Idol> pagedIdols = factory
                .selectFrom(idol)
                .orderBy(idol.age.desc()) // 나이로 정렬
                .offset(0)
                .limit(3)
                .fetch();

        //then
        System.out.println("\n\n\n");
        pagedIdols.forEach(System.out::println);
        System.out.println("\n\n\n");


    }

    //"아이브" 그룹의 아이돌을 이름 기준으로 오름차순 정렬하고, 페이지당 2명씩 페이징 처리하여 첫 번째 페이지의 아이돌을 조회하세요.
    @Test
    @DisplayName("아이브 그룹 아이돌을 오름차순으로 정렬 후, 2명씩 페이징 처리해 첫 번쨰 페이지 아이돌 조회")
    void pagingIveByName() {

        // given
        String groupName = "아이브";

        //when
        List<Idol> pagedIve = factory
                .selectFrom(idol)
                .where(idol.group.groupName.eq(groupName))
                .orderBy(idol.idolName.asc()) // 나이로 정렬
                .offset(0) //2명씩이니까 2면 페이지, 다음 페이지 보려면 4
                .limit(2) //1페이지에 2개씩 (0,2)
                .fetch();


        //then
        System.out.println("\n\n\n");
        pagedIve.forEach(System.out::println);
        System.out.println("\n\n\n");


    }






}