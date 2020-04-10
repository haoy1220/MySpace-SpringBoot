package cn.wzhihao.myspace.controller;

import cn.wzhihao.myspace.annotation.VerifyToken;
import cn.wzhihao.myspace.common.Const;
import cn.wzhihao.myspace.common.Result;
import cn.wzhihao.myspace.entity.Diary;
import cn.wzhihao.myspace.service.IDiaryService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/diary")
@CrossOrigin
public class DiaryController {

    @Autowired
    private IDiaryService iDiaryService;

    //图片上传
    @VerifyToken
    @PostMapping("/uploadImg")
    public Result<String> uploadImg(MultipartFile file) throws IOException {
        if (file == null) {
            return Result.Error(Const.StatusCode.PARAM_ERROR, "参数错误");
        }
        return iDiaryService.uploadImg(file);
    }

    //新增日记
    @VerifyToken
    @PostMapping()
    public Result<String> addDiary(String diaryTitle, String diaryBody) {
        return iDiaryService.addDiary(diaryTitle, diaryBody);
    }

    //获取分页日记
    @VerifyToken
    @GetMapping("/list/{pageNum}/{pageSize}")
    public Result<PageInfo<Diary>> getDiaryList(@PathVariable(value = "pageNum") int pageNum, @PathVariable(value = "pageSize") int pageSize) {
        return iDiaryService.getDiaryList(pageNum, pageSize);
    }

    //删除日记
    @VerifyToken
    @DeleteMapping("/{id}")
    public Result<String> deleteDiary(@PathVariable(value = "id") int id) {
        return iDiaryService.deleteDiary(id);
    }

    //更新日记
    @VerifyToken
    @PutMapping("/{id}")
    public Result<String> updateDiary(@PathVariable(value = "id") int id, String diaryTitle, String diaryBody) {
        return iDiaryService.updateDiary(id, diaryTitle, diaryBody);
    }

    //根据id获取日记
    @VerifyToken
    @GetMapping("/{id}")
    public Result<Diary> getDiaryById(@PathVariable(value = "id") int id) {
        return iDiaryService.getDiaryById(id);
    }

    //获取归档
    @VerifyToken
    @GetMapping("/archive")
    public Result<List<Map<String, Integer>>> getArchive() {

        return iDiaryService.getArchive();
    }

    //获取搜索结果
    @VerifyToken
    @GetMapping("/{text}/{pageNum}/{pageSize}")
    public Result<PageInfo<Diary>> searchDiary(@PathVariable(value = "text") String text,
                                               @PathVariable(value = "pageNum") int pageNum,
                                               @PathVariable(value = "pageSize") int pageSize) {
        return iDiaryService.searchDiary(text, pageNum, pageSize);
    }
}
