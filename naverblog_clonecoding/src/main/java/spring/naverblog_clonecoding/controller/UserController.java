package spring.naverblog_clonecoding.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.naverblog_clonecoding.domain.User;
import spring.naverblog_clonecoding.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    @RequestMapping("/add")
    public String add() {
        return "user/add";
    }

    @RequestMapping("/add-save")
    public String addSave(
            @RequestParam(value = "id") String id
            , @RequestParam(value = "password") String password
            , @RequestParam(value = "name") String name) {
        User user = User.build(id, password, name);
        userService.add(user);

        return "user/add-save";
    }

    @GetMapping("/mypage")
    public String myPage() {
        return "user/mypage";
    }
}
