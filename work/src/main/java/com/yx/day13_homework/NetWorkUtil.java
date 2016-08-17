package com.yx.day13_homework;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2016/7/27.
 */
public class NetWorkUtil {
    CallBackProgress callBackProgress;

    public NetWorkUtil(CallBackProgress callBackProgress) {
        this.callBackProgress = callBackProgress;
    }

    public  byte[] getBytes(String path){
        InputStream is = null;
        ByteArrayOutputStream bos = null;
        try {
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(3000);

            if(connection.getResponseCode() == 200){
                is = connection.getInputStream();
                 bos = new ByteArrayOutputStream();

                int len = 0;
                byte[] buffer = new byte[1024];

                int currProgress = 0;//当前进度
                int totalProgress = connection.getContentLength();
                while ((len = is.read(buffer))!= -1){
                    bos.write(buffer,0,len);
                    currProgress += len;
                    int progress = (int) (currProgress*100.0/totalProgress);
                    callBackProgress.getProgress(progress);
                }
                return bos.toByteArray();

            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (is != null){

                    is.close();
                }
                if (bos != null){
                    bos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    //回调
    interface CallBackProgress{
        public void getProgress(int progress);
    }

}
