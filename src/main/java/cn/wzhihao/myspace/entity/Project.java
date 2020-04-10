package cn.wzhihao.myspace.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

@Table(name = "myspace_project")
@Data
public class Project {
    @Id
    private Integer id;
    private String userEmail;
    private String projectBody;
    private String projectDesc;
    private Integer projectState;

    private Long createTime;
    private Long updateTime;


}
