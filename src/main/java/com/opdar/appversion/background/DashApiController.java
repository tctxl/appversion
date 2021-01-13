package com.opdar.appversion.background;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.opdar.appversion.base.ErrCodeException;
import com.opdar.appversion.base.ErrorPrinterHandler;
import com.opdar.appversion.base.Page;
import com.opdar.appversion.base.ResultEditor;
import com.opdar.appversion.entity.AppChannelEntity;
import com.opdar.appversion.entity.AppEntity;
import com.opdar.appversion.entity.AppVersionEntity;
import com.opdar.appversion.service.AppService;
import com.opdar.mote.web.annotations.web.Editor;
import com.opdar.mote.web.annotations.web.ErrorHandler;
import com.opdar.mote.web.annotations.web.Interceptor;
import com.opdar.mote.web.annotations.web.Request;
import com.opdar.mote.web.session.ISessionManager;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@Editor(ResultEditor.class)
@ErrorHandler(ErrorPrinterHandler.class)
public class DashApiController {
    @Autowired
    private ISessionManager<UserEntity> sessionManager;

    @Autowired
    private AppService appService;

    @Request(value = "/api/dash/login",restful = true,format = Request.Format.JSON)
    public String login(String userName,String userPwd) throws IOException {
        File file = new File(Constants.ENTRY);
        String entry = null;
        if(file.exists()){
            entry = FileUtils.readFileToString(file);
            JSONObject o = JSON.parseObject(entry);
            String _userName = o.getString(Constants.Key.USER_NAME);
            String _userPwd = o.getString(Constants.Key.USER_PWD);
            if(userName.equals(_userName) && userPwd.equals(_userPwd)){
                String token = UUID.randomUUID().toString();
                UserEntity user = new UserEntity();
                user.setUserName(_userName);
                user.setUserPwd(_userPwd);
                sessionManager.set(token,user,Constants.DASH_USER_TIME);
                return token;
            }else{
                throw new ErrCodeException(Constants.ErrorText.USERNAME_OR_PASSWORD_IS_INCORRECT);
            }
        }
        throw new ErrCodeException(Constants.ErrorText.NO_ENTRY);
    }


    @Interceptor(TokenInterceptor.class)
    @Request(value = "/api/dash/app/list",restful = true,format = Request.Format.JSON)
    public List<AppEntity> appList() throws IOException {
        return appService.appList();
    }

    @Interceptor(TokenInterceptor.class)
    @Request(value = "/api/dash/app/detail",restful = true,format = Request.Format.JSON)
    public Page<AppEntity> appDetail(Long id, Integer pageNo) {
        if(StringUtils.isEmpty(id)){
            throw new ErrCodeException(Constants.ErrorText.APP_ID_ERROR);
        }
        if(pageNo == null){
            pageNo = 1;
        }
        return appService.findApp(id,pageNo);
    }

    @Interceptor(TokenInterceptor.class)
    @Request(value = "/api/dash/app/create",restful = true,format = Request.Format.JSON)
    public AppEntity appCreate() {
        return appService.createApp();
    }

    @Interceptor(TokenInterceptor.class)
    @Request(value = "/api/dash/app/update",restful = true,format = Request.Format.JSON)
    public AppEntity appUpdate(Long id, String appName, String platform, String appDesc, String icon) {
        if(StringUtils.isEmpty(id)){
            throw new ErrCodeException(Constants.ErrorText.APP_ID_ERROR);
        }
        return appService.updateApp(id,appName,platform,appDesc,icon);
    }

    @Interceptor(TokenInterceptor.class)
    @Request(value = "/api/dash/app/share",restful = true,format = Request.Format.JSON)
    public String appShare(Long id, Long appId) {
        if(StringUtils.isEmpty(appId)){
            throw new ErrCodeException(Constants.ErrorText.APP_ID_ERROR);
        }
        if(StringUtils.isEmpty(id)){
            throw new ErrCodeException(Constants.ErrorText.PARAMS_ERROR);
        }
        appService.shareApp(id,appId);
        return "";
    }

    @Interceptor(TokenInterceptor.class)
    @Request(value = "/api/dash/channel/share",restful = true,format = Request.Format.JSON)
    public Integer channelShare(Long id, Long appId) {
        if(StringUtils.isEmpty(appId)){
            throw new ErrCodeException(Constants.ErrorText.APP_ID_ERROR);
        }
        if(StringUtils.isEmpty(id)){
            throw new ErrCodeException(Constants.ErrorText.PARAMS_ERROR);
        }
        return appService.shareChannelApp(id,appId);
    }

