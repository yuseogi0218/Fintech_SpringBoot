package spring.finEdu.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.finEdu.entity.Member;
import spring.finEdu.repository.MemberRepository;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    final MemberRepository memberRepository;

    @GetMapping("/{key}")
    public Member getMember(@PathVariable("key") Long key) {
        return memberRepository.findById(key).orElse(null); //findById() → spring data JPA 를 통하여 만들어진 함수
    }

}
