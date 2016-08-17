package com.yx.day13_homework;

import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class SecondActivity extends AppCompatActivity {

    //1.创建通知管理器
    private NotificationManager manager;

    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        img = (ImageView) findViewById(R.id.img);
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(100);//根据通知的id 清除通知


        byte[] bytes = SDUtil.readFromSD(ImgAsync.FILE_DIR,ImgAsync.fileName);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);

        img.setImageBitmap(bitmap);
    }
}
