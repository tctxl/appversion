package com.opdar.appversion.entity;

import com.opdar.appversion.mapper.AppChannelMapper;
import com.opdar.plugins.mybatis.annotations.Id;
import com.opdar.plugins.mybatis.annotations.Namespace;
import com.opdar.plugins.mybatis.annotations.Sort;

import java.sql.Timestamp;

@Namespace(AppChannelMapper.class)
public class AppChannelEntity {
    @Id(Id.GenerateType.AUTO)
    private Long id;
    private Long appId;
    private String versionName;
    @Sort(type = Sort.SortType.DESC)
    private Integer versionCode;
    private String channel;
    private Integer type;
    private String title;
    private String content;
    private String url;
    private Long nextId;
    @Sort(type = Sort.SortType.DESC)
    private Timestamp createTime;
    private Timestamp updateTime;

    @Override
    public String toString() {
        return "AppChannelEntity{" +
                "id=" + id +
                ", appId='" + appId + '\'' +
                ", versionName='" + versionName + '\'' +
                ", versionCode=" + versionCode +
                ", channel='" + channel + '\'' +
                ", type=" + type +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", url='" + url + '\'' +
                ", nextId=" + nextId +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

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

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getNextId() {
        return nextId;
    }

    public void setNextId(Long nextId) {
        this.nextId = nextId;
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
