package cn.wzhihao.myspace.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Data
@Table(name = "myspace_diary")
@ToString
@Entity
public class Diary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String userEmail;
    private String diaryTitle;
    private String diaryBody;
    private Long createTime;
    private Long updateTime;
}
