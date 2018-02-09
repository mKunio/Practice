package mlearn.sabachina.com.cn.okhttp.glide;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

import net.dxtek.haoyixue.ecp.android.R;

/**
 * Created by zhc on 2017/9/5 0005.
 */

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context).load(path).centerCrop().error(R.drawable.default_cover).into(imageView);
    }
}
