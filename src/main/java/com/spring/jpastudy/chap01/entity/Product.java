package com.spring.jpastudy.chap01.entity;

import jdk.jfr.Category;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@ToString(exclude = "nickName") //닉네임은 제외해줘. 여러개 안보려면 {"nickName", "price}
@EqualsAndHashCode (of = "id") //id(필드명)만 가지고 판단해라
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name="tbl_product") //테이블 이름 써준다
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prod_id") //컬럼명도 바꿔줄 수 있다
    private Long id; //PK

    @Setter
    @Column(name = "prod_nm", length = 30, nullable = false) //not null
    private String name; //상품명

    @Column(name = "price")
    private int price; //상품가격

    @Setter
    @Column(nullable = false)
    @Enumerated(EnumType.STRING) //  => 이늄의 기본값이 ordinarl(순서가 있는)이다.
    private Category category; //상품 카테고리

    @CreationTimestamp // INSERT문 실행시 자동으로 서버시간 저장
    @Column(updatable = false) //수정 불가하다!
    private LocalDateTime createdAt; //상품 등록시간
    //캐멀케이스로 쓰면 자동으로 스네이크로 변환해준다!

    @UpdateTimestamp // UPDATE문 실행시 자동으로 시간 저장
    private LocalDateTime updatedAt; //상품 수정시간

    //데이터베이스에는 저장 안하고 클래스 내부에서만 사용할 필드
    @Transient
    private String nickName;

    public enum Category{
        FOOD, FASHION, ELECTRONIC
    }


    // 컬럼 기본값 설정
    @PrePersist
    public void prePersist() {
        if (this.price == 0) {
            this.price = 10000;
        }
        if (this.category == null) {
            this.category = Category.FOOD;
        }
    }


}
