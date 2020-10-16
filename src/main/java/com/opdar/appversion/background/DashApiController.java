package com.opdar.appversion.background;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.opdar.appversion.base.ErrCodeException;
import com.opdar.appversion.base.ErrorPrinterHandler;
import com.opdar.appversion.base.Page;
import com.opdar.appversion.base.ResultEditor;
import com.opdar.appversion.entity.AppEntity;
import com.opdar.appversion.service.AppService;
import com.opdar.mote.web.annotations.web.Editor;
import com.opdar.mote.web.annotations.web.ErrorHandler;
import com.opdar.mote.web.annotations.web.Request;
import com.opdar.mote.web.session.ISessionManager;
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
        File file = new File("entry.json");
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


    @Request(value = "/api/dash/app/list",restful = true,format = Request.Format.JSON)
    public List<AppEntity> appList() throws IOException {
        return appService.appList();
    }

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
}
