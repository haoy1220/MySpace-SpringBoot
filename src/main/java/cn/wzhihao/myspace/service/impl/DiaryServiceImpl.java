package cn.wzhihao.myspace.service.Impl;

import cn.wzhihao.myspace.common.Const;
import cn.wzhihao.myspace.common.Result;
import cn.wzhihao.myspace.dao.DiaryMapper;
import cn.wzhihao.myspace.entity.Diary;
import cn.wzhihao.myspace.service.IDiaryService;
import cn.wzhihao.myspace.utils.JwtTokenUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Transactional
@Service("iDiaryService")
public class DiaryServiceImpl implements IDiaryService {

    private static Logger logger = LoggerFactory.getLogger(DiaryServiceImpl.class);

    @Autowired
    private DiaryMapper diaryMapper;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private HttpServletRequest request;

    //上传文件服务
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
        return Result.SuccessByData("图片上传成功", Const.IMG_URL + "/" + fileSaveName);
    }


    //新增日记服务
    @Override
    public Result<String> addDiary(String diaryTitle, String diaryBody) {
        Diary diary = new Diary();
        diary.setUserEmail(jwtTokenUtil.getEmailFromRequest(request));
        diary.setDiaryTitle(diaryTitle);
        diary.setDiaryBody(diaryBody);
        diary.setCreateTime(Calendar.getInstance().getTimeInMillis());
        diary.setUpdateTime(Calendar.getInstance().getTimeInMillis());

        int res = diaryMapper.insertSelective(diary);
        if (res > 0) {
            return Result.Success("保存日记成功");
        } else {
            return Result.Error(Const.StatusCode.SQL_ERROR, "数据库插入失败");
        }
    }

    //获取分页日记服务
    @Override
    public Result<PageInfo<Diary>> getDiaryList(int pageNum, int pageSize) {
        logger.info(pageNum + "" + pageSize + "");
        //startPage
        PageHelper.startPage(pageNum, pageSize);
        List<Diary> diaryList = diaryMapper.selectByEmail(jwtTokenUtil.getEmailFromRequest(request));
        // 截取前100个字符 减少网络io
        for (Diary diary : diaryList) {
            String body = diary.getDiaryBody();
            if (body.length() > Const.MAX_BODY_CHAR_COUNT) {
                diary.setDiaryBody(body.substring(0, Const.MAX_BODY_CHAR_COUNT));
            }
        }
        PageInfo<Diary> pageInfo = new PageInfo<>(diaryList);
        return Result.SuccessByData("获取分页列表成功", pageInfo);
    }


    //删除日记服务
    @Override
    public Result<String> deleteDiary(int id) {
        Diary diary = new Diary();
        diary.setId(id);
        diary.setUserEmail(jwtTokenUtil.getEmailFromRequest(request));
        int res = diaryMapper.delete(diary);
        if (res > 0) {
            return Result.Success("删除成功");
        } else {
            return Result.Error(Const.StatusCode.SQL_ERROR, "权限不足或数据可能不存在");
        }
    }

    @Override
    public Result<String> updateDiary(int id, String diaryTitle, String diaryBody) {
        Diary diary = new Diary();
        diary.setId(id);
        diary.setUserEmail(jwtTokenUtil.getEmailFromRequest(request));
        diary.setDiaryTitle(diaryTitle);
        diary.setDiaryBody(diaryBody);
        diary.setUpdateTime(Calendar.getInstance().getTimeInMillis());
        //要用email放在纵向越权
        int res = diaryMapper.updateByEmailAndId(diary);
        if (res > 0) {
            return Result.Success("修改成功");
        } else {
            return Result.Error(Const.StatusCode.SQL_ERROR, "修改失败");
        }
    }

    @Override
    public Result<Diary> getDiaryById(int id) {
        Diary diary = new Diary();
        diary.setId(id);
        diary.setUserEmail(jwtTokenUtil.getEmailFromRequest(request));
        diary = diaryMapper.selectOne(diary);
        if (diary != null) {
            return Result.SuccessByData("获取日记成功", diary);
        } else {
            return Result.Error(Const.StatusCode.SQL_ERROR, "权限不足或数据不存在");
        }
    }

    //获取归档记录
    @Override
    public Result<List<Map<String, Integer>>> getArchive() {
        List<Map<String, Integer>> resMap = diaryMapper.selectArchiveByEmail(jwtTokenUtil.getEmailFromRequest(request));
        if (resMap.isEmpty()) {
            return Result.Error(Const.StatusCode.SQL_ERROR, "权限不足或数据不存在");
        }
        return Result.SuccessByData("查询归档数据成功", resMap);
    }

    //搜索日记
    @Override
    public Result<PageInfo<Diary>> searchDiary(String text, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Diary> diaryList = diaryMapper.selectByEmailAndText(jwtTokenUtil.getEmailFromRequest(request), text);
        for (Diary diary : diaryList) {
            String body = diary.getDiaryBody();
            if (body.length() > Const.MAX_BODY_CHAR_COUNT) {
                diary.setDiaryBody(body.substring(0, Const.MAX_BODY_CHAR_COUNT));
            }
        }
        PageInfo<Diary> pageInfo = new PageInfo<>(diaryList);
        if (pageInfo.getTotal() != 0) {
            return Result.SuccessByData("搜索成功", pageInfo);
        } else {
            return Result.Error(Const.StatusCode.SQL_ERROR, "没搜索到内容");
        }
    }


}
