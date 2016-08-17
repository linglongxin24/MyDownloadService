package com.yx.day13_homework;

import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2016/7/27.
 */
public class SDUtil {

    //判断SD能否正常使用
   private static boolean isSDOK(){
       String state = Environment.getExternalStorageState();
//       if (state.equals(Environment.MEDIA_MOUNTED)){
//           return true;
//       }else {
//           return false;
//       }
       return state.equals(Environment.MEDIA_MOUNTED);
   }

    /**
     * 往SD写入
     * @param dirStr       SD卡文件夹
     * @param fileName  写入的文件名
     * @param bytes     写入的内容
     */
    public static boolean writeToSD(String dirStr,String fileName,byte[] bytes){
        if (isSDOK()){
            File fileDir = new File(Environment.getExternalStorageDirectory(),dirStr);
            if(!fileDir.exists()){//文件夹不存在就创建
                fileDir.mkdir();
            }

            File file = new File(fileDir,fileName);
            FileOutputStream fos = null;

            try {
                 fos = new FileOutputStream(file);
                 fos.write(bytes);
                return true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    if (fos != null){
                        fos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * 从SD读取
     * @param fileDir   读取文件夹
     * @param fileName  读取文件名
     * @return
     */
    public static byte[] readFromSD(String fileDir,String fileName){

        if (isSDOK()){
            String filePath = Environment.getExternalStorageDirectory().getAbsolutePath()+
                    File.separator+fileDir+File.separator+fileName;
            File file = new File(filePath);
            FileInputStream fis = null;
            ByteArrayOutputStream bos = null;

            try {
                fis = new FileInputStream(file);
                bos = new ByteArrayOutputStream();

                int len =0;
                byte[] buffer = new byte[1024*8];

                while ((len = fis.read(buffer))!=-1){
                     bos.write(buffer,0,len);
                }

                return bos.toByteArray();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    if(fis != null){
                        fis.close();
                    }
                    if (bos != null){
                        bos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

}
