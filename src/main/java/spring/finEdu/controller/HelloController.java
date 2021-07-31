package spring.finEdu.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import spring.finEdu.entity.Hello;
import spring.finEdu.service.MemberService;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor // 필드에서 필요한 것들을 골라서 생성자 생성한다. - final
// 생성자에 들어가는 field 가 하나 일 경우에 자동으로 @Autowired 된다.
public class HelloController {

    private final MemberService memberService; // 수정 불가 - 변화를 맊을 수 있다. - immutable

    //Bean 주입, DI
    //SetterInjection
//    @Autowired
//    private void setMemberService(MemberService memberService) {
//        this.memberService = memberService;
//    }

//    //Constructor Injection
//    @Autowired
//    public HelloController(MemberService memberService) {
//        this.memberService = memberService;
//    }

    @GetMapping("hello-string")
    @ResponseBody
    public String helloString(String name) {
        return memberService.greet(name);
        //return String.format("Hello, %s", name);
    }

    @GetMapping("hello-mvc")
    public String helloTemplate(String name, Model model) {
        model.addAttribute("name", name);
        return "hello-template";
    }

    // 빈이 생성되고 나서 찍힌다.
    @PostConstruct
    public void postConstruct() {
        log.info("I am already CONSTRUCTED");
    }

    //빈이 죽고 나서 찍힌다.
    @PreDestroy
    public void preDestroy() {
        log.info("test2");
        log.info("I will be destroyed");
    }

    @GetMapping("hello-map")
    @ResponseBody
    public Map<String, Object> helloMap(String name) {
        Map<String, Object> m = new HashMap<>();
        m.put("name", name);
        return m;
    }

    @GetMapping("hello-object")
    @ResponseBody
    public Hello helloObject(String name) {
        Hello h = new Hello(name);
        return h;
    }
}
