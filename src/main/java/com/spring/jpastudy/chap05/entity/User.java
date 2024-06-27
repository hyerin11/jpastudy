package com.spring.jpastudy.chap05.entity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter @Getter
@ToString(exclude = "purchaseList")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "tbl_mtm_user")  //상품과 유저는 다대다 관계
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_name")
    private String name;

    //@ManyToMany 하지않기. 중간테이블을 마음대로 만들기 때문에 Purchase라는 중간 테이블을 수동으로 만들어주는게 좋음
    //private List<User> userList;

    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Purchase> purchaseList = new ArrayList<>();
}
