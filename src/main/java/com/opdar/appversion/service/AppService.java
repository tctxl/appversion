package com.opdar.appversion.service;

import com.opdar.appversion.base.ErrCodeException;
import com.opdar.appversion.base.Page;
import com.opdar.appversion.base.PageInterceptor;
import com.opdar.appversion.entity.AppChannelEntity;
import com.opdar.appversion.entity.AppEntity;
import com.opdar.appversion.entity.AppVersionEntity;
import com.opdar.appversion.mapper.AppChannelMapper;
import com.opdar.appversion.mapper.AppMapper;
import com.opdar.appversion.mapper.AppVersionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

@Service
public class AppService {

    @Autowired
    private AppChannelMapper appChannelMapper;
    @Autowired
    private AppVersionMapper appVersionMapper;
    @Autowired
    private AppMapper appMapper;

    public AppChannelEntity checkVersion(String version, String channel, Long appId) {
        AppChannelEntity where = new AppChannelEntity();
        where.setVersionName(version);
        where.setChannel(channel);
        where.setAppId(appId);
        AppChannelEntity appChannel = appChannelMapper.selectOne(where);
        if (appChannel != null && !StringUtils.isEmpty(appChannel.getNextId())) {
            AppChannelEntity nextWhere = new AppChannelEntity();
            nextWhere.setId(appChannel.getNextId());
            AppChannelEntity nextAppVersion = appChannelMapper.selectOne(nextWhere);
            if (nextAppVersion != null) {
                return nextAppVersion;
            }
        }
        where.setVersionName(null);
        appChannel = appChannelMapper.selectOne(where);
        if (appChannel != null) {
            String latest = appChannel.getVersionName();
            String[] ls = latest.split("\\.");
            boolean isUpdate = false;
            String[] v = version.split("\\.");
            if (v.length >= ls.length) {
                isUpdate = isNeedUpdate(ls, v, false);
            } else {
                isUpdate = isNeedUpdate(v, ls, true);
            }
            if (isUpdate) return appChannel;
        }
        throw new ErrCodeException("您的应用已经是最新版本.");
    }

    public List<AppEntity> appList(){
        return appMapper.selectList(null);
    }

    private boolean isNeedUpdate(String[] ls, String[] v, boolean reserve) {
        boolean isUpdate = false;
        for (int i = 0; i < v.length; i++) {
            int vi = Integer.parseInt(v[i]);
            int lvi = 0;
            if (i < ls.length) {
                lvi = Integer.parseInt(ls[i]);
            }
            isUpdate = reserve ? vi > lvi : lvi > vi;
            if (isUpdate) {
                break;
            }
            if(vi != lvi){
                break;
            }
        }
        return isUpdate;
    }

    public Page<AppEntity> findApp(Long id, Integer pageNo) {
        AppEntity where = new AppEntity();
        where.setId(id);
        AppEntity app = appMapper.selectOne(where);

        AppChannelEntity channelWhere = new AppChannelEntity();
        channelWhere.setAppId(app.getId());
        int pageSize = 10;
        PageInterceptor.setPage(pageNo,pageSize);
        List<AppChannelEntity> channels = appChannelMapper.selectList(channelWhere);
        app.setChannels(channels);
        Integer cnt = appChannelMapper.count(channelWhere);
        Page<AppEntity> page = new Page<>();
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        page.setCount(cnt);
        page.setData(app);
        return page;
    }

    public AppEntity updateApp(Long id, String appName, String platform, String appDesc, String icon) {
        AppEntity where = new AppEntity();
        where.setId(id);
        AppEntity update = new AppEntity();
        update.setAppName(appName);
        update.setPlatform(platform);
        update.setAppDesc(appDesc);
        update.setIcon(icon);
        update.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        appMapper.update(update,where);
        return appMapper.selectOne(where);
    }

    public AppEntity createApp() {
        AppEntity app = new AppEntity();
        app.setPlatform("Android");
        app.setAppName("未命名");
        app.setAppDesc("开发者很懒，什么都没写。");
        app.setIcon("/static/default/app.png");
        app.setCreateTime(new Timestamp(System.currentTimeMillis()));
        app.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        appMapper.insert(app);
        return app;
    }

    public AppChannelEntity createChannel(Long appId, Integer type, String channel, Long versionId, String title, String content, String url) {
        AppChannelEntity channelEntity = new AppChannelEntity();
        channelEntity.setAppId(appId);
        channelEntity.setType(type);
        channelEntity.setChannel(channel);
        AppVersionEntity where = new AppVersionEntity();
        where.setId(versionId);
        AppVersionEntity version = appVersionMapper.selectOne(where);
        channelEntity.setVersionName(version.getVersionName());
        channelEntity.setVersionCode(version.getVersionCode());
        channelEntity.setTitle(title);
        channelEntity.setContent(content);
        channelEntity.setUrl(url);
        channelEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
        channelEntity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        appChannelMapper.insert(channelEntity);
        return channelEntity;
    }

    public AppVersionEntity createVersion(Long appId, String versionName) {
        AppVersionEntity where = new AppVersionEntity();
        where.setAppId(appId);
        AppVersionEntity lastVersion = appVersionMapper.selectOne(where);
        int nextVersionCode = 0;
        if(lastVersion != null){
            nextVersionCode = lastVersion.getVersionCode() + 1;
        }

        AppVersionEntity newVersion = new AppVersionEntity();
        newVersion.setAppId(appId);
        newVersion.setVersionCode(nextVersionCode);
        newVersion.setVersionName(versionName);
        newVersion.setCreateTime(new Timestamp(System.currentTimeMillis()));
        newVersion.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        appVersionMapper.insert(newVersion);
        return newVersion;
    }

    public List<AppVersionEntity> findVersions(Long appId) {
        AppVersionEntity where = new AppVersionEntity();
        where.setAppId(appId);
        return appVersionMapper.selectList(where);
    }

    public AppEntity findLastAppVersion(Long appId) {
        AppEntity appWhere = new AppEntity();
        appWhere.setId(appId);
        AppEntity app = appMapper.selectOne(appWhere);
        AppChannelEntity where = new AppChannelEntity();
        where.setShareTop(1);
        where.setAppId(appId);
        final AppChannelEntity channel = appChannelMapper.selectOne(where);
        if(channel != null){
            app.setChannels(new LinkedList<AppChannelEntity>(){{add(channel);}});
        }
        return app;
    }

    public AppEntity findAppChannel(Long channelId) {
        AppChannelEntity where = new AppChannelEntity();
        where.setAppOpen(1);
        where.setId(channelId);
        final AppChannelEntity channel = appChannelMapper.selectOne(where);
        if(channel != null){
            AppEntity appWhere = new AppEntity();
            appWhere.setId(channel.getAppId());
            AppEntity app = appMapper.selectOne(appWhere);
            app.setChannels(new LinkedList<AppChannelEntity>(){{add(channel);}});
            return app;
        }
        throw new ErrCodeException("渠道号不存在");
    }

    public void shareApp(Long channelId, Long appId) {
        AppChannelEntity where = new AppChannelEntity();
        where.setAppId(appId);
        AppChannelEntity update = new AppChannelEntity();
        update.setShareTop(0);
        appChannelMapper.update(update,where);
        where.setId(channelId);
        update.setShareTop(1);
        appChannelMapper.update(update,where);
    }

    public Integer shareChannelApp(Long channelId, Long appId) {
        AppChannelEntity where = new AppChannelEntity();
        where.setAppId(appId);
        where.setId(channelId);
        AppChannelEntity appChannel = appChannelMapper.selectOne(where);
        AppChannelEntity update = new AppChannelEntity();
        if(appChannel.getAppOpen() == null || appChannel.getAppOpen() == 0){
            update.setAppOpen(1);
        }else{
            update.setAppOpen(0);
        }
        appChannelMapper.update(update,where);
        return update.getAppOpen();
    }

    public void deleteChannel(Long channelId, Long appId) {
        AppChannelEntity where = new AppChannelEntity();
        where.setId(channelId);
        where.setAppId(appId);
        appChannelMapper.delete(where);
    }

    public void deleteVersion(Long appId, Long versionId) {
        AppVersionEntity where = new AppVersionEntity();
        where.setId(versionId);
        where.setAppId(appId);
        appVersionMapper.delete(where);
    }

    @Transactional(rollbackFor = Throwable.class)
    public void deleteApp(Long id) {
        {
            AppVersionEntity where = new AppVersionEntity();
            where.setAppId(id);
            appVersionMapper.delete(where);
        }
        {
            AppChannelEntity where = new AppChannelEntity();
            where.setAppId(id);
            appChannelMapper.delete(where);
        }
        {
            AppEntity where = new AppEntity();
            where.setId(id);
            appMapper.delete(where);
        }
    }
}
