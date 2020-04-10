package cn.wzhihao.myspace.utils;

import cn.wzhihao.myspace.entity.Memo;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ScheduledUtil {
    private static Logger logger = LoggerFactory.getLogger(ScheduledUtil.class);

    //job组和trigger组默认的名字
    private static String JOB_GROUP_NAME = "MY_JOB_GROUP";
    private static String TRIGGER_GROUP_NAME = "MY_TRIGGER_GROUP";

    //添加一个定时任务
    public static void addJob(Scheduler scheduler, @SuppressWarnings("rawtypes") Class jobClass, Memo memo) throws SchedulerException {

        Map<String, Object> map = new HashMap<>();

        map.put("memo", memo);

        JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(memo.getId() + "", JOB_GROUP_NAME).usingJobData(new JobDataMap(map)).build();

        //触发器
        CronTrigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity(memo.getId() + "", TRIGGER_GROUP_NAME)
                .withSchedule(
                        CronScheduleBuilder.cronSchedule(DateUtil.getCron(new Date(memo.getMemoDate())))
                )
                .build();

        scheduler.scheduleJob(jobDetail, trigger);
        logger.info(scheduler.toString());
        scheduler.start();

    }

    //移除一个定时任务
    public static void deleteJob(Scheduler scheduler, String jobName, String triggerName) throws SchedulerException {
        logger.info(scheduler.toString());
        scheduler.pauseTrigger(new TriggerKey(triggerName, TRIGGER_GROUP_NAME));
        scheduler.unscheduleJob(new TriggerKey(triggerName, TRIGGER_GROUP_NAME));
        scheduler.deleteJob(new JobKey(jobName, JOB_GROUP_NAME));
        logger.info(scheduler.toString() + "被删除了");
    }
}
