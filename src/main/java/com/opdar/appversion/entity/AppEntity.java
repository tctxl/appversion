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
    private String desc;
    private Timestamp createTime;
    private Timestamp updateTime;
    @Field(resultmap=false,insert = false,update = false,delete = false,select = false)
    private List<AppChannelEntity> channels = new LinkedList<>();

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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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
