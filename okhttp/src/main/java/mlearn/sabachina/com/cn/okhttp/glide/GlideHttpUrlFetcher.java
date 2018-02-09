package mlearn.sabachina.com.cn.okhttp.glide;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.util.ContentLengthInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Set;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by zhc on 2018/2/9 0009.
 */

public final class GlideHttpUrlFetcher implements DataFetcher<InputStream> {
    private final OkHttpClient client;
    private final GlideUrl url;
    private InputStream stream;
    private ResponseBody responseBody;
    private volatile boolean isCancelled;

    public GlideHttpUrlFetcher(OkHttpClient client, GlideUrl url) {
        this.client = client;
        this.url = url;
    }

    @Override
    public InputStream loadData(Priority priority) throws Exception {
        Request.Builder builder = new Request.Builder().url(url.toStringUrl());
        Set<Map.Entry<String, String>> entrySet = url.getHeaders().entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            builder.addHeader(entry.getKey(), entry.getValue());
        }
        builder.addHeader("httplib", "OkHttp");
        Request request = builder.build();
        if (isCancelled) {
            return null;
        }
        Response response = client.newCall(request).execute();
        responseBody = response.body();
        if (response.isSuccessful() && responseBody != null) {
            stream = ContentLengthInputStream.obtain(responseBody.byteStream(), responseBody.contentLength());
            return stream;
        }
        return null;
    }

    @Override
    public void cleanup() {
        try {
            if (stream != null) {
                stream.close();
            }
            if (responseBody != null) {
                responseBody.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getId() {
        return url.getCacheKey();
    }

    @Override
    public void cancel() {
        isCancelled = true;
    }
}
