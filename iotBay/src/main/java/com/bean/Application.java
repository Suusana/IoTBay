package com.bean;

import com.enums.Title;

import java.util.Date;
import java.io.Serializable;
public class Application implements Serializable {
    private Integer applicationId;
    private String title;
    private String description;
    private Date createDate;
    private Date updateDate;
    private Integer userId;

    public Application() {
    }

    public Application(Integer applicationId, String title, String description, Date createDate, Date updateDate, Integer userId) {
        this.applicationId = applicationId;
        this.title = title;
        this.description = description;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.userId = userId;
    }

    public Integer getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Integer applicationId) {
        this.applicationId = applicationId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
