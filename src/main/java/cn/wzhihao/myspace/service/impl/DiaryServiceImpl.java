package cn.wzhihao.myspace.service.Impl;

import cn.wzhihao.myspace.common.Const;
import cn.wzhihao.myspace.common.Result;
import cn.wzhihao.myspace.service.IDiaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Transactional
@Service("iDiaryService")
public class DiaryServiceImpl implements IDiaryService {

    private static Logger logger = LoggerFactory.getLogger(DiaryServiceImpl.class);

    @Override
    public synchronized Result<String> uploadImg(MultipartFile file) throws IOException {

        //原始文件名
        String fileName = file.getOriginalFilename();

        //获取扩展名
        assert fileName != null;
        String fileExtName = fileName.substring(fileName.lastIndexOf(".") + 1);

        //保存的名字
        String fileSaveName = UUID.randomUUID().toString() + "." + fileExtName;

        logger.info("开始上传文件，上传的文件名：{}，保存的文件名：{}", fileName, fileSaveName);
        File localFile = new File(Const.SAVE_IMG_PATH + "/" + fileSaveName);
        file.transferTo(localFile);
        return Result.SuccessByData("图片上传成功",Const.IMG_URL + "/" + fileSaveName);
    }
}
