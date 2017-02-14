package cn.guoyukun.spring.jpa;

/**
 * <p>User: 郭玉昆
 * <p>Date: 13-2-7 下午5:14
 * <p>Version: 1.0
 */
public interface Constants {
    /**
     * 操作名称
     */
    String OP_NAME = "op";


    /**
     * 消息key
     */
    String MESSAGE = "message";

    /**
     * 错误key
     */
    String ERROR = "error";

    /**
     * 上个页面地址
     */
    String BACK_URL = "BackURL";
    /**
     * 登录失败的URL
     */
    String FAIL_BACK_URL = "FailBackURL";

    String IGNORE_BACK_URL = "ignoreBackURL";

    /**
     * 当前请求的地址 带参数
     */
    String CURRENT_URL = "currentURL";

    /**
     * 当前请求的地址 不带参数
     */
    String NO_QUERYSTRING_CURRENT_URL = "noQueryStringCurrentURL";

    String CONTEXT_PATH = "ctx";

    /**
     * 当前登录的用户
     */
    //String CURRENT_USER = "user";
    String CURRENT_USER2 = "user";
    String CURRENT_USERID = "current_id";
    String CURRENT_USER_EMAIL = "current_email";

    String ENCODING = "UTF-8";

}
