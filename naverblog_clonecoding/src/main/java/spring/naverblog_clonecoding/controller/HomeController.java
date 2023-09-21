package spring.naverblog_clonecoding.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.naverblog_clonecoding.domain.MyUserDetails;
import spring.naverblog_clonecoding.domain.User;

@Controller
public class HomeController {

    @RequestMapping("/")
    private String home() {
        return "home";
    }

    @RequestMapping("/sub")
    private String sub() {
        return "sub";
    }


    @RequestMapping("/write")
    public String write(){
        //방명록의 내용을 입력하기 위한 페이지 추가
        return "write";
    }

    @PostMapping("/write-save")
    public String writeSave(@RequestParam("content") String content){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        MyUserDetails userDetails = (MyUserDetails)authentication.getPrincipal();
        User user = userDetails.getUser();

        System.out.println("NAME "+ user.getName());


        return "write-result";
    }
}
