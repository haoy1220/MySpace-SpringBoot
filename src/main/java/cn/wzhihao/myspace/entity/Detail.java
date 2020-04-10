package cn.wzhihao.myspace.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@ToString
@Table(name = "myspace_detail")
@Entity
public class Detail {
    @Id
    private Integer id;
    private Integer parentId;
    private String userEmail;
    private String detail;
}
