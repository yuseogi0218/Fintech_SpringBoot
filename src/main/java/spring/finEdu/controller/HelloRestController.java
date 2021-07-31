package spring.finEdu.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.finEdu.entity.Hello;

import java.util.HashMap;
import java.util.Map;

@RestController // @Controller, @ResponseBody
public class HelloRestController {

    @GetMapping("hello-string-rest")
    public String helloString(String name) {
        return String.format("Hello, %s", name);
    }

    @GetMapping("hello-map-rest")
    public Map<String, Object> helloMap(String name) {
        Map<String, Object> m = new HashMap<>();
        m.put("name", name);
        return m;
    }

    @GetMapping("hello-object-rest")
    public Hello helloObject(String name) {
        Hello h = new Hello(name);
        return h;
    }
}
