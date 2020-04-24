package cn.wzhihao.myspace.service;

import cn.wzhihao.myspace.common.Result;
import cn.wzhihao.myspace.entity.Project;
import com.github.pagehelper.PageInfo;

public interface IProjectService {

    Result<PageInfo<Project>> getProjectList(int projectState, int pageNum, int pageSize);

    Result<String> getDetail(int id);

    Result<String> deleteProject(int id);

    Result<String> finishProject(int id);

    Result<String> newProject(String projectBody, String projectDesc);

    Result<String> updateDetail(int id, String detail);

    Result<String> updateProject(int id, String projectBody, String projectDesc);
}
