package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded // @Embeddable이나 @Embedded 둘 중 하나만 써도 되지만, 임베디드 타입이라는 걸 명시하기 위해 양쪽에 사용
    private Address address;

    @JsonIgnore // 양방향 걸린데의 한쪽은 다 걸어줘야 무한 루프에 빠지지 않는다
    @OneToMany(mappedBy = "member") // Order의 member에 의해 맵핑된 거울일 뿐!
    // 컬렉션은 필드에서 바로 초기화 하는 것이 안전하다, null 신경 안 써도 되고
    private List<Order> orders = new ArrayList<>();

}
