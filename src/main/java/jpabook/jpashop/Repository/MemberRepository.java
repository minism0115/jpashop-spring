package jpabook.jpashop.Repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor // MemberService와 동일한 포맷으로 개발 가능
public class MemberRepository {

//    @PersistenceContext // 최신 스프링부트 데이터 JPA가 @Autowired 어노테이션으로 대체 가능하도록 지원
    private final EntityManager em;

    public void save(Member member){
        em.persist(member);
    }

    public Member findOne(Long id){
        return em.find(Member.class, id);
    }

    public List<Member> findAll(){
        // JPQL
        // SQL은 테이블을 대상으로 쿼리, JPQL은 엔티티를 대상으로 쿼리
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
