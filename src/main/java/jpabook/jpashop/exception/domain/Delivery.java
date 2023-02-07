package jpabook.jpashop.exception.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
@Setter
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    // fetch를 static import로 바꾼 형태
    // ALT + ENTER, 옵션에서 선택
    @OneToOne(mappedBy = "delivery", fetch = LAZY)
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING) // ORDINAL이 기본값, ORDINAL은 숫자로 세팅되기 때문에 절대 사용 하지 말 것!
    private DeliveryStatus status; // 배송상태 [READY, COMP]
}
