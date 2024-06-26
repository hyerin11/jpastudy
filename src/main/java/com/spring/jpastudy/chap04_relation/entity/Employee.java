package com.spring.jpastudy.chap04_relation.entity;

import lombok.*;

import javax.persistence.*;

@Setter @Getter
@ToString(exclude = "department") //연관관계 필드는 꼭 제외해준다🌟
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_emp")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emp_id")
    private Long id; // 사원번호

    @Column(name = "emp_name", nullable = false)
    private String name;

    // 단방향 매핑 - 데이터베이스처럼 한쪽에 상대방의 PK를 FK로 갖는 형태
    // EAGER Loading: 연관된 데이터를 항상 JOIN을 통해 같이 가져온다.
    // LAZY Loading: 해당 엔터티 데이터만 가져오고 필요한 경우 연관엔터티를 가져옴
    //@ManyToOne(fetch = FetchType.EAGER)
    @ManyToOne(fetch = FetchType.LAZY) // 기본적으로 LAZY씀. manytoone = lazy🌟
    @JoinColumn(name = "dept_id")  // FK 컬럼명
    private Department department;

    public void changeDepartment(Department department) {
        this.department = department;
        department.getEmployees().add(this); //잊지않고 반대쪽 바꿔주기!
    }

//    @ManyToOne
//    @JoinColumn(name = "receive_dept_id")
//    private Department department2;
}

