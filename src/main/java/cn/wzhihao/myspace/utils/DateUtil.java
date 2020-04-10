package cn.wzhihao.myspace.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/***
 * @author     ：hao
 * @date       ：Created in 2020/3/27 20:57
 * @description：时间操作工具
 **/
public class DateUtil {

    //计算提前日期
    public static long computePreDate(long time, int preDay) {
        Date temp = new Date(time);
        Date date = new Date(temp.getYear(), temp.getMonth(), temp.getDate());

        Date temp2 = new Date();
        Date today = new Date(temp2.getYear(), temp2.getMonth(), temp2.getDate());

        date.setYear(today.getYear());
        Date preDate = new Date(date.getTime() - preDay * 24 * 3600 * 1000);//今年提前提醒的时间
        if (today.getTime() >= preDate.getTime()) {//今年提醒时间过了，就返回下一年
            date.setYear(today.getYear() + 1);
            return new Date(date.getTime() - preDay * 24 * 3600 * 1000).getTime();
        } else {//还没过就返回今年的
            return preDate.getTime();
        }
    }

    /***
     *
     * @param date
     * @param dateFormat : e.g:yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String formatDateByPattern(Date date, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        String formatTimeStr = null;
        if (date != null) {
            formatTimeStr = sdf.format(date);
        }
        return formatTimeStr;
    }

    /***
     * convert Date to cron ,eg.  "0 07 10 15 1 ? 2016"
     * @param date  : 时间点
     * @return
     */
    public static String getCron(java.util.Date date) {
        String dateFormat = "ss mm HH dd MM ? yyyy";
        return formatDateByPattern(date, dateFormat);
    }
}
