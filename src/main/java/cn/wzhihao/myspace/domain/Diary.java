package cn.wzhihao.myspace.domain;

import java.util.Date;

public class Diary {
    private Integer id;
    private Integer userId;
    private String diaryTitle;
    private String diaryContent;
    private Integer diaryState;
    private Date createTime;
    private Date updateTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getDiaryTitle() {
        return diaryTitle;
    }

    public void setDiaryTitle(String diaryTitle) {
        this.diaryTitle = diaryTitle;
    }

    public String getDiaryContent() {
        return diaryContent;
    }

    public void setDiaryContent(String diaryContent) {
        this.diaryContent = diaryContent;
    }

    public Integer getDiaryState() {
        return diaryState;
    }

    public void setDiaryState(Integer diaryState) {
        this.diaryState = diaryState;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
