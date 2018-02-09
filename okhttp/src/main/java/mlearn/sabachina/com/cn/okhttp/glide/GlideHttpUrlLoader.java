package mlearn.sabachina.com.cn.okhttp.glide;

import android.content.Context;

import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.GenericLoaderFactory;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;

import java.io.InputStream;

import okhttp3.OkHttpClient;

/**
 * Created by zhc on 2018/2/9 0009.
 */

public final class GlideHttpUrlLoader implements ModelLoader<GlideUrl, InputStream> {
    private OkHttpClient okHttpClient;

    public GlideHttpUrlLoader(OkHttpClient client) {
        this.okHttpClient = client;
    }

    @Override
    public DataFetcher<InputStream> getResourceFetcher(GlideUrl model, int width, int height) {
        return new GlideHttpUrlFetcher(okHttpClient, model);
    }

    public static class Factory implements ModelLoaderFactory<GlideUrl, InputStream> {
        private OkHttpClient client;

        //        public Factory(OkHttpClient client) {
//            this.client = client;
//        }
//        public Factory() {
//        }
        private synchronized OkHttpClient getOkHttpClient() {
            if (client == null) {
                client = new OkHttpClient();
            }
            return client;
        }

        @Override
        public ModelLoader<GlideUrl, InputStream> build(Context context, GenericLoaderFactory factories) {
            return new GlideHttpUrlLoader(getOkHttpClient());
        }

        @Override
        public void teardown() {

        }
    }
}
