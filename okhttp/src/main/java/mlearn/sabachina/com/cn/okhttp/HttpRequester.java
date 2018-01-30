package mlearn.sabachina.com.cn.okhttp;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * 说明：
 * 作者：杨健
 * 时间：2017/5/17.
 */

public abstract class HttpRequester<T extends HttpResult> implements HQAsyncTask<T> {
    /**
     * The default socket timeout in milliseconds
     */
    public static final int MY_DEFAULT_TIMEOUT_MS = 10 * 1000;

    protected TaskListener<T> mTaskListener;
    /**
     * TaskStatus
     */
    protected TaskStatus status;
    protected final Class<T> mResultClassType;
    Context context;

    public HttpRequester(@Nullable Context ctx, @Nullable TaskListener<T> taskListener,
                         Class<T> resultClassType) {
        this.mTaskListener = taskListener;
        this.status = TaskStatus.INIT;
        this.mResultClassType = resultClassType;
        this.context = ctx;
    }

    @Override
    public void execute() {
        onStarted();

        // 准备数据
        HashMap<String, String> header = new HashMap<>();
        setHeader(header);
        ArrayList<NameValuePair> params = new ArrayList<>();
        addParam(params);

        Request.Builder reqBuild = makeRequestBuildWithHeader(header);

        // 构造请求体
        Request request;
        if (isGetMethod()) {
            request = makeGetRequest(params, reqBuild);
        } else {
            request = makePostRequest(params, reqBuild);
        }

        // 发起网络请求
        (new OkHttpClient.Builder()
                .connectTimeout(getTimeOutMS(), TimeUnit.MILLISECONDS))
                .readTimeout(getTimeOutMS(), TimeUnit.MILLISECONDS)
                .writeTimeout(getTimeOutMS(), TimeUnit.MILLISECONDS)
                .build().newCall(request).enqueue(getResponseCallback());
    }

    @NonNull
    private Request.Builder makeRequestBuildWithHeader(HashMap<String, String> header) {
        Set<String> keys = header.keySet();
        Request.Builder reqBuild = new Request.Builder();
        for (String key : keys) {
            if (TextUtils.isEmpty(key)) continue;
            if (TextUtils.isEmpty(header.get(key))) continue;
            reqBuild.addHeader(key, header.get(key));
        }
        return reqBuild;
    }

    private Request makeGetRequest(ArrayList<NameValuePair> params, Request.Builder reqBuild) {
        Request request;
        HttpUrl.Builder urlBuilder = HttpUrl.parse(getHost() + getPath()).newBuilder();
        for (NameValuePair pair : params) {
            if (pair.isStringValue()) {
                urlBuilder.addQueryParameter(pair.getName(), pair.getStringValue());
            }
        }
        request = reqBuild.url(urlBuilder.build()).build();
//        SparseArray
        return request;
    }

    private Request makePostRequest(ArrayList<NameValuePair> params, Request.Builder reqBuild) {
        Request request;
        int count = 0;
        MultipartBody.Builder multipartBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        for (NameValuePair pair : params) {
            if (pair.isStringValue()) {
                multipartBody.addFormDataPart(pair.getName(), pair.getStringValue());
                count++;
            }
            if (pair.isFileValue()) {
                MediaType type = MediaType.parse("application/octet-stream");
                RequestBody fileBody = RequestBody.create(type, pair.getFileValue());
                multipartBody.addFormDataPart(pair.getName(), pair.getFileValue().getName(), fileBody);
//                RequestBody.create()
                count++;
            }
        }
        if (count > 0) {
            request = reqBuild.url(getHost() + getPath()).post(multipartBody.build()).build();
        } else {
            request = reqBuild.url(getHost() + getPath()).post(okhttp3.internal.Util.EMPTY_REQUEST).build();
        }
        return request;
    }


    @NonNull
    private Callback getResponseCallback() {
        return new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, final IOException e) {
                System.out.println(e.toString());
                onCompleted(null, e);
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                T result = null;
                Exception exception = null;
                try {
                    String body = response.body().string();
                    Log.e("net", "body = " + body);
//                    result = new Gson().fromJson(body, mResultClassType);
                    // 当前业务类的异常
                    exception = new IOException(body);

                } catch (Exception e) {
                    exception = e;
                } finally {
                    onCompleted(result, exception);
                }
            }
        };
    }

    public TaskStatus getStatus() {
        return status;
    }


    protected int getTimeOutMS() {
        return MY_DEFAULT_TIMEOUT_MS;
    }

    @Override
    public void cancel() {
        onCanceled();
    }

    public TaskListener<T> getTaskListener() {
        return mTaskListener;
    }

    @Override
    public void setTaskListener(TaskListener<T> listener) {
        this.mTaskListener = listener;
    }

    protected abstract void setHeader(HashMap<String, String> header);

    /**
     * POST or GET 请求
     *
     * @return
     */
    protected boolean isGetMethod() {
        return true;
    }

    protected abstract void addParam(List<NameValuePair> list);

    /**
     * 获取url路径，带最前面的/
     *
     * @return
     */
    protected abstract String getPath();

    /**
     * 获取主机地址，结尾不带/
     *
     * @return
     */
    protected abstract String getHost();

    @CallSuper
    protected boolean onStarted() {
        boolean startAble = true;
        switch (status) {
            case INIT:
                startAble = true;
                break;
            default:
                startAble = false;
                break;
        }
        if (!startAble) {
            if (BuildConfig.DEBUG) {
                throw new IllegalStateException("不要重复启动同一个任务");
            } else {
                startAble = true;
            }
        }
        status = TaskStatus.STARTED;
        if (mTaskListener != null) {
            mTaskListener.onTaskStart(mTaskListener);
        }
        return startAble;
    }

    @CallSuper
    protected boolean onCanceled() {
        boolean cancelAble;
        switch (status) {
            case INIT:
            case STARTED:
                cancelAble = true;
                break;
            default:
                cancelAble = false;
                break;
        }
        if (!cancelAble) return false;
        status = TaskStatus.CANCELED;
        if (mTaskListener != null) {
            mTaskListener.onCancel();
        }
        mTaskListener = null;
        return cancelAble;
    }

    @CallSuper
    protected boolean onCompleted(final T result, final Exception e) {
        boolean completeAble = true;
        switch (status) {
            case INIT:
            case STARTED:
                completeAble = true;
                break;
            default:
                completeAble = false;
                break;
        }
        if (!completeAble) return false;
        status = TaskStatus.COMPLETED;
        Handler handler = new Handler(context.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (result != null && result.getState() == 40002) {
                    if (hasSession()) {
                        // 不交给mTaskListener，直接处理掉
                        onSessionOutTime(getHost() + getPath());
                        return;
                    } else {
                        onIllegalRequestWithoutSession(getHost() + getPath());
                    }
                }

                // 如果Activity在destroy前没有调用cancel,这里很可能发生奔溃
                // 加入try，这样即使忘记cancel也不会奔溃
                try {
                    if (mTaskListener != null) {
                        mTaskListener.onTaskComplete(mTaskListener, result, e);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return completeAble;
    }

    protected abstract boolean hasSession();

    protected abstract void onSessionOutTime(String url);

    protected abstract void onIllegalRequestWithoutSession(String url);
}
