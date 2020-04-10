package cn.wzhihao.myspace.service;

import cn.wzhihao.myspace.common.Result;
import cn.wzhihao.myspace.entity.Memo;
import com.github.pagehelper.PageInfo;
import org.quartz.SchedulerException;

public interface IMemoService {


    Result<PageInfo<Memo>> getMemoListByType(int type, int pageNum, int pageSize);

    Result<String> addMemo(Memo memo) throws SchedulerException;

    Result<String> deleteMemo(int id) throws SchedulerException;
}
