package cn.wzhihao.myspace.job;

import cn.wzhihao.myspace.common.Const;
import cn.wzhihao.myspace.dao.MemoMapper;
import cn.wzhihao.myspace.entity.Memo;
import cn.wzhihao.myspace.utils.EmailUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.util.Map;

/***
 * @author     ：hao
 * @date       ：Created in 2020/3/28 21:33
 * @description：提醒任务
 **/
public class RemindJob implements Job {

    private Logger logger = LoggerFactory.getLogger(RemindJob.class);

    @Autowired
    private MemoMapper memoMapper;

    @Autowired
    private JavaMailSenderImpl javaMailSender;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        Map<String,Object> wrappedMap = jobExecutionContext.getJobDetail().getJobDataMap().getWrappedMap();
        Memo memo = (Memo) wrappedMap.get("memo");

        logger.info(memo.toString());
        SimpleMailMessage simpleMailMessage = EmailUtil.sendRemindEmail(memo.getMemoContent(),memo.getMemoEmail(), Const.ADMIN_EMAIL);
        javaMailSender.send(simpleMailMessage);
        memo.setMemoState(Const.Send.YES);
        memoMapper.updateByPrimaryKeySelective(memo);
    }
}
