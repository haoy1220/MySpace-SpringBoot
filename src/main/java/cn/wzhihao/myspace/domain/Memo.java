package cn.wzhihao.myspace.domain;

import java.util.Date;

public class Memo {
    private Integer id;
    private Integer userId;
    private Integer memoType;
    private String memoEmail;
    private Date memoDate;
    private String memoContent;
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

    public Integer getMemoType() {
        return memoType;
    }

    public void setMemoType(Integer memoType) {
        this.memoType = memoType;
    }

    public String getMemoEmail() {
        return memoEmail;
    }

    public void setMemoEmail(String memoEmail) {
        this.memoEmail = memoEmail;
    }

    public Date getMemoDate() {
        return memoDate;
    }

    public void setMemoDate(Date memoDate) {
        this.memoDate = memoDate;
    }

    public String getMemoContent() {
        return memoContent;
    }

    public void setMemoContent(String memoContent) {
        this.memoContent = memoContent;
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
