package cn.wzhihao.myspace.service;

import cn.wzhihao.myspace.common.Result;
import cn.wzhihao.myspace.entity.Diary;
import com.github.pagehelper.PageInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface IDiaryService {
    Result<String> uploadImg(MultipartFile file) throws IOException;

    Result<String> addDiary(String diaryTitle, String diaryBody);

    Result<PageInfo<Diary>> getDiaryList(int pageNum, int pageSize);

    Result<String> deleteDiary(int id);

    Result<String> updateDiary(int id, String diaryTitle, String diaryBody);

    Result<Diary> getDiaryById(int id);

    Result<List<Map<String, Integer>>> getArchive();

    Result<PageInfo<Diary>> searchDiary(String text, int pageNum, int pageSize);
}
