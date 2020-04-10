package cn.wzhihao.myspace.dao;

import cn.wzhihao.myspace.entity.Project;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

import java.util.ArrayList;

public interface ProjectMapper extends Mapper<Project> {


    @Select("select * from myspace_project where project_state=#{projectState} and user_email=#{email} order by create_time desc")
    ArrayList<Project> selectByStateAndEmail(int projectState, String email);

    @Select("select id, parent_id, project_body from myspace_project where parent_id=#{parentId} and user_email=#{email}")
    ArrayList<Project> selectList(int parentId, String email);

    @Delete("delete from myspace_project where id=#{id} and user_email=#{email}")
    int deleteByIdAndEmail(int id, String email);

    @Update("update myspace_project set project_state=1 where id=#{id} and user_email=#{email}")
    int updateByIdAndEmail(int id, String email);
}
