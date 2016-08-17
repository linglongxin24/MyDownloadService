package com.yx.day13_homework;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private String path = "http://img1.3lian.com/2015/w7/98/d/22.jpg";
    //private String path = "http://www.bz55.com/uploads1/allimg/120312/1_120312100435_8.jpg";
    //1.创建通知管理器
    private NotificationManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public void onDownLoadClick(View view) {
        //2.通知构造器
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        //3.设置通知 三要素  标题、小图标、内容
        builder.setContentTitle("下载中...")
                .setSmallIcon(R.mipmap.ic_launcher);


        //开启异步任务
        new ImgAsync(new ImgAsync.CallBack() {
            @Override
            public void getProgress(int progress) {

                builder.setContentText("下载了" + progress + "%");
                builder.setProgress(100, progress, false);
                if (progress == 100) {
                    builder.setContentTitle("点击查看")
                            .setContentText("下载完成");
                    //点击通知  跳转  封装跳转意图  PendingIntent.FLAG_ONE_SHOT有效点击次数一次
                    Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                    PendingIntent pendingIntent =
                            PendingIntent.getActivity(MainActivity.this, 100, intent, PendingIntent.FLAG_ONE_SHOT);
                    builder.setContentIntent(pendingIntent);
                }
                //发送通知
                manager.notify(100, builder.build());
            }
        }).execute(path);


    }
}
