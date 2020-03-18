package cn.wzhihao.myspace.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "myspace_diary")

public class Diary {
    @Id
    private int id;
    private String userId;
    private String diaryTitle;
    private String diaryBody;
    private String createTime;
    private String updateTime;
}
