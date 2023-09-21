package spring.naverblog_clonecoding.entity;

import lombok.Getter;
import lombok.Setter;
import spring.naverblog_clonecoding.domain.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "user_entity")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userIdx;

    @Column(length = 15, nullable = false)
    private String id;

    @Column(length = 250, nullable = false)
    private String password;

    @Column(length = 100, nullable = false)
    private String name;

    private int grade;

    private Date registerTime;

//    private Date lastLoginTime;

    private boolean withrawed;

//    private Date withdrawTime;

    public static UserEntity build(User user) {
        UserEntity entity = new UserEntity();

        entity.setUserIdx(user.getUserIdx());
        entity.setId(user.getId());
        entity.setPassword(user.getPassword());
        entity.setName(user.getName());
        entity.setGrade(user.getGrade());
        entity.setRegisterTime(user.getRegisterTime());
        entity.setWithrawed(user.isWithrawed());

        return entity;
    }

}