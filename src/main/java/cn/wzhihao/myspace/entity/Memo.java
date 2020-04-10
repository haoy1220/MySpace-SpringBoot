package cn.wzhihao.myspace.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Data
@Table(name = "myspace_memo")
@ToString
@Entity
public class Memo {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)//在数据库交互的时候自动获取自增主键
    private Integer id;

    private String userEmail;
    private Integer memoType;
    private String memoEmail;
    private Integer preTime;
    private Long preDate;
    private Long memoDate;
    private String memoContent;
    private Long createTime;
    private Integer memoState;

}
