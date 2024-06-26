package com.spring.jpastudy.chap04_relation.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ToString(exclude = "employees")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_dept")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emp_id")
    private Long id; // 부서번호

    @Column(name = "emp_name", nullable = false)
    private String name;

    /*
        - 양방향 매핑은 데이터베이스와 달리 객체지향 시스템에서 가능한 방법으로
        1대N관계에서 1쪽에 N데이터를 포함시킬 수 있는 방법이다.

        - 양방향 매핑에서 1쪽은 상대방 엔터티 갱신에 관여 할 수 없고
           (리스트에서 사원을 지운다고 실제 디비에서 사원이 삭제되지는 않는다는 말)
           🌟단순히 읽기전용 (조회전용)으로만 사용하는 것이다.
        - mappedBy에는 상대방 엔터티에 @ManyToOne에 대응되는 필드명을 꼭 적어야 함🌟
     */

    //employee에 manytoOne에 department라고 지정함.
    @OneToMany(mappedBy = "department", orphanRemoval = true, cascade = CascadeType.ALL) //= 상대방은 나를 뭐라고 맵핑했니?
                                            //고아객체 removal을 true로 해준다
    //여기서 OneToMany는 LAZY로 되어있음. (조인안함)
    private List<Employee> employees = new ArrayList<>();

    //@OneToMany는 기본 LAZY임.
    //@ManytoOne는 EAGER라 LAZY로 바꿔줘야 하고
    //ToString에서 꼭 빼줘야 함.

    public void removeEmployee(Employee employee) {
        this.employees.remove(employee);
        employee.setDepartment(null);
    }

    public void addEmployee(Employee employee){
        this.employees.add(employee); //추가했으면
        employee.setDepartment(this); //반대쪽도 추가
    }

}
