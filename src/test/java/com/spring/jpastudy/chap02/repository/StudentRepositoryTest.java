package com.spring.jpastudy.chap02.repository;

import com.spring.jpastudy.chap02.entity.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional //🌟🌟🌟  주석처리 하지 않기
@Rollback(false) // 주석처리 하지 않고 @Rollback(false)라고 하기
class StudentRepositoryTest {

    @Autowired
    StudentRepository studentRepository;

    @BeforeEach //테스트 전 3명 데이터 넣고 시작한다
    void insertData() {
        Student s1 = Student.builder()
                .name("쿠로미")
                .city("청양군")
                .major("경제학")
                .build();

        Student s2 = Student.builder()
                .name("춘식이")
                .city("서울시")
                .major("컴퓨터공학")
                .build();

        Student s3 = Student.builder()
                .name("어피치")
                .city("제주도")
                .major("화학공학")
                .build();

        studentRepository.save(s1);
        studentRepository.save(s2);
        studentRepository.save(s3);
    }

    @Test
    @DisplayName("")
    void dummyTest() {
        //given

        //when

        //then
    }

    @Test
    @DisplayName("이름이 춘식이인 학생의 모든 정보를 조회한다")
    void findByNameTest() {
        //given
        String name = "춘식이";

        //when
        List<Student> students = studentRepository.findByName(name);

        //then
        assertEquals(1, students.size());

        System.out.println("\n\n\n\n");
        System.out.println("students.get(0) = " + students.get(0));
        System.out.println("\n\n\n\n");

    }

    @Test
    @DisplayName("도시이름과 전공으로 학생 조회")
    void findByCityAndMajorTest() {
        //given
            String city = "제주도";
            String major = "화학공학";

        //when
        List<Student> students = studentRepository.findByCityAndMajor(city, major);

        //then
        System.out.println("\n\n\n\n");
        System.out.println("students" + students);
        System.out.println("\n\n\n\n");
    }

    @Test
    @DisplayName("전공이 공학으로 끝나는 학생들 조회")
    void findByMajorContainingTest() {
        //given
        String majorContaining = "공학";
        //when
        List<Student> students = studentRepository.findByMajorContaining(majorContaining);
        //then
        System.out.println("\n\n\n\n");
        students.forEach(System.out::println);
        System.out.println("\n\n\n\n");
    }


    @Test
    @DisplayName("도시 또는 이름으로 학생 조회")
    void nativeSQLTest() {
        //given
        String name = "춘식이";
        String city = "제주도";
        //when
        List<Student> students = studentRepository.getStudentByNameOrCity(name, city);
        //then
        System.out.println("\n\n\n\n");
        students.forEach(System.out::println);
        System.out.println("\n\n\n\n");
    }

    @Test
    @DisplayName("도시 또는 이름으로 학생 조회")
    void nativeSQLTest2() {
        //given
        String name = "춘식이";
        String city = "제주도";
        //when
        List<Student> students = studentRepository.getStudentByNameOrCity(name, city);
        //then
        System.out.println("\n\n\n\n");
        students.forEach(System.out::println);
        System.out.println("\n\n\n\n");
    }



    @Test
    @DisplayName("JPQL로 학생 조회하기")
    void jpqlTest() {
        //given
        String city = "제주도";
        //when
        Student student = studentRepository.getByCityWithJPQL(city)
                .orElseThrow(() -> new RuntimeException("해당 학생이 없습니다."));
        //then
        assertNotNull(student);
        System.out.println("\n\n\nstudent = " + student + "\n\n\n");
        //assertThrows(RuntimeException.class, () -> new RuntimeException()); // 이런 에러가 날 수 있다는 테스트
    }


    @Test
    @DisplayName("JPQL로 이름이 포함된 학생목록 조회하기")
    void jpqlTest2() {
        //given
        String containingName = "춘";
        //when
        List<Student> students = studentRepository.searchByNameWithJPQL(containingName);
        //then
        System.out.println("\n\n\n");
        students.forEach(System.out::println);
        System.out.println("\n\n\n");
    }


    @Test
    @DisplayName("JPQL로 삭제하기")
    void deleteJPQLTest() { //롤백=false 함
        //given
        String name = "어피치";
        String city = "제주도";
        //when
        studentRepository.deleteByNameAndCityWithJPQL(name, city);
        //then
        assertEquals(0, studentRepository.findByName(name).size());
    }

}