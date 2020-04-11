package cn.wzhihao.myspace.utils;

import cn.wzhihao.myspace.common.Const;
import org.springframework.mail.SimpleMailMessage;

public class EmailUtil {

    private static SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

    public static String tranPre(int pre) {
        if (pre == 3) {
            return "三天后";
        }
        return pre == 5 ? "五天后" : "七天后";
    }

    public static SimpleMailMessage sendActiveEmail(String id, String activeCode, String sendTo, String sendFrom) {
        simpleMailMessage.setSubject("MySpace邮箱激活邮件");
        simpleMailMessage.setText("您收到这封邮件，是由于您的邮箱在【MySpace网站】上进行了新用户注册。如果您并没有访问过【MySpace网站】，请忽略这封邮件。\n" +
                "\n" +
                "\n" +
                "----------------------------------------------------------------------\n" +
                "帐号激活说明\n" +
                "----------------------------------------------------------------------\n" +
                "\n" +
                "如果您是【MySpace网站】的新用户，我们需要对您的地址有效性进行验证以避免垃圾邮件或地址被滥用。\n" +
                "\n" +
                "您只需点击下面的链接即可激活您的帐号：\n" +
                Const.URL + "/active?id=" + id + "&activeCode=" + activeCode + "\n" +
                "(如果上面不是链接形式，请将该地址手工粘贴到浏览器地址栏再访问)\n" +
                "\n" +
                "感谢您的访问，祝您使用愉快！\n" +
                "\n" +
                "此致\n" +
                "MySpace网站开发者.\n" +
                Const.URL);
        simpleMailMessage.setTo(sendTo);
        simpleMailMessage.setFrom(sendFrom);
        return simpleMailMessage;
    }

    public static SimpleMailMessage sendVerifyEmail(String verifyCode, String sendTo, String sendFrom) {
        simpleMailMessage.setSubject("MySpace找回密码邮件");
        simpleMailMessage.setText("我们已收到您找回密码的请求。如果这不是您想要的，请忽略这封邮件。\n" +
                "\n" +
                "\n" +
                "----------------------------------------------------------------------\n" +
                "找回密码说明\n" +
                "----------------------------------------------------------------------\n" +
                "您的验证码为：" + verifyCode + "\n" +
                "您可以复制此验证码并返回至找回密码页面，以验证您的邮箱。\n" +
                "此验证码只能使用一次，在5分钟内有效。验证成功则自动失效。\n" +
                "感谢您的访问，祝您使用愉快！\n" +
                "\n" +
                "此致\n" +
                "MySpace网站开发者.\n" +
                Const.URL);
        simpleMailMessage.setTo(sendTo);
        simpleMailMessage.setFrom(sendFrom);
        return simpleMailMessage;
    }

    //提醒邮件
    public static SimpleMailMessage sendRemindEmail(String memoContent, String sendTo, String sendFrom) {
        simpleMailMessage.setSubject("MySpace提醒邮件");
        simpleMailMessage.setText("\n" +
                "\n" +
                "你还有以下事情需要办哦：\n" +
                "======================================================================\n" +
                ""+memoContent +"\n" +
                "======================================================================\n" +
                "不要忘记了哦！！！\n" +
                "\n" +
                "\n" +
                "感谢您的访问，祝您使用愉快！\n" +
                "\n" +
                "此致\n" +
                "MySpace网站开发者.\n" +
                Const.URL);
        simpleMailMessage.setTo(sendTo);
        simpleMailMessage.setFrom(sendFrom);
        return simpleMailMessage;
    }

    //生日邮件
    public static SimpleMailMessage sendBirthdayEmail(String date, String memoContent, String sendTo, String sendFrom) {
        simpleMailMessage.setSubject("MySpace生日提醒邮件");
        simpleMailMessage.setText("\n" +
                "\n" +
                "\n" +
                "======================================================================\n" +
                "【" + date + "】是【" + memoContent + "】的生日，不要忘记给他/她个惊喜哦！！！\n" +
                "======================================================================\n" +
                "\n" +
                "\n" +
                "\n" +
                "感谢您的访问，祝您使用愉快！\n" +
                "\n" +
                "此致\n" +
                "MySpace网站开发者.\n" +
                Const.URL);
        simpleMailMessage.setTo(sendTo);
        simpleMailMessage.setFrom(sendFrom);
        return simpleMailMessage;
    }

    //纪念日邮件
    public static SimpleMailMessage sendAnniversaryEmail(String date, String memoContent, String sendTo, String sendFrom) {
        simpleMailMessage.setSubject("MySpace纪念日提醒邮件");
        simpleMailMessage.setText("【" + date + "】是特殊的纪念日哦，不要忘记了！！！\n" +
                "\n" +
                "纪念内容：\n" +
                "======================================================================\n" +
                "【" + memoContent + "】\n" +
                "======================================================================\n" +
                "\n" +
                "\n" +
                "\n" +
                "感谢您的访问，祝您使用愉快！\n" +
                "\n" +
                "此致\n" +
                "MySpace网站开发者.\n" +
                Const.URL);
        simpleMailMessage.setTo(sendTo);
        simpleMailMessage.setFrom(sendFrom);
        return simpleMailMessage;
    }
}
