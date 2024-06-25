package com.spring.jpastudy.chap02.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "tbl_student")
public class Student {

    //랜덤문자로 PK지정
    @Id
    @Column(name="stu_id")
    @GeneratedValue(generator = "uid")
    @GenericGenerator(strategy = "uuid", name="uid")
    private String id; //pk 랜덤값으로 받음 + 모든 테이블의 pk가 다 다른 값으로 들어감.

    @Column(name = "stu_name", nullable = false)
    private String name;

    private String city;

    private String major;

}
