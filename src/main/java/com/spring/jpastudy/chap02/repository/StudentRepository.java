package com.spring.jpastudy.chap02.repository;

import com.spring.jpastudy.chap02.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, String> {

    // 쿼리메서드 : 메서드 이름에 특별한 규칙을 적용하면 SQL이 규칙에 맞게 생성된다.
    List<Student> findByName(String name);  //카멜케이스 적용해야 함. findBy + 어쩌구(stu_name쓰면 안됨)
    // Optional findByName(String name); => 단일조회

    // 도시로 찾고싶다면
    List<Student> findByCity(String city);

    //도시와 전공이름이 매칭되게 찾고싶다면 (And)
    List<Student> findByCityAndMajor(String city, String major);

    // where major like '%major%'
    List<Student> findByMajorContaining(String major);
    // where major like 'major%'
    List<Student> findByMajorStartingWith(String major);
    // where major like '%major'
    List<Student> findByMajorEndingWith(String major);


//    // where age <= ?
//    List<Student> findByAgeLessThanEqual(int age);


    // native sql 사용하기 = 순수 sql쓰기
    @Query(value = "SELECT * FROM tbl_student WHERE stu_name = :snm OR city = :city", nativeQuery = true)
    List<Student> getStudentByNameOrCity(@Param("snm") String name, @Param("city") String city); //이름은 아무거나 써도 됨.


    // native sql 사용하기 = 순수 sql쓰기
    @Query(value = "SELECT * FROM tbl_student WHERE stu_name = :?1 OR :city = ?2", nativeQuery = true)
    List<Student> getStudentByNameOrCity2(String name, String city); //이름은 아무거나 써도 됨.


    /*
        - JPQL

        SELECT 엔터티별칭
        FROM 엔터티클래스명 AS 엔터티별칭
        WHERE 별칭.필드명

        ex) native - SELECT * FROM tbl_student WHERE stu_name = ?
            JPQL   - SELECT st FROM Student AS st WHERE st.name = ?

          * native               * JPQL
            FROM + 테이블명   >>>   FROM + 클래스명

     */

    // 도시명으로 학생 1명 단일조회
    @Query(value = "SELECT st FROM Student AS st WHERE st.city = ?1") //as는 생략 가능
    Optional<Student> getByCityWithJPQL(String city);
    // 단일조회는 결과가 null이 나올 수 있기 때문에 Optional로 감싸줌

    //특정 이름이 포함된 학생 리스트 조회하기
    @Query("SELECT stu FROM Student stu WHERE stu.name LIKE %?1%")
    List<Student> searchByNameWithJPQL(String name);

}















