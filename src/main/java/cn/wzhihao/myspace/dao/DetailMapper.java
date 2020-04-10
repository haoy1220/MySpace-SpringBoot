package cn.wzhihao.myspace.dao;

import cn.wzhihao.myspace.entity.Detail;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

public interface DetailMapper extends Mapper<Detail> {
    @Delete("delete from myspace_detail where parent_id=#{id} and user_email=#{email}")
    void deleteByParentId(int id, String email);

    @Select("select detail from myspace_detail where parent_id=#{id} and user_email=#{email}")
    String selectByParentId(int id, String email);
}
