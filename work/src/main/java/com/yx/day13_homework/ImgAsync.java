package com.yx.day13_homework;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

/**
 * Created by Administrator on 2016/7/27.
 */
public class ImgAsync extends AsyncTask<String,Void,Bitmap> {

    public static final String FILE_DIR = "ImgDir";
    public static String fileName;

    private CallBack callBack;

    public ImgAsync(CallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        String path = params[0];//要下载的图片地址
        String[] split = path.split("/");
        //获取图片地址/的最后一段作为文件名
        fileName = split[split.length-1];
        byte[] bytes = new NetWorkUtil(new NetWorkUtil.CallBackProgress() {
            @Override
            public void getProgress(int progress) {
                callBack.getProgress(progress);
            }
        }).getBytes(path);
        //做缓存
        if(bytes != null){
           SDUtil.writeToSD(FILE_DIR, fileName,bytes);
            return BitmapFactory.decodeByteArray(bytes,0,bytes.length);

        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);

    }

    //回调
    interface CallBack{
        public void getProgress(int progress);
    }
}
