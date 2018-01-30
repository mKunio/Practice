package com.afap.lib_player;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


import player.IJKVideoPlayActivity;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 String testurl = "http://flashmedia.eastday.com/newdate/news/2016-11/shznews1125-19.mp4";

//                Intent intent = new Intent(MainActivity.this, VideoPlayActivity.class);
//                intent.putExtra("url", testurl);
//                startActivity(intent);


//                Uri uri = Uri.parse(testurl);
//                // 让系统选择播放器来播放流媒体视频
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setDataAndType(uri, "video/*");
//                startActivity(intent);

                IJKVideoPlayActivity.intentTo(MainActivity.this, testurl, "测试视频");
            }
        });


    }
}
