package com.example.handlerexample;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Handler mhandler;
    private static final int MSG_UPDATE_NUMBER = 100;
    private static final int MSG_UPDATE_NUMBER_DONE = 101;

    private TextView mTextNumber;
    private boolean mIsCounting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


            initViews();
            listenerHandler();

    }

    private void listenerHandler() {

        mhandler =new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case MSG_UPDATE_NUMBER:
                        mIsCounting=true;
                        mTextNumber.setText(String.valueOf(msg.arg1));
                        break;
                    case MSG_UPDATE_NUMBER_DONE:
                        mTextNumber.setText("Done!");
                        mIsCounting = false;
                        break;
                    default:
                        break;
                }
            }
        };
    }

    private void initViews() {
        mTextNumber = findViewById(R.id.text_number);
        findViewById(R.id.button_count).setOnClickListener(this);
    }

    private void countNumbers() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 10; i++) {
                    Message message = new Message();
                    message.what = MSG_UPDATE_NUMBER;
                    message.arg1 = i;
                    mhandler.sendMessage(message);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                mhandler.sendEmptyMessage(MSG_UPDATE_NUMBER_DONE);
            }
        }).start();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_count:
                if (!mIsCounting) {
                    countNumbers();
                }
                break;
            default:
                break;
        }

    }
}
