package cn.wzhihao.myspace.utils;

import cn.wzhihao.myspace.common.Const;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

public class EmailUtil {
    private static SimpleMailMessage simpleMailMessage = new SimpleMailMessage();


    public static SimpleMailMessage sendActiveEmail(String url, String id, String activeCode, String sendTo, String sendFrom) {
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
                "您只需点击下面的链接即可激活您的帐号：\n"+
                url + "/api/user/active?id=" + id + "&activeCode=" + activeCode + "\n" +
                "(如果上面不是链接形式，请将该地址手工粘贴到浏览器地址栏再访问)\n" +
                "\n" +
                "感谢您的访问，祝您使用愉快！\n" +
                "\n" +
                "此致\n" +
                "MySpace网站开发者.\n" +
                "http://www.wzhihao.cn");
        simpleMailMessage.setTo(sendTo);
        simpleMailMessage.setFrom(sendFrom);
        return simpleMailMessage;
    }

    public static SimpleMailMessage sendVerifyEmail(String url, String verifyCode, String sendTo, String sendFrom) {
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
                "http://www.wzhihao.cn");
        simpleMailMessage.setTo(sendTo);
        simpleMailMessage.setFrom(sendFrom);
        return simpleMailMessage;
    }
}
