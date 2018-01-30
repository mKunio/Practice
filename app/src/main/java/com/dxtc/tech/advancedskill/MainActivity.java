package com.dxtc.tech.advancedskill;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    public TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setStatusBarColor(Color.BLACK);
        textView = (TextView) findViewById(R.id.tv);
        textView.setText(getCurrentTimes());
        Message message = Message.obtain();
        message.what = 1;
        handler.sendMessageDelayed(message, 1000);
    }

    private String getCurrentTimes() {
        Date date = new Date();
        String timeFormat = "yyyy-MM-dd  HH:mm:ss";
        DateFormat dateFormat = new SimpleDateFormat(timeFormat, Locale.getDefault());
        return dateFormat.format(date);
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    textView.setText(getCurrentTimes());
                    Message message = Message.obtain();
                    message.what = 1;
                    handler.sendMessageDelayed(message, 1000);
                    break;
            }
            return false;
        }
    });

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
