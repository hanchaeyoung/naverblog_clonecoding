package spring.naverblog_clonecoding.domain;

import lombok.Getter;
import lombok.Setter;
import spring.naverblog_clonecoding.entity.UserEntity;

import java.util.Date;

@Getter
@Setter
public class User {

    private Long userIdx;

    private String id;

    private String password;

    private String name;

    private int grade;

    private Date registerTime;

    private boolean withrawed;
    public static User build(String id, String password, String name) {
        User user = new User();

        user.setId(id);
        user.setPassword(password);
        user.setName(name);

        return user;
    }

    public static User build(UserEntity entity) {
        User user = new User();

        user.setUserIdx(entity.getUserIdx());
        user.setId(entity.getId());
        user.setPassword(entity.getPassword());
        user.setName(entity.getName());
        user.setGrade(entity.getGrade());
        user.setRegisterTime(entity.getRegisterTime());
        user.setWithrawed(entity.isWithrawed());

        return user;
    }
}
