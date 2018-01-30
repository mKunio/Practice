package mlearn.sabachina.com.cn.vitamio;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import io.vov.vitamio.utils.StringUtils;

/**
 * Created by zhc on 2017/9/27 0027.
 */

public class CustomMediaController extends Controller {
    //控制提示窗口的显示
    private static final int HIDEFRAM = 0;
    private GestureDetector mGestureDetector;
    //返回按钮
    private ImageView img_back;
    //文件名
    private TextView mFileName;
    private VideoView videoView;
    private Activity activity;
    private Context context;
    //视频名称
    private String videoname;
    //设置mediaController高度为了使横屏时top显示在屏幕顶端
    private int controllerWidth = 0;

    private View mVolumeBrightnessLayout;
    //提示窗口
    private ImageView mOperationBg;
    //提示图片
    private TextView mOperationTv;
    //提示文字
    private AudioManager mAudioManager;
    //最大声音
    private int mMaxVolume;
    // 当前声音
    private int mVolume = -1;
    //当前亮度
    private float mBrightness = -1f;
    //全屏图标
    private ImageView fullScreen;
    private FullScreenListener mFullScreenListener;
    //水平滑动显示的进度布局
    private View forwardLayout;
    //水平滑动进度提示
    private TextView markTv;
    //水平滑动是否需要更新进度开关
    boolean needChangeVideoProcess;
    //水平滑动记录滑动最新的视频时间
    private long scrollVideoPosition;

