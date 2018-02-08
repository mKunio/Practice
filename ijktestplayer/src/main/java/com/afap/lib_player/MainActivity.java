package com.afap.lib_player;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


import player.IJKVideoPlayActivity;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class MainActivity extends AppCompatActivity {
    int MEDIA_INFO_UNKNOWN = 1;
    int MEDIA_INFO_STARTED_AS_NEXT = 2;
    int MEDIA_INFO_VIDEO_RENDERING_START = 3;
    int MEDIA_INFO_VIDEO_TRACK_LAGGING = 700;
    int MEDIA_INFO_BUFFERING_START = 701;
    int MEDIA_INFO_BUFFERING_END = 702;
    int MEDIA_INFO_NETWORK_BANDWIDTH = 703;
    int MEDIA_INFO_BAD_INTERLEAVING = 800;
    int MEDIA_INFO_NOT_SEEKABLE = 801;
    int MEDIA_INFO_METADATA_UPDATE = 802;
    int MEDIA_INFO_TIMED_TEXT_ERROR = 900;
    int MEDIA_INFO_UNSUPPORTED_SUBTITLE = 901;
    int MEDIA_INFO_SUBTITLE_TIMED_OUT = 902;
    int MEDIA_INFO_VIDEO_ROTATION_CHANGED = 10001;
    int MEDIA_INFO_AUDIO_RENDERING_START = 10002;
    int MEDIA_INFO_AUDIO_DECODED_START = 10003;
    int MEDIA_INFO_VIDEO_DECODED_START = 10004;
    int MEDIA_INFO_OPEN_INPUT = 10005;
    int MEDIA_INFO_FIND_STREAM_INFO = 10006;
    int MEDIA_INFO_COMPONENT_OPEN = 10007;
    int MEDIA_INFO_MEDIA_ACCURATE_SEEK_COMPLETE = 10100;
    int MEDIA_ERROR_UNKNOWN = 1;
    int MEDIA_ERROR_SERVER_DIED = 100;
    int MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK = 200;
    int MEDIA_ERROR_IO = -1004;
    int MEDIA_ERROR_MALFORMED = -1007;
    int MEDIA_ERROR_UNSUPPORTED = -1010;
    int MEDIA_ERROR_TIMED_OUT = -110;
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
