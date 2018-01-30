package mlearn.sabachina.com.cn.recordvideo;

import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by zhc on 2017/10/25 0025.
 */

/**
 * 此类关联与本APP所有文件夹与文件路径，所有下载存储路径，拍照等需要存储的路径都通过此类获取
 */
public class FileUtil {
    /**
     * root  folder
     */
    public static final String ROOT = "jinengzhongyou";
    /**
     * 拍照存储文件夹  images
     */
    public static final String IMAGES = "/images/";
    public static final String VIDEO = "/video/";
    /**
     * 下载的PDF文件夹  pdf
     */
    public static final String PDF = "pdf";

    /**
     * 获取一个拍照存储地址Uri
     * 存储位置：jinengzhongyou/images
     *
     * @return
     */

    public static Uri getTempUri() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File file = new File(getAppRootFile(), VIDEO + timeStamp + ".mp4");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        return Uri.fromFile(file);
    }

    /**
     * APP所有下载的文件，拍照片存储，视频存储的根文件夹
     *
     * @return 根文件夹File
     */
    public static File getAppRootFile() {
        return new File(Environment.getExternalStorageDirectory(), ROOT);
    }
}
