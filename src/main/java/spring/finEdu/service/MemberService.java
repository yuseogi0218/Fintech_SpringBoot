package spring.finEdu.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import spring.finEdu.entity.Member;
import spring.finEdu.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor // final 생성자 생성해주고 자동 연결 해 준다.
@Slf4j // logger 사용가능하게 해준다.
public class MemberService {

    final MemberRepository memberRepository;

    public String greet(String name) {
        return String.format("Welcome, %s", name);
    }

    public Member findById(Long seq, String name) {
        return memberRepository.findBySeqAndName(seq, name).orElse(null);
    }

    public Member findById(Long seq) {
        return memberRepository.findById(seq).orElse(null);
    }

    public Member saveMember(Member member) {
        return memberRepository.save(member);
    }


    public List<Object> countByOrgGroup(Boolean isActive) {
        return memberRepository.countOrgGroup(isActive);
    }
}
