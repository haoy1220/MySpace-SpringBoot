package cn.wzhihao.myspace.service.Impl;

import cn.wzhihao.myspace.common.Const;
import cn.wzhihao.myspace.common.Result;
import cn.wzhihao.myspace.dao.ProjectMapper;
import cn.wzhihao.myspace.entity.Project;
import cn.wzhihao.myspace.service.IProjectService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("iProjectService")
public class ProjectServiceImpl implements IProjectService {

    @Autowired
    ProjectMapper projectMapper;

    @Override
    public Result addProject(String projectName, Integer parentId, String userId) {
        if (parentId == null || StringUtils.isBlank(projectName)) {
            return Result.Error(Const.StatusCode.PARAM_ERROR, "参数错误");
        }
        Project project = new Project();
        project.setProjectName(projectName);
        project.setProjectState(Const.Project.UNFINISHED);
        project.setParentId(parentId);
        project.setUserId(userId);

        int res = projectMapper.insert(project);
        if (res > 0) {
            return Result.Success("添加项目成功");
        }
        return Result.Error(Const.StatusCode.SQL_ERROR, "添加项目失败");
    }
}
