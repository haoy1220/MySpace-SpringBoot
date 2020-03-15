package cn.wzhihao.myspace.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "myspace_project")
@Data
public class Project {
    @Id
    private Integer id;
    private String userId;
    private Integer parentId;
    private String projectName;
    private Integer projectState;
    private Date createTime;
    private Date updateTime;


}
