package cn.wzhihao.myspace.controller;


import cn.wzhihao.myspace.annotation.VerifyToken;
import cn.wzhihao.myspace.common.Result;
import cn.wzhihao.myspace.entity.Project;
import cn.wzhihao.myspace.service.IProjectService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/project")
public class ProjectController {

    @Autowired
    private IProjectService iProjectService;

    //根据项目id获取卡片内容
    @VerifyToken
    @GetMapping("/{id}")
    public Result<String> getDetail(@PathVariable(value = "id") int id) {
        return iProjectService.getDetail(id);
    }

    //    根据状态获取项目
    @VerifyToken
    @GetMapping("/{projectState}/{pageNum}/{pageSize}")
    public Result<PageInfo<Project>> getProjectList(@PathVariable(value = "projectState") int projectState,
                                                    @PathVariable(value = "pageNum") int pageNum,
                                                    @PathVariable(value = "pageSize") int pageSize) {
        return iProjectService.getProjectList(projectState, pageNum, pageSize);
    }

    //根据id删除项目
    @VerifyToken
    @DeleteMapping("/{id}")
    public Result<String> deleteProject(@PathVariable(value = "id") int id) {
        return iProjectService.deleteProject(id);
    }

    //根据id结束项目
    @VerifyToken
    @PutMapping("/finish/{id}")
    public Result<String> finishProject(@PathVariable(value = "id") int id) {
        return iProjectService.finishProject(id);
    }

    //新增项目
    @VerifyToken
    @PostMapping()
    public Result<String> newProject(String projectBody, String projectDesc) {
        return iProjectService.newProject(projectBody, projectDesc);
    }

    //根据项目id更新项目
//    @VerifyToken
//    @PutMapping("/{id}")
//    public Result<String> updateProject(@PathVariable(value = "id") int id, String cardVoList) {
//        logger.info(cardVoList);
//        ArrayList<CardVo> cardVo = null;
//        return iProjectService.updateProject(id, cardVo);
//    }
    //根据项目id更新项目详情
    @VerifyToken
    @PutMapping("/detail/{id}")
    public Result<String> updateDetail(@PathVariable(value = "id") int id, String detail) {
        return iProjectService.updateDetail(id, detail);
    }

    //根据项目id更新项目
    @VerifyToken
    @PutMapping("/{id}")
    public Result<String> updateProject(@PathVariable(value = "id") int id, String projectBody, String projectDesc) {
        return iProjectService.updateProject(id, projectBody, projectDesc);
    }
}
