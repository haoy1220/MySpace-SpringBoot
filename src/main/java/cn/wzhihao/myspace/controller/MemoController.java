package cn.wzhihao.myspace.controller;

import cn.wzhihao.myspace.annotation.VerifyToken;
import cn.wzhihao.myspace.common.Result;
import cn.wzhihao.myspace.entity.Memo;
import cn.wzhihao.myspace.service.IMemoService;
import com.github.pagehelper.PageInfo;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/memo")
@CrossOrigin
public class MemoController {

    @Autowired
    private IMemoService iMemoService;

    //根据类型获取分页备忘录
    @VerifyToken
    @GetMapping("/{type}/{pageNum}/{pageSize}")
    public Result<PageInfo<Memo>> getMemoListByType(@PathVariable(value = "type") int type,
                                                    @PathVariable(value = "pageNum") int pageNum,
                                                    @PathVariable(value = "pageSize") int pageSize) {
        return iMemoService.getMemoListByType(type, pageNum, pageSize);
    }


    //根据类型添加
    @VerifyToken
    @PostMapping()
    public Result<String> addMemo(Memo memo) throws SchedulerException {
        return iMemoService.addMemo(memo);
    }

    //根据id删除
    @VerifyToken
    @DeleteMapping("/{id}")
    public Result<String> deleteMemo(@PathVariable(value = "id") int id) throws SchedulerException {
        return iMemoService.deleteMemo(id);
    }
}
