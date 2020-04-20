package cn.wzhihao.myspace.common;


public class Const {

    public static final String URL = "http://47.107.63.176";//网站url
    public static final String ADMIN_EMAIL = "zhihao.test@qq.com";//提醒邮箱的发件人
//    public static final String SAVE_IMG_PATH = "D:\\img";//存储图片的地址
//    public static final String IMG_URL = "image.wzhihao.cn";//图片对外的url地址
    public static final int MAX_BODY_CHAR_COUNT = 80;//日志概览的最大字符数
    public static final long ACTIVE_EXP_TIME = 1000 * 60 * 60 * 24;//激活码过期时间
    public static final long VERIFY_EXP_TIME = 1000 * 60 * 5;//验证码过期时间


    //七牛云配置
    public interface QiNiu {
        String accessKey = "3DDKpgY99dNDQ3QeblHsquW57k3j1_Lxwedg9A6o";
        String secretKey = "w52X3NqgdhHreq8x-mjcX9ege05jUcyXiu9HFpOP";
        String bucket = "myspace--project";
        String domain = "http://qiniu.wzhihao.cn/";

    }

    //角色权限
    public interface Role {
        int ROLE_CUSTOMER = 0;
        int ROLE_ADMIN = 1;
    }

    public interface Sex {
        int BOY = 1;
        int GIRL = 0;
    }

    //激活状态
    public interface Active {
        int NO = 0;
        int YES = 1;
    }

    //提醒发送状态
    public interface Send {
        int NO = 0;
        int YES = 1;
    }

    //响应码状态
    public interface StatusCode {
        int OK = 200;//操作成功

        int ERROR = 201;//操作失败

        int NEED_LOGIN = 403;//需要登录

//        int PWD_ERROR = 401;//密码错误
//        int REGISTER_ERROR = 402;//注册错误
//        int ACTIVE_EXP = 403;//激活码失效过期
//        int VERIFY_ERROR = 409;//验证码错误
//        int ID_NOT_EXISTS = 405;//id查找失败
//        int SQL_ERROR = 406;//数据库操作失败
//        int PARAM_ERROR = 407;//参数错误
//        int NOT_PERMISSION_ERROR = 408;//没有权限
//
//        int EMAIL_EXISTS = 301;//邮箱已存在
//        int EMAIL_NOT_EXISTS = 303;//邮箱不存在(或激活码无效)
    }

    //项目状态
    public interface Project {
        int UNFINISHED = 0;
        int FINISHED = 1;
    }

    //jwt令牌配置
    public interface JwtConfig {
        String SECRET = "DA56F8f468a4f6F4FA6";//密钥，暂时不用，使用用户密码加密
        long EXP_TIME = 86400;//86400过期时间24小时（24*60*60）
    }

}
