package cn.wzhihao.myspace.utils;

import cn.wzhihao.myspace.common.Const;
import com.google.gson.Gson;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class QiNiuUploadUtil {
    //设置ak和sk
    private static String ACCESS_KEY = Const.QiNiu.accessKey;
    private static String SECRET_KEY = Const.QiNiu.secretKey;

    //配置认证密钥和地区
    private static Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
    private static Configuration configuration = new Configuration(Zone.huanan());


    private static UploadManager uploadManager = new UploadManager(configuration);

    //简单上传，设置上传空间即可
    public static String getUpToken() {
        //上传的空间
        String bucketName = Const.QiNiu.bucket;
        return auth.uploadToken(bucketName);
    }

    public static String uploadImg(MultipartFile file, String fileName) throws IOException {
        InputStream inputStream = file.getInputStream();
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[600];
        int rc = 0;
        while ((rc = inputStream.read(buff, 0, 100)) > 0) {
            swapStream.write(buff, 0, rc);
        }

        byte[] uploadBytes = swapStream.toByteArray();
        Response response = uploadManager.put(uploadBytes, fileName, getUpToken());
        DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
        return Const.QiNiu.domain + putRet.key;
    }
}
