package com.opdar.appversion.background;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.opdar.appversion.base.ErrCodeException;
import com.opdar.mote.web.annotations.web.ErrorHandler;
import com.opdar.mote.web.annotations.web.Request;
import com.opdar.mote.web.base.Context;
import com.opdar.mote.web.view.ViewTemplate;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.IOException;

@Controller
@ErrorHandler(ErrorPageViewHandler.class)
public class DashControlller {

    @Request(value = "/{{entryCode}}",restful = true,format = Request.Format.VIEW)
    public String dash(String entryCode){
        try {
            File file = new File(Constants.ENTRY);
            String entry = null;
            if(file.exists()){
                entry = FileUtils.readFileToString(file);
            }
            if(entry == null){
                JSONObject o = new JSONObject();
                o.put("entryCode",EntryUtils.build());
                o.put(Constants.Key.USER_NAME,EntryUtils.build(6));
                o.put(Constants.Key.USER_PWD,EntryUtils.build(12));
                FileUtils.writeStringToFile(file,o.toJSONString());
                Context.putAttribute("entryCode",o.getString("entryCode"));
                Context.putAttribute(Constants.Key.USER_NAME,o.getString(Constants.Key.USER_NAME));
                Context.putAttribute(Constants.Key.USER_PWD,o.getString(Constants.Key.USER_PWD));
                return "init";
            }else{
                JSONObject o = JSON.parseObject(entry);
                String _entryCode = o.getString("entryCode");
                if(!_entryCode.equals(entryCode)){
                    throw new ErrCodeException(Constants.ErrorText.NO_ENTRY);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "dash/login";
    }
}
