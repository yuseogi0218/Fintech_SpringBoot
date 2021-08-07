package spring.finEdu.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import spring.finEdu.entity.Member;
import spring.finEdu.repository.MemberRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest // 스프링 서버 실해하여 테스트 가능
@Transactional // 테스트 케이스에 적용시, 테스트 시작 전에 트랜잭션을 시작하고, 테스트 완료 후에 항상 롤백한다.
public class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void saveMember() throws Exception{

        //Given : Member 빌더로 Member 객체 생성 후
        Member member = new Member();
        member.setName("Winter");
        member.setId("elsa");
        member.setOrg("sm");
        member.setActive(true);

        //When : 멤버가 가입괴면 (MemberService, 스프링 컨텍스트가 사용된걸 확인)
        Member storedMember = memberService.addUser(member);

        //Then : 그 멤버를 다시 찾을때 둘의 이름이 같아야 한다.
        Member foundMember = memberRepository.findById(storedMember.getSeq()).get();
        assertEquals(member.getName(), foundMember.getName());
    }
}