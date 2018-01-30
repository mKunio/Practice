package mlearn.sabachina.com.cn.recordvideo;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void click(View view) {
//        Intent intent = new Intent();
//        intent.setAction("android.media.action.VIDEO_CAPTURE");
//        intent.addCategory("android.intent.category.DEFAULT");
//
//        // 保存录像到指定的路径
//        Uri uri = FileUtil.getTempUri();
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
////        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0.8);
//        intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 10);
//        intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 3 * 1024 * 1024L);
//        startActivityForResult(intent, 0);
    }
}
