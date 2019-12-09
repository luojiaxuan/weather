package com.example.weather;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
public class GetPictureHttps {
    /*
     * 功能:根据网址获取图片对应的Bitmap对象
     * @param picture
     * @return Bitmap
     * */
    public Bitmap getPicture(String picture){
        Bitmap bm=null;
        URL url;
        try {
            url = new URL("https://cdn.heweather.com/cond_icon/"+picture+".png");//创建URL对象
            URLConnection conn=url.openConnection();//获取URL对象对应的连接
            conn.connect();//打开连接
            InputStream is=conn.getInputStream();//获取输入流对象
            bm= BitmapFactory.decodeStream(is);//根据输入流对象创建Bitmap对象
        } catch (MalformedURLException e1) {
            e1.printStackTrace();//输出异常信息
        }catch (IOException e) {
            e.printStackTrace();//输出异常信息
        }
        return bm;
    }
}

