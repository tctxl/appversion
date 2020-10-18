package com.opdar.appversion.entity;

import com.opdar.appversion.mapper.AppMapper;
import com.opdar.appversion.mapper.AppVersionMapper;
import com.opdar.plugins.mybatis.annotations.Field;
import com.opdar.plugins.mybatis.annotations.Id;
import com.opdar.plugins.mybatis.annotations.Namespace;
import com.opdar.plugins.mybatis.annotations.Sort;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

@Namespace(AppVersionMapper.class)
public class AppVersionEntity {
    @Id(Id.GenerateType.AUTO)
    private Long id;
    private Long appId;
    private String versionName;
    @Sort(type = Sort.SortType.DESC)
    private Integer versionCode;
    private Timestamp createTime;
    private Timestamp updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public Integer getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(Integer versionCode) {
        this.versionCode = versionCode;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }
}
