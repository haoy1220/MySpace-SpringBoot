package cn.wzhihao.myspace.controller;


import cn.wzhihao.myspace.common.Const;
import cn.wzhihao.myspace.common.Result;
import cn.wzhihao.myspace.entity.User;
import cn.wzhihao.myspace.service.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@ResponseBody
@CrossOrigin
@RequestMapping("/api/project")
public class ProjectController {

    @Autowired
    private IProjectService iProjectService;

    @PostMapping("/addProject")
    public Result addProject(HttpSession session, String projectName, @RequestParam(name = "parentId", defaultValue = "0") int parentId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return Result.Error(Const.StatusCode.NEED_LOGIN, "请登录后再尝试");
        }
        return iProjectService.addProject(projectName, parentId, user.getId());
    }
}
