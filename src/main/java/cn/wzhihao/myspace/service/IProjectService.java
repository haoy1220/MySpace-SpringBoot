package cn.wzhihao.myspace.service;

import cn.wzhihao.myspace.common.Result;

public interface IProjectService {
    Result addProject(String projectName, Integer parentId, String userId);
}
