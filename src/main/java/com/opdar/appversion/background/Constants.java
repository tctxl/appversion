package com.opdar.appversion.background;

public class Constants {
    public static final long DASH_USER_TIME = 3600 * 1000;
    public static final String ENTRY = "entry/config.json";

    public final static class Key{
        public static final String USER_NAME = "user";
        public static final String USER_PWD = "pass";
    }
    public final static class ErrorText{
        public static final String NO_ENTRY = "您访问的页面不存在，如果您忘记了入口点地址，请重新初始化。";
        public static final String USERNAME_OR_PASSWORD_IS_INCORRECT = "用户名或密码不正确，请核对后重新输入。";
        public static final String APP_ID_ERROR = "APPID错误";
        public static final String FILE_IS_NOT_FOUND = "上传文件不存在";
        public static final String PARAMS_ERROR = "参数错误，请核对后重新提交";
        public static final String TOKEN_EXPIRE = "登录超时，请重新登陆";
    }
}