    @Interceptor(TokenInterceptor.class)
    @Request(value = "/api/dash/app/delete",restful = true,format = Request.Format.JSON)
    public String appDelete(Long id) {
        if(StringUtils.isEmpty(id)){
            throw new ErrCodeException(Constants.ErrorText.PARAMS_ERROR);
        }
        appService.deleteApp(id);
        return "";
    }

    @Interceptor(TokenInterceptor.class)
    @Request(value = "/api/dash/app/channel/delete",restful = true,format = Request.Format.JSON)
    public String deleteChannel(Long id, Long appId) {
        if(StringUtils.isEmpty(appId)){
            throw new ErrCodeException(Constants.ErrorText.APP_ID_ERROR);
        }
        if(StringUtils.isEmpty(id)){
            throw new ErrCodeException(Constants.ErrorText.PARAMS_ERROR);
        }
        appService.deleteChannel(id,appId);
        return "";
    }

    @Interceptor(TokenInterceptor.class)
    @Request(value = "/api/dash/app/channel/create",restful = true,format = Request.Format.JSON)
    public AppChannelEntity appCreateChannel(Long appId, Integer type, String channel, Long versionId, String title, String content, String url) {
        if(StringUtils.isEmpty(appId)){
            throw new ErrCodeException(Constants.ErrorText.APP_ID_ERROR);
        }
        if(StringUtils.isEmpty(type) || StringUtils.isEmpty(channel) || StringUtils.isEmpty(versionId) || StringUtils.isEmpty(title) || StringUtils.isEmpty(content) || StringUtils.isEmpty(url)){
            throw new ErrCodeException(Constants.ErrorText.PARAMS_ERROR);
        }
        return appService.createChannel(appId,type,channel,versionId,title,content,url);
    }

    @Interceptor(TokenInterceptor.class)
    @Request(value = "/api/dash/app/version/create",restful = true,format = Request.Format.JSON)
    public AppVersionEntity appCreateVersion(Long appId, String versionName) {
        if(StringUtils.isEmpty(appId)){
            throw new ErrCodeException(Constants.ErrorText.APP_ID_ERROR);
        }
        if(StringUtils.isEmpty(versionName)){
            throw new ErrCodeException(Constants.ErrorText.PARAMS_ERROR);
        }
        return appService.createVersion(appId,versionName);
    }

    @Interceptor(TokenInterceptor.class)
    @Request(value = "/api/dash/app/version/delete",restful = true,format = Request.Format.JSON)
    public String appDeleteVersion(Long appId, Long id) {
        if(StringUtils.isEmpty(appId)){
            throw new ErrCodeException(Constants.ErrorText.APP_ID_ERROR);
        }
        if(StringUtils.isEmpty(id)){
            throw new ErrCodeException(Constants.ErrorText.PARAMS_ERROR);
        }
        appService.deleteVersion(appId,id);
        return "";
    }


    @Interceptor(TokenInterceptor.class)
    @Request(value = "/api/dash/app/version/find",restful = true,format = Request.Format.JSON)
    public List<AppVersionEntity> appCreateList(Long appId) {
        if(StringUtils.isEmpty(appId)){
            throw new ErrCodeException(Constants.ErrorText.APP_ID_ERROR);
        }
        return appService.findVersions(appId);
    }

    @Interceptor(TokenInterceptor.class)
    @Request(value = "/api/dash/app/icon", format = Request.Format.JSON)
    public String appUpload(FileItem[] file) throws Exception {
        if(file == null){
            throw new ErrCodeException(Constants.ErrorText.FILE_IS_NOT_FOUND);
        }

        String fileName = UUID.randomUUID().toString().replaceAll("-","");
        String savePath = "./upload";
        FileItem fileItems = file[0];
        if (fileItems instanceof DiskFileItem) {
            File f = new File(savePath, fileName);
            if (!f.getParentFile().exists()) {
                f.getParentFile().mkdirs();
            }
            fileItems.write(f);
            return "/upload/"+fileName;
        }
        throw new ErrCodeException();
    }

    @Interceptor(TokenInterceptor.class)
    @Request(value = "/api/dash/app/version", format = Request.Format.JSON)
    public String versionUpload(FileItem[] file,String appId) throws Exception {
        if(file == null || StringUtils.isEmpty(appId)){
            throw new ErrCodeException(Constants.ErrorText.FILE_IS_NOT_FOUND);
        }

        String fileName = UUID.randomUUID().toString().replaceAll("-","") + ".apk";
        String savePath = "./apk/"+appId+"/";
        FileItem fileItems = file[0];
        if (fileItems instanceof DiskFileItem) {
            File f = new File(savePath, fileName);
            if (!f.getParentFile().exists()) {
                f.getParentFile().mkdirs();
            }
            fileItems.write(f);
            return "/apk/"+appId+"/"+fileName;
        }
        throw new ErrCodeException();
    }
}
