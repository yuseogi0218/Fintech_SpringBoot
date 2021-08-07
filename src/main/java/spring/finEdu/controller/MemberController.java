package spring.finEdu.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import spring.finEdu.entity.Member;
import spring.finEdu.service.MemberService;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j  // logger 사용 가능하게 해준다.
public class MemberController {

    final MemberService memberService;

    @GetMapping("/info")
    public Map<String, Object> getUserInfo(@AuthenticationPrincipal OAuth2User principal) {
        return Collections.singletonMap("name", principal.getAttribute("name"));
    }

    @PostMapping("/save")
    public void saveMember(@RequestBody Member member) {
        memberService.saveMember(member);
    }

    @GetMapping("/{key}")
    public Member getMember(@PathVariable("key") Long key, @RequestParam(required = false) String name) {
        if (name != null) {
            return memberService.findById(key, name); //findById() → spring data JPA 를 통하여 만들어진 함수
        } else {
            return memberService.findById(key); //findById() → spring data JPA 를 통하여 만들어진 함수
        }

    }

    @GetMapping("/api/count")
    public List<Object> countByOrgGroup(@RequestParam Boolean isActive) {
        return memberService.countByOrgGroup(isActive);
    }
}
