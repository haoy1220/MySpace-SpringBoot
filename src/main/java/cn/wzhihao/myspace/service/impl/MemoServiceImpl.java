package cn.wzhihao.myspace.service.Impl;

import cn.wzhihao.myspace.common.Const;
import cn.wzhihao.myspace.common.Result;
import cn.wzhihao.myspace.dao.MemoMapper;
import cn.wzhihao.myspace.entity.Memo;
import cn.wzhihao.myspace.job.RemindJob;
import cn.wzhihao.myspace.service.IMemoService;
import cn.wzhihao.myspace.utils.DateUtil;
import cn.wzhihao.myspace.utils.JwtTokenUtil;
import cn.wzhihao.myspace.utils.ScheduledUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.List;

@Service("iMemoService")
public class MemoServiceImpl implements IMemoService {

    @Autowired
    private MemoMapper memoMapper;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private Scheduler scheduler;

    //    根据类型获取分页
    @Override
    public Result<PageInfo<Memo>> getMemoListByType(int type, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Memo> memoList = memoMapper.selectByTypeAndEmail(type, JwtTokenUtil.getEmailFromRequest(request));
        PageInfo<Memo> pageInfo = new PageInfo<>(memoList);
        return Result.SuccessByData("获取成功", pageInfo);
    }

    //    根据类型添加
    @Override
    public Result<String> addMemo(Memo memo) throws SchedulerException {
        memo.setUserEmail(JwtTokenUtil.getEmailFromRequest(request));
        memo.setMemoState(Const.Send.NO);
        memo.setCreateTime(Calendar.getInstance().getTimeInMillis());
        //保存提前提醒时间
        if (memo.getPreTime() != 0) {
            memo.setPreDate(DateUtil.computePreDate(memo.getMemoDate(), memo.getPreTime()));
        }
        int res = memoMapper.insert(memo);

        //如果是remind类型，加入调度任务中
        if (memo.getMemoType() == 0) {
            ScheduledUtil.addJob(scheduler, RemindJob.class, memo);
        }
        if (res > 0) {
            return Result.Success("保存成功");
        } else {
            return Result.Error(Const.StatusCode.ERROR, "权限不足或数据库出错了");
        }
    }

    //根据id删除
    @Override
    public Result<String> deleteMemo(int id) throws SchedulerException {
        Memo memo = memoMapper.selectByPrimaryKey(id);
        if (memo.getUserEmail().equals(JwtTokenUtil.getEmailFromRequest(request))) {
            int res = memoMapper.deleteByPrimaryKey(id);
            if (memo.getMemoType() == 0) {
                ScheduledUtil.deleteJob(scheduler, memo.getId() + "", memo.getId() + "");
            }
            if (res == 1) {
                return Result.Success("删除成功");
            } else {
                return Result.Error(Const.StatusCode.ERROR, "数据出错");
            }
        } else {
            return Result.Error(Const.StatusCode.ERROR, "没有权限");
        }
    }
}
