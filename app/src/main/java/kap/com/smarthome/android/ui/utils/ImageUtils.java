package kap.com.smarthome.android.ui.utils;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Administrator on 2017/8/23 0023.
 */

public class ImageUtils {
    /**
     * Uri转换成string路径
     * @param uri
     * @return
     */
    public static String getPath(Activity activity, Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA };
        Cursor cursor = activity.managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String path  = cursor.getString(column_index);
        cursor.close();
        return path;
    }


    /**
     * 路径生成图片
     * @param path
     * @return
     */
    public final static Bitmap lessenUriImage(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bitmap1 = BitmapFactory.decodeFile(path, options);
        //此时返回 bm 为空
        options.inJustDecodeBounds = false; //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = (int)(options.outHeight / (float)320);
        if (be <= 0)
            be = 1;
        options.inSampleSize = be;
        //重新读入图片，注意此时已经把options.inJustDecodeBounds设回false了
        bitmap1=BitmapFactory.decodeFile(path , options);
        int w = bitmap1.getWidth();
        int h = bitmap1.getHeight();
        System.out.println(w+" "+h); //after zoom
        return bitmap1;
    }


    /**
     * 从本地获取图片
     * @param pathString 文件路径
     * @return 图片
     */
    public static Bitmap getDiskHeadBitmap(String pathString){
        Bitmap bitmap = null;
        try {
            File file = new File(pathString);
            if(file.exists()) {
                bitmap = BitmapFactory.decodeFile(pathString);
            }
        }catch (Exception e) {
            // TODO: handle exception
        }
        return bitmap;
    }


    /**
     * 保存头像图片
     * @param bm
     * @throws IOException
     */
    public static File saveFile(Bitmap bm) {
        String path = Environment.getExternalStorageDirectory().toString()+"/kotihome/";
        File dirFile = new File(path);
        if(!dirFile.exists()){
            dirFile.mkdirs();
        }
        File myIconFile= new File(path + "user_head.jpg");
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(myIconFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if(bm != null)
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        try {
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return myIconFile;
    }


    /**
     * 保存新的头像图片
     * @param bm
     * @throws IOException
     */
    public static File saveNewHeadImage(Bitmap bm) {
        String path = Environment.getExternalStorageDirectory().toString()+"/kotihome/";
        File dirFile = new File(path);
        if(!dirFile.exists()){
            dirFile.mkdirs();
        }
        File myIconFile= new File(path + "user_new_head.jpg");
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(myIconFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if(bm != null)
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        try {
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return myIconFile;
    }


    /**
     * 将图片转换成Base64编码的字符串
     * @param path
     * @return base64编码的字符串
     */
    public static String imageToBase64(String path){
        if(TextUtils.isEmpty(path)){
            return null;
        }
        InputStream is = null;
        byte[] data = null;
        String result = null;
        try{
            is = new FileInputStream(path);
            //创建一个字符流大小的数组。
            data = new byte[is.available()];
            //写入数组
            is.read(data);
            //用默认的编码格式进行编码
            result = Base64.encodeToString(data,Base64.DEFAULT);
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(null !=is){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return result;
    }


    /**
     * base64编码字符集转化成图片文件。
     * @param base64Str
     * @param path 文件存储路径
     * @return 是否成功
     */
    public static boolean base64ToFile(String base64Str,String path){
        byte[] data = Base64.decode(base64Str,Base64.DEFAULT);
        for (int i = 0; i < data.length; i++) {
            if(data[i] < 0){
                //调整异常数据
                data[i] += 256;
            }
        }
        OutputStream os = null;
        try {
            os = new FileOutputStream(path);
            os.write(data);
            os.flush();
            os.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }

    }

}