    public void setFullScreenListener(FullScreenListener listener) {
        this.mFullScreenListener = listener;
    }

    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HIDEFRAM:
                    //隐藏提示窗口
                    mVolumeBrightnessLayout.setVisibility(View.GONE);
                    forwardLayout.setVisibility(View.GONE);
                    break;
            }
        }
    };

    //videoview 用于对视频进行控制的等，activity为了退出
    public CustomMediaController(Context context, VideoView videoView, Activity activity) {
        super(context);
        this.context = context;
        this.videoView = videoView;
        this.activity = activity;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        controllerWidth = wm.getDefaultDisplay().getWidth();
        mGestureDetector = new GestureDetector(context, new MyGestureListener());
    }

    @Override
    protected View makeControllerView() {
        //此处的mymediacontroller为我们自定义控制器的布局文件名称
        View v = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.mymediacontroller, this);
        v.setMinimumHeight(controllerWidth);
        //获取控件
        img_back = v.findViewById(R.id.mediacontroller_top_back);
        mFileName = (TextView) v.findViewById(R.id.mediacontroller_filename);
        if (mFileName != null) {
            mFileName.setText(videoname);
        }
        mVolumeBrightnessLayout = v.findViewById(R.id.operation_volume_brightness);
        mOperationBg = (ImageView) v.findViewById(R.id.operation_bg);
        mOperationTv = (TextView) v.findViewById(R.id.operation_tv);
        //声音控制
        mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        mMaxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        //水平滑动
        forwardLayout = v.findViewById(R.id.operation_Forward_brightness);
        markTv = v.findViewById(R.id.mediacontroller_process_mark);
        //注册事件监听
        img_back.setOnClickListener(backListener);
        fullScreen = v.findViewById(R.id.full);
        fullScreen.setOnClickListener(fullScreenListener);
        return v;
    }

    //返回监听
    private View.OnClickListener backListener = new View.OnClickListener() {
        public void onClick(View v) {
            if (activity != null) {
                activity.onBackPressed();
            }
        }
    };
    //全屏监听
    private View.OnClickListener fullScreenListener = new View.OnClickListener() {
        public void onClick(View v) {
            if (mFullScreenListener != null) {
                mFullScreenListener.dealWithFullScreen(fullScreen);
            }
        }
    };

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        System.out.println("MYApp-MyMediaController-dispatchKeyEvent");
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mGestureDetector.onTouchEvent(event))
            return true;
        // 处理手势结束
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                endGesture();
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 手势结束
     */
    private void endGesture() {
        mVolume = -1;
        mBrightness = -1f;
        //隐藏
        myHandler.removeMessages(HIDEFRAM);
        myHandler.sendEmptyMessageDelayed(HIDEFRAM, 500);
        //如果是水平滑动进度结束，就切到对应进度播放
        if (needChangeVideoProcess) {
            videoView.seekTo(scrollVideoPosition);
            needChangeVideoProcess = false;
        }
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        /**
         * 因为使用的是自定义的mediaController 当显示后，mediaController会铺满屏幕，
         * 所以VideoView的点击事件会被拦截，所以重写控制器的手势事件，
         * 将全部的操作全部写在控制器中，
         * 因为点击事件被控制器拦截，无法传递到下层的VideoView，
         * 所以原来的单击隐藏会失效，作为代替，
         * 在手势监听中onSingleTapConfirmed（）添加自定义的隐藏/显示，
         *
         * @param e
         * @return
         */
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            //当手势结束，并且是单击结束时，控制器隐藏/显示
            toggleMediaControlsVisiblity();
            return super.onSingleTapConfirmed(e);
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        //滑动事件监听
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            float mOldX = e1.getX(), mOldY = e1.getY();
            int y = (int) e2.getY();
            int x = (int) e2.getX();
            Display disp = activity.getWindowManager().getDefaultDisplay();
            int windowWidth = disp.getWidth();
            int windowHeight = disp.getHeight();
            if (mOldX > windowWidth * 4.0 / 5.0) {
                //右边滑动 超过屏幕右侧3/4
                onVolumeSlide((mOldY - y) / windowHeight);
            } else if (mOldX < windowWidth * 1.0 / 5.0) {
                //左边滑动 不超过屏幕左侧1/4
                onBrightnessSlide((mOldY - y) / windowHeight);
            } else {
                //水平滑动
                onHorizontalSlide((mOldX - x) / windowWidth);
            }
            Log.d("onScroll", "onScrollX: " + (mOldX - x));
            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            playOrPause();
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }

    /**
     * 滑动改变声音大小
     *
     * @param percent
     */
    private void onVolumeSlide(float percent) {
        //如果是竖屏小屏幕播放，放弃事件
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            return;
        }
        if (mVolume == -1) {
            mVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            if (mVolume < 0)
                mVolume = 0;
            // 显示
            mVolumeBrightnessLayout.setVisibility(View.VISIBLE);
        }
        int index = (int) (percent * mMaxVolume) + mVolume;
        if (index > mMaxVolume) {
            index = mMaxVolume;
        } else if (index < 0) {
            index = 0;
        }
        //变更声音
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, index, 0);
        mOperationBg.setImageResource(R.mipmap.voice);
        mOperationTv.setText((int) (((double) index / mMaxVolume) * 100) + "%");
    }

    /**
     * 滑动改变亮度
     *
     * @param percent
     */
    private void onBrightnessSlide(float percent) {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            return;
        }
        Window window = activity.getWindow();
        if (mBrightness < 0) {
            mBrightness = window.getAttributes().screenBrightness;
            if (mBrightness <= 0.00f)
                mBrightness = 0.50f;
            if (mBrightness < 0.01f)
                mBrightness = 0.01f;
            mVolumeBrightnessLayout.setVisibility(View.VISIBLE);
        }
        WindowManager.LayoutParams lpa = window.getAttributes();
        lpa.screenBrightness = mBrightness + percent;
        if (lpa.screenBrightness > 1.0f) {
            lpa.screenBrightness = 1.0f;
        } else if (lpa.screenBrightness < 0.01f) {
            lpa.screenBrightness = 0.01f;
        }
        //变更亮度
        mOperationBg.setImageResource(R.mipmap.light);
        mOperationTv.setText((int) (lpa.screenBrightness * 100) + "%");
        window.setAttributes(lpa);
    }

    //水平滑动
    private void onHorizontalSlide(float percent) {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            return;
        }
        if (percent > 1) {
            return;
        }
        long duration = videoView.getDuration();
        long currentTime = videoView.getCurrentPosition();
        float time = duration * percent;
        long round = Math.round(Math.abs(time));
        long preTime;
        if (time > 0) {
            preTime = currentTime - round;
            if (preTime < 0) {
                preTime = 0;
            }
            markTv.setText(StringUtils.generateTime(preTime));
        } else {
            preTime = currentTime + round;
            if (preTime > duration) {
                preTime = duration;
            }
            markTv.setText(StringUtils.generateTime(preTime));
        }
        forwardLayout.setVisibility(VISIBLE);
        scrollVideoPosition = preTime;
        needChangeVideoProcess = true;
    }

    /**
     * 设置视频文件名
     *
     * @param name
     */
    public void setVideoName(String name) {
        videoname = name;
        if (mFileName != null) {
            mFileName.setText(name);
        }
    }

    /**
     * 隐藏或显示
     */
    private void toggleMediaControlsVisiblity() {
        if (isShowing()) {
            hide();
        } else {
            show();
        }
    }

    /**
     * 播放/暂停
     */
    private void playOrPause() {
        if (videoView != null)
            if (videoView.isPlaying()) {
                videoView.pause();
            } else {
                videoView.start();
            }
    }

    @Override
    public void controllerHeightChanged(boolean isFullScreen) {
        super.controllerHeightChanged(isFullScreen);
        if (!isFullScreen) {
            fullScreen.setImageResource(R.drawable.full_screen);
        }
    }

    interface FullScreenListener {
        void dealWithFullScreen(ImageView view);
    }
}