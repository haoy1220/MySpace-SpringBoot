package cn.wzhihao.myspace.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "myspace_user")
@Data
public class User {

    //因为没有注明主键导致通用mapper更新操作找不到主键
    @Id
    private String id;
    private String nickname;
    private String password;
    private String email;
    private Integer sex;
    private Integer role;
    private Integer activeState;
    private String verifyCode;
    private String activeCode;
    private String expTime;
    private String createTime;
    private String updateTime;
}
