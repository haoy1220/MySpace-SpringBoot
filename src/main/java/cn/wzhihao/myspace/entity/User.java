package cn.wzhihao.myspace.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "myspace_user")
@Data
@ToString
@Entity
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
    private Long expTime;
    private Long createTime;
    private Long updateTime;
}
