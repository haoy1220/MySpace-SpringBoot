package cn.wzhihao.myspace.service;

import cn.wzhihao.myspace.common.Result;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IDiaryService {
    Result<String> uploadImg(MultipartFile file) throws IOException;
}
