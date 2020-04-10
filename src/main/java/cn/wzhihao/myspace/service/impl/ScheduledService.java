package cn.wzhihao.myspace.service.Impl;

import cn.wzhihao.myspace.common.Const;
import cn.wzhihao.myspace.dao.MemoMapper;
import cn.wzhihao.myspace.entity.Memo;
import cn.wzhihao.myspace.utils.DateUtil;
import cn.wzhihao.myspace.utils.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ScheduledService {

    @Autowired
    private JavaMailSenderImpl javaMailSender;

    @Autowired
    private MemoMapper memoMapper;

    @Scheduled(cron = "0 0 0 * * ?")
    public void remindEmail() {
        Date today = new Date();
        int day = today.getDate() - 1;//获取当天时间,数据库查找的会少一天，暂时不清楚原因
        int month = today.getMonth() + 1;//获取当前月
        int year = Calendar.getInstance().get(Calendar.YEAR);
        System.out.println(year);
        today = null;
        //生日
        //纪念日
        List<Memo> memoList = memoMapper.selectMemo(day, month);
        if (!memoList.isEmpty()) {
            for (Memo memo : memoList) {
                if (memo.getMemoType() == 2) {
                    SimpleMailMessage simpleMailMessage = EmailUtil.sendAnniversaryEmail("今天", memo.getMemoContent(), memo.getMemoEmail(), Const.ADMIN_EMAIL);
                    javaMailSender.send(simpleMailMessage);
                } else if (memo.getMemoType() == 1) {
                    SimpleMailMessage simpleMailMessage = EmailUtil.sendBirthdayEmail("今天", memo.getMemoContent(), memo.getMemoEmail(), Const.ADMIN_EMAIL);
                    javaMailSender.send(simpleMailMessage);
                }
            }
        }

        //提前生日
        //提前纪念日
        List<Memo> preMemoList = memoMapper.selectPreMemo(day, month, year);
        if (!preMemoList.isEmpty()) {
            for (Memo preMemo : preMemoList) {
                if (preMemo.getMemoType() == 2) {
                    SimpleMailMessage simpleMailMessage = EmailUtil.sendAnniversaryEmail(EmailUtil.tranPre(preMemo.getPreTime()), preMemo.getMemoContent(), preMemo.getMemoEmail(), Const.ADMIN_EMAIL);
                    javaMailSender.send(simpleMailMessage);
                } else if (preMemo.getMemoType() == 1) {
                    SimpleMailMessage simpleMailMessage = EmailUtil.sendBirthdayEmail(EmailUtil.tranPre(preMemo.getPreTime()), preMemo.getMemoContent(), preMemo.getMemoEmail(), Const.ADMIN_EMAIL);
                    javaMailSender.send(simpleMailMessage);
                }
                //处理完要重置下次提前时间
                preMemo.setPreDate(DateUtil.computePreDate(preMemo.getMemoDate(), preMemo.getPreTime()));
                memoMapper.updateByPrimaryKeySelective(preMemo);
            }
        }
    }



}
