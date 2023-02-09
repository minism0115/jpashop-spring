package jpabook.jpashop.Service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.Repository.MemberRepository;
import jpabook.jpashop.domain.Member;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

// 이 두 개의 어노테이션이 있어야 스프링부트 테스트 가능
@RunWith(SpringRunner.class)
@SpringBootTest
// 엔티티 매니저를 통한 모든 엔티티 변경은 트랜잭션 안에서 이뤄져야 한다
// 테스트에 이 어노테이션을 쓰면 테스트 후 롤백, @Rollback(value = false)로 커밋 가능
@Transactional
public class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager em;

    @Test
    public void 회원가입() throws Exception {
        // given
        Member member = new Member();
        member.setName("kwon");

        // when
        Long savedId = memberService.join(member);

        // then
        em.flush(); // 영속성 컨텍스트 내용을 DB에 반영(INSERT 쿼리 실행)
        assertEquals(member, memberRepository.findOne(savedId));

        // JPA 엔티티 동일성 보장
        // 동일한 트랜잭션 안에서 저장과 조회가 일어났으니 영속성 컨텍스트도 동일
        // 동일 영속성 컨텍스트에서 식별자가 같으면 동일 엔티티로 인식
    }

    @Test(expected = IllegalStateException.class) // 코드 간결화
    public void 중복_회원_예외() throws Exception {
        // given
        Member member1 = new Member();
        member1.setName("kwon");

        Member member2 = new Member();
        member2.setName("kwon");

        // when
        memberService.join(member1);
        memberService.join(member2); // 예외가 발생해야 한다!

       /* memberService.join(member1);
        try {
            memberService.join(member2); // 예외가 발생해야 한다!
        } catch (IllegalStateException e){
            return;
        }*/

        // then
        fail("예외가 발생해야 한다"); // 코드가 여기까지 오면 안 돼!

    }

}