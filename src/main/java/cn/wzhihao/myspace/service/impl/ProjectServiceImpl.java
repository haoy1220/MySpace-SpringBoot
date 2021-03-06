package cn.wzhihao.myspace.service.Impl;

import cn.wzhihao.myspace.common.Const;
import cn.wzhihao.myspace.common.Result;
import cn.wzhihao.myspace.dao.DetailMapper;
import cn.wzhihao.myspace.dao.ProjectMapper;
import cn.wzhihao.myspace.entity.Detail;
import cn.wzhihao.myspace.entity.Project;
import cn.wzhihao.myspace.service.IProjectService;
import cn.wzhihao.myspace.utils.JwtTokenUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Calendar;

@Service("iProjectService")
public class ProjectServiceImpl implements IProjectService {

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private DetailMapper detailMapper;

    @Autowired
    private HttpServletRequest request;

    //获取项目内的卡片布局
    @Override
    public Result<String> getDetail(int id) {
        String detail = detailMapper.selectByParentId(id, JwtTokenUtil.getEmailFromRequest(request));
        return Result.SuccessByData("获取项目详情成功", detail);
    }

    //删除项目
    @Override
    public Result<String> deleteProject(int id) {
        projectMapper.deleteByIdAndEmail(id, JwtTokenUtil.getEmailFromRequest(request));
        //删除项目细节
        detailMapper.deleteByParentId(id, JwtTokenUtil.getEmailFromRequest(request));
        return Result.Success("项目删除成功");
    }

    //结束项目
    @Override
    public Result<String> finishProject(int id) {
        //按主键结束
        Project project = new Project();
        project.setId(id);
        project.setUserEmail(JwtTokenUtil.getEmailFromRequest(request));
        project=projectMapper.selectOne(project);
        if (project==null){
            return Result.Error(Const.StatusCode.ERROR,"数据不存在或权限不足");
        }else {
            project.setProjectState(Const.Project.FINISHED);
            project.setUpdateTime(Calendar.getInstance().getTimeInMillis());
            projectMapper.updateByPrimaryKeySelective(project);
            return Result.Success("项目结束成功");
        }

    }

    //新建项目
    @Override
    public Result<String> newProject(String projectBody, String projectDesc) {
        Project project = new Project();
        project.setUserEmail(JwtTokenUtil.getEmailFromRequest(request));
        project.setProjectState(Const.Project.UNFINISHED);
        project.setProjectBody(projectBody);
        project.setProjectDesc(projectDesc);
        project.setCreateTime(Calendar.getInstance().getTimeInMillis());
        project.setUpdateTime(Calendar.getInstance().getTimeInMillis());
        int res = projectMapper.insertSelective(project);
        if (res > 0) {
            return Result.Success("新建项目成功");
        } else {
            return Result.Error(Const.StatusCode.ERROR, "插入数据库失败");
        }
    }

    @Override
    public Result<String> updateDetail(int id, String detail) {
        //更新详情
        Detail curDetail = new Detail();
        curDetail.setParentId(id);
        curDetail.setUserEmail(JwtTokenUtil.getEmailFromRequest(request));
        Detail oldDetail = detailMapper.selectOne(curDetail);
        if (oldDetail == null) {
            curDetail.setDetail(detail);
            detailMapper.insertSelective(curDetail);
        } else {
            curDetail.setId(oldDetail.getId());
            curDetail.setDetail(detail);
            detailMapper.updateByPrimaryKey(curDetail);
        }

        //更新项目日期
        Project project = new Project();
        project.setId(id);
        project.setUpdateTime(Calendar.getInstance().getTimeInMillis());
        projectMapper.updateByPrimaryKeySelective(project);
        return Result.Success("保存成功");
    }

    @Override
    public Result<String> updateProject(int id, String projectBody, String projectDesc) {
        Project project = projectMapper.selectByPrimaryKey(id);
        project.setProjectBody(projectBody);
        project.setProjectDesc(projectDesc);
        project.setUpdateTime(Calendar.getInstance().getTimeInMillis());
        projectMapper.updateByPrimaryKeySelective(project);
        return Result.Success("修改成功");
    }


    //按完成度获取项目列表
    @Override
    public Result<PageInfo<Project>> getProjectList(int projectState, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        ArrayList<Project> projectList = projectMapper.selectByStateAndEmail(projectState, JwtTokenUtil.getEmailFromRequest(request));
        PageInfo<Project> pageInfo = new PageInfo<>(projectList);
        return Result.SuccessByData("成功获取项目列表", pageInfo);
    }


}
