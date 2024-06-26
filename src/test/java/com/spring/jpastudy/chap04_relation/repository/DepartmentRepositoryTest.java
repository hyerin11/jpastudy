package com.spring.jpastudy.chap04_relation.repository;

import com.spring.jpastudy.chap04_relation.entity.Department;
import com.spring.jpastudy.chap04_relation.entity.Employee;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class DepartmentRepositoryTest {
    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Test
    @DisplayName("특정 부서를 조회하면 해당 소속부서원들이 함께 조회된다")
    void findDeptTest() {
        //given
        Long id = 1L;
        //when
        Department department = departmentRepository.findById(id).orElseThrow();
        //then
        System.out.println("\n\n\n\n");
        System.out.println("department = " + department);
        System.out.println("\n\n\n\n");

        //모든 사원정보를 알고싶다면
        List<Employee> employees = department.getEmployees();
        System.out.println("\n\n\n\n");
        employees.forEach(System.out::println);
        System.out.println("\n\n\n\n");

    }

    //양방향 연관관계에서 리스트에 데이터 갱신시 주의사항
    @Test
    @DisplayName("양방향 연관관계에서 연관데이터 수정")
    void changeTest() {
        //given
        //3번 사원의 부서를 2번 부서에서 1번부서로 수정한다
        // 1) 3번 사원 정보조회
        Employee employee = employeeRepository.findById(3L).orElseThrow();
        // 2) 1번 부서 정보조회
        Department department = departmentRepository.findById(1L).orElseThrow();

        //when
        // 사원정보를 수정한다
        employee.setDepartment(department);
        //핵심!!!! 양방향에서는 수정 시 반대편에도 같이 수정해줘야 한다🌟🌟🌟
        department.getEmployees().add(employee);
        employeeRepository.save(employee); //다시 save하면 수정됨.


        //then
        //다시 2번으로 돌리고 싶다면, 2)에서 2L로 바꾸고, 바뀐부서의 사원목록을 조회한다.
        List<Employee> employees = department.getEmployees();
        System.out.println("\n\n\n");
        employees.forEach(System.out::println);
        System.out.println("\n\n\n");
        // => 부서는 변경되었지만, 사원정보에 들어가서 보면 수정되지 않음. = 한쪽만 바꿔주면 안됨. 반대편에도 수동으로 바꿔줘야 한다.

        /*
        * 사원정보가 employee엔터티에서 수정되어도
        * 반대편 엔터티인 department에서는 리스트에 바로 반영되지 않는다.
        *
        * 해결방안)
        * 데이터 수정 시 반대편 엔터티에도 같이 수정해줘야 한다.
        * */
    }

}