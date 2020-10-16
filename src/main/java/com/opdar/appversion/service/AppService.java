package com.opdar.appversion.service;

import com.opdar.appversion.base.ErrCodeException;
import com.opdar.appversion.base.Page;
import com.opdar.appversion.base.PageInterceptor;
import com.opdar.appversion.entity.AppChannelEntity;
import com.opdar.appversion.entity.AppEntity;
import com.opdar.appversion.mapper.AppChannelMapper;
import com.opdar.appversion.mapper.AppMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class AppService {

    @Autowired
    private AppChannelMapper appChannelMapper;
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
}
