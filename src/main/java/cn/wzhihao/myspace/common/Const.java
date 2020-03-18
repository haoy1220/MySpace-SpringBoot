package cn.wzhihao.myspace.common;


public class Const {

    public static final String CURRENT_USER = "currentUser";
    public static final String URL = "http://localhost";
    public static final String ADMIN_EMAIL = "zhihao.test@qq.com";
    public static final String SAVE_IMG_PATH = "D:\\img";
    public static final String IMG_URL = "image.wzhihao.cn";


    public interface Role {
        int ROLE_CUSTOMER = 0;
        int ROLE_ADMIN = 1;
    }

    public interface Active {
        int NO = 0;
        int YES = 1;
    }

    public interface StatusCode {
        int OK = 0;//成功

        int PWD_ERROR = 40001;//密码错误
        int REGISTER_ERROR = 40002;//注册错误
        int ACTIVE_EXP = 40003;//激活码失效过期
        int VERIFY_ERROR = 40004;//验证码错误
        int ID_NOT_EXISTS = 40005;//id查找失败
        int SQL_ERROR = 40006;//数据库操作失败
        int PARAM_ERROR = 40007;//参数错误

        int EMAIL_EXISTS = 30001;//邮箱已存在
        int EMAIL_NEED_ACTIVE = 30002;//邮箱需要激活
        int EMAIL_NOT_EXISTS = 30003;//邮箱不存在(或激活码无效)


        int NEED_LOGIN = 20001;//需要登录
        int REMOTEERROR = 20004;//远程调用失败
        int REPERROR = 20005;//重复操作
    }

    public interface Project {
        int UNFINISHED = 0;
        int FINISHED = 1;
    }

    public interface JwtConfig {
        String SECRET = "DA56F8f468a4f6F4FA6";
        long EXP_TIME = 86400;
    }

}
