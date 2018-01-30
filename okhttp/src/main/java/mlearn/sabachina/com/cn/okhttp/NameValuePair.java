package mlearn.sabachina.com.cn.okhttp;


import android.util.Log;

import java.io.File;

/**
 * 包装参数用的兼职对
 *
 * @author htyuan
 */
public class NameValuePair {

    private final String name;
    private final Object value;

    /**
     * Default Constructor taking a name and a value. The value may be null.
     *
     * @param name  The name.
     * @param value The value.
     */
    public NameValuePair(String name, String value) {
        this.name = name;
        this.value = value;
        if (value == null || value.length() <= 0) {
            Log.w("NameValuePair", "参数不能为空！！！");
        }
    }

    public NameValuePair(String name, File value) {
        this.name = name;
        this.value = value;
        if (value == null || !value.exists()) {
            Log.w("NameValuePair", "参数不能为空！！！");
        }
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }


    public boolean isStringValue() {
        if (value != null && value instanceof String) {
            return true;
        }
        return false;
    }

    public boolean isFileValue() {
        if (value != null && value instanceof File) {
            return true;
        }
        return false;
    }

    public String getStringValue() {
        return (String) value;
    }

    public File getFileValue() {
        return (File) value;
    }
}
