package mlearn.sabachina.com.cn.okhttp;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.Util;

/**
 * Created by zhc on 2018/1/30 0030.
 */

public class HttpRequest {
    private static final int DEFAULT_HTTP_TIMEOUT = 10 * 1000;
    private String serviceName;
    private String functionName;
    private Map<String, Object> params;
    private Method method;

    public HttpRequest(String serviceName, String functionName,
                       Map<String, Object> params, Method method) {
        this.serviceName = serviceName;
        this.functionName = functionName;
        this.params = params;
        this.method = method;
    }

    public void execute() {
        // baseUrl  http://192.168.1.101/portal/mvc/service/cmn/serviceName + "/" + functionName + ".json"
//        String serverHost = Utils.getResources().getString(R.string.defult_protocol) + "://" + Utils.getHost();
        String commonServicePath = "/mvc/service/cmn/";
//        String commonServiceUrl = serverHost + commonServicePath;
        String baseUrl = commonServicePath + serviceName + "/" + functionName + ".json";
        Request.Builder reqBuilder = new Request.Builder();
//        reqBuilder.addHeader("ck", Utils.getCK());
        Request request;
        if (method == Method.GET) {
            request = createGetRequest(baseUrl, reqBuilder);
        } else {
            request = createPostRequest(baseUrl, reqBuilder);
        }
        new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_HTTP_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(DEFAULT_HTTP_TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(DEFAULT_HTTP_TIMEOUT, TimeUnit.MILLISECONDS)
                .build().newCall(request).enqueue(getResponseCallback());
    }

    private Request createGetRequest(String baseUrl, Request.Builder reqBuilder) {
        Request request;
        HttpUrl.Builder builder = HttpUrl.parse(baseUrl).newBuilder();
//        builder.addQueryParameter("ck", Utils.getCK());
        if (params != null) {
            Set<Map.Entry<String, Object>> entrySet = params.entrySet();
            for (Map.Entry<String, Object> entry : entrySet) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (!TextUtils.isEmpty(key) && value != null) {
                    builder.addQueryParameter(key, value.toString());
                }
            }
        }
        request = reqBuilder.url(builder.build()).build();
        return request;
    }

    /**
     * 不带文件
     * @param baseUrl
     * @param reqBuilder
     * @return
     */
    private Request createPostRequest(String baseUrl, Request.Builder reqBuilder) {
        int count = 0;
        Request request;
        FormBody.Builder builder = new FormBody.Builder();
//        builder.add("ck", Utils.getCK());
        Set<Map.Entry<String, Object>> entrySet = params.entrySet();
        if (params != null) {
            for (Map.Entry<String, Object> entry : entrySet) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (!TextUtils.isEmpty(key) && value != null) {
                    builder.add(key, value.toString());
                    count++;
                }
            }
        }
        if (count > 0) {
            request = reqBuilder.url(baseUrl).post(builder.build()).build();
        } else {
            request = reqBuilder.url(baseUrl).post(Util.EMPTY_REQUEST).build();
        }
        return request;
    }

    /**
     * 带文件
     * @param baseUrl
     * @param reqBuilder
     * @return
     */
    private Request createPostRequestWithFile(String baseUrl, Request.Builder reqBuilder) {
        int count = 0;
        Request request;
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
//        builder.addFormDataPart("ck", Utils.getCK());
        Set<Map.Entry<String, Object>> entrySet = params.entrySet();
        if (params != null) {
            for (Map.Entry<String, Object> entry : entrySet) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (!TextUtils.isEmpty(key) && value != null) {
                    if (value instanceof File) {
                        File file = (File) value;
                       builder.addFormDataPart(key,file.getName(), RequestBody.create(getMediaType(file),file));
                    }else {
                        builder.addFormDataPart(key, value.toString());
                    }
                    count++;
                }
            }
        }
        if (count > 0) {
            request = reqBuilder.url(baseUrl).post(builder.build()).build();
        } else {
            request = reqBuilder.url(baseUrl).post(Util.EMPTY_REQUEST).build();
        }
        return request;
    }

    private MediaType getMediaType(File file) {
        String name = file.getName();
        if (name.lastIndexOf("png") > 0 || name.lastIndexOf("PNG") > 0){
            return MediaType.parse("image/png; charset=UTF-8");
        }
        if (name.lastIndexOf("jpg") > 0 || name.lastIndexOf("JPG") > 0
                ||name.lastIndexOf("jpeg") > 0 || name.lastIndexOf("JPEG") > 0){
            return MediaType.parse("image/jpeg; charset=UTF-8");
        }
        return MediaType.parse("application/octet-stream");
    }

    private Callback getResponseCallback() {

        return new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull final Response response) throws IOException {

            }
        };
    }
}
