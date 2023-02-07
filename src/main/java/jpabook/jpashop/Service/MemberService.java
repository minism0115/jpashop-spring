package jpabook.jpashop.Service;

import jpabook.jpashop.Repository.MemberRepository;
import jpabook.jpashop.exception.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
// 모든 데이터 변경은 트랜잭션 안에서!, 스프링에서 제공하는 어노테이션 쓰는 걸 권장
@Transactional(readOnly = true) // JPA 성능 최적화, 읽기
//@AllArgsConstructor // 필드의 생성자 자동 생성
@RequiredArgsConstructor // final 필드의 생성자 자동 생성
public class MemberService {

    // 변경할 일이 없기 때문에 final로 해주는 것이 좋다
    private final MemberRepository memberRepository;

    // 생성자 인젝션 선호
//    @Autowired // @Autowired 어노테이션을 생략해도 스프링이 자동으로 주입
    /*public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }*/

    /**
     * 회원 가입
     */
    @Transactional // readOnly = false가 디폴트
    public Long join(Member member) {
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 회원 전체 조회
     */
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    /**
     * 단일 회원 조회
     */
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }
}
