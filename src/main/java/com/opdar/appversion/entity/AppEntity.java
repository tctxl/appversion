package com.opdar.appversion.entity;

import com.opdar.appversion.mapper.AppChannelMapper;
import com.opdar.appversion.mapper.AppMapper;
import com.opdar.plugins.mybatis.annotations.Field;
import com.opdar.plugins.mybatis.annotations.Id;
import com.opdar.plugins.mybatis.annotations.Namespace;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

@Namespace(AppMapper.class)
public class AppEntity {
    @Id(Id.GenerateType.AUTO)
    private Long id;
    private String appName;
    private String platform;
    private String appDesc;
    private String icon;
    private Timestamp createTime;
    private Timestamp updateTime;
    @Field(resultmap=false,insert = false,update = false,delete = false,select = false)
    private List<AppChannelEntity> channels = new LinkedList<>();

    @Override
    public String toString() {
        return "AppEntity{" +
                "id=" + id +
                ", appName='" + appName + '\'' +
                ", platform='" + platform + '\'' +
                ", appDesc='" + appDesc + '\'' +
                ", icon='" + icon + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", channels=" + channels +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getAppDesc() {
        return appDesc;
    }

    public void setAppDesc(String appDesc) {
        this.appDesc = appDesc;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
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

    public void setChannels(List<AppChannelEntity> channels) {
        this.channels = channels;
    }

    public List<AppChannelEntity> getChannels() {
        return channels;
    }
}
