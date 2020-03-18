package cn.wzhihao.myspace.controller;

import cn.wzhihao.myspace.annotation.VerifyToken;
import cn.wzhihao.myspace.common.Const;
import cn.wzhihao.myspace.common.Result;
import cn.wzhihao.myspace.service.IDiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/diary")
@CrossOrigin
public class DiaryController {

    @Autowired
    private IDiaryService iDiaryService;

    @VerifyToken
    @ResponseBody
    @PostMapping("/uploadImg")
    public Result<String> uploadImg(MultipartFile file) throws IOException {
        if (file == null) {
            return Result.Error(Const.StatusCode.PARAM_ERROR, "参数错误");
        }
        return iDiaryService.uploadImg(file);
    }
}
