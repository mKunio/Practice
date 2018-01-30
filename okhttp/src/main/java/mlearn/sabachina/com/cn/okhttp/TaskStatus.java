package mlearn.sabachina.com.cn.okhttp;

/**
 * Created by jian on 16/10/8.
 */

public enum TaskStatus {
    // 这个状态几乎不被用到
    INIT,
    //正在请求中
    STARTED,
    COMPLETED,
    CANCELED
}
