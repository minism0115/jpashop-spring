package jpabook.jpashop.exception.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
// 값 타입은 변경 불가능하도록 설계해야 한다
// Setter 제거, 생성자에서 값 초기화
// JPA 구현 라이브러리가 객체를 생성할 때 리플렉션이나 프록시 같은 기술을 사용할 수 있도록 지원하기 위해
public class Address {

    private String city;
    private String street;
    private String zipcode;

    protected Address() {
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
