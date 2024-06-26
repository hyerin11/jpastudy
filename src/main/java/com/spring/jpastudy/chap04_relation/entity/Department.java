package com.spring.jpastudy.chap04_relation.entity;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@ToString
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

     // 단방향 매핑 : 데이터베이스처럼 한쪽에 상대방의 PK를 FK로 갖는 형태
    @ManyToOne //임폴로이를 기준으로 employee가 다 니까.
    @JoinColumn(name = "send_dept_id") //FK컬럼명
    private Department department;

//    @ManyToOne
//    @JoinColumn(name = "receive_dept_id")
//    private Department department2;

}
