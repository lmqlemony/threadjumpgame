package com.lmqlemony.threadjumpgame;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    int count;
    private Integer k, g, x, y, btn,l,m,q;
    private Button button;
    private View bg;
    Vibrator vibrator;
    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x0829) {
                Random random = new Random();
                l = (int) random.nextInt(256);
                m = (int) random.nextInt(256);
                q = (int) random.nextInt(256);
                button.setBackgroundColor(Color.rgb(l,m,q));
                button.setTextColor(Color.rgb(255-l,255-m,255-q));
                bg.setBackgroundColor(Color.rgb(255-l,255-m,255-q));
                x = (int) (Math.random() * (k - btn));
                y = (int) (Math.random() * (g - btn));
                button.setX(x);
                button.setY(y);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        bg = findViewById(R.id.bg);
        vibrator=(Vibrator) getSystemService(VIBRATOR_SERVICE);

        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        k = metric.widthPixels;     // 屏幕宽度（像素）
        g = metric.heightPixels;   // 屏幕高度（像素）
        btn = DensityUtil.dip2px(this,80);
        count = 0;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                button.setText(""+count);
                vibrator.vibrate(200);
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        Thread.sleep(1000);
                        handler.obtainMessage(0x0829).sendToTarget();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}

class DensityUtil {
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}