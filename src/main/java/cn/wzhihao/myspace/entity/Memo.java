package cn.wzhihao.myspace.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "myspace_memo")
@ToString
@Entity
public class Memo {
    @Id
    private int id;

    private String userEmail;
    private int memoType;
    private String memoEmail;
    private Long preTime;
    private Long memoDate;
    private String memoContent;
    private Long createTime;

}
