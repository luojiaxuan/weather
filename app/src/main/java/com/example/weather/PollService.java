package com.example.weather;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
public class PollService extends IntentService {
    public static final String TAG ="PollService";
    private String CityName = "changsha";
    private static final String API_KEY = "a2b2330c385b47ac8292db7990f0f1bd";//key
    private static final String WEB_KEY = "dda27bca16444a88966cb27bffe11fb0";//web_key
    private JSONObject jsonObject;
    public PollService() {
        super(TAG);
    }
    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        //判断网络是否可用：
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(cm == null)return;
        if(cm.getActiveNetworkInfo() == null && !cm.getActiveNetworkInfo().isAvailable())return;
        Log.i(TAG,"receive an intent:"+intent);
        final SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase("/data/data/com.example.weather/databases/Weather.db",null);
        //再次联网获取数据
        new Thread(new Runnable() {//创建线程，从网上获取JSON数据
            @Override
            public void run() {
                try {
                    //获取当前城市:
                    ReturnData cn = new ReturnData(0);
                    if (!cn.getCityName().equals("")){
                        CityName = cn.getCityName();
                    }
                    Log.i(TAG,CityName);//打印出城市名
                    //调用和风天气API
                    String url = Uri.parse("https://free-api.heweather.net/s6/weather/forecast?")
                            .buildUpon()
                            .appendQueryParameter("location", CityName)
                            .appendQueryParameter("key", WEB_KEY)
                            .build().toString();
                    String jsonString = getUrlString(url);//通过url获得json数据字符串
                    Log.i(TAG, "received json:" + jsonString);
                    jsonObject = new JSONObject(jsonString);//获取json对象
                    //解析JSON数据及对数据库的操作
                    db.delete("weather", null, null);//清空weather表数据
                    ContentValues values = new ContentValues();//数据模型
                    //数据定义
                    String cityName;//城市
                    Double temp_max;//最高温度
                    Double temp_min;//最高温度
                    Double pressure;//气压
                    Double humidity;//湿度
                    String weatxtd;//白天天气状况
                    String weatIcon;//图标
                    String date;//日期
                    String oldDate = null;
                    JSONObject HeWeather6 = jsonObject.getJSONArray("HeWeather6").getJSONObject(0);
                    JSONObject basic = HeWeather6.getJSONObject("basic");
                    cityName = basic.getString("location");//城市
                    Log.i(TAG, "cityName:" + cityName);
                    JSONArray listdays = HeWeather6.getJSONArray("daily_forecast");
                    for (int i = 0; i < listdays.length(); i++) {
                        JSONObject iday = listdays.getJSONObject(i);
                        temp_max = iday.getDouble("tmp_max");//当天最高温
                        temp_min = iday.getDouble("tmp_min");//当天最低温
                        pressure = iday.getDouble("pres");//气压
                        humidity = iday.getDouble("hum");//湿度
                        weatxtd = iday.getString("cond_txt_d");//白天天气状况
                        weatIcon = iday.getString("cond_code_d");//获得天气图标对应的编号
                        Double windspeed = iday.getDouble("wind_spd");//风速
                        date = iday.getString("date");//时间
                        if (!date.equals(oldDate)) {//只保存第一次数据
                            Log.i(TAG, "date:" + date);
                            values.put("date", date);
                            values.put("weather", weatxtd);
                            values.put("picture", weatIcon);
                            values.put("maxC", temp_max);
                            values.put("minC", temp_min);
                            values.put("humidity", humidity);
                            values.put("pressure", pressure);
                            values.put("wind", windspeed);
                            db.insert("weather", null, values);
                            oldDate = date;//保存时间
                            values.clear();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        ReturnData notic = new ReturnData(0);
        String NoticDate = notic.getDate();
        String NoticWeather = notic.getWeatMain();
        //通知信息：
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String channelId = "app1";
        NotificationChannel channel = new NotificationChannel(channelId,"app1",NotificationManager.IMPORTANCE_DEFAULT);
        notificationManager.createNotificationChannel(channel);
        Intent intent1 = new Intent(PollService.this,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(PollService.this, 0 ,intent1,PendingIntent.FLAG_CANCEL_CURRENT);
        Notification.Builder builder = new Notification.Builder(PollService.this);
        builder.setContentTitle("Today's weather");
        String sharestring = notic.getDate()+"\n天气："+notic.getWeatMain()+"\n最高温度:"
                +notic.getTemp_max()+"\n最低温度:"+notic.getTemp_min()+"\n风力："+notic.getWind()+"\n";
        if(notic.getWeatMain().contains("雨")){
            sharestring="今天有雨，请注意带伞哦！"+sharestring;
        }
        else if(notic.getWeatMain().contains("晴")||notic.getWeatMain().contains("阴")){
            sharestring="今天天气不错呢！"+sharestring;
        }
        builder.setContentText(sharestring);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setChannelId(channelId);
        builder.setWhen(System.currentTimeMillis());
        builder.setContentIntent(pendingIntent);//点击跳转到主页面
        builder.setAutoCancel(true);//点击后消失
        Notification notification = builder.build();
        notificationManager.notify(1,notification);
    }
    /**
     * 延迟运行服务
     */
    public static void setServiceAlarm(Context context, boolean isOn) {
         Intent i = new Intent(context,PollService.class);
         PendingIntent pendingIntent = PendingIntent.getService(context, 0, i, 0);
         AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
         if(isOn) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(), 300,pendingIntent);
         } else {
            alarmManager.cancel(pendingIntent);
            pendingIntent.cancel();
         }
    }
    /**
     * 查看定时器的状态
     */
    public static boolean isServiceAlarmOn(Context context) {
        Intent in = new Intent(context,PollService.class);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, in, PendingIntent.FLAG_NO_CREATE);
        return pendingIntent != null;
    }
    /**
     * getUrlBytes(String)：从指定URL获取字节流数组
     */
    public byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
            if(connection.getResponseCode() != HttpURLConnection.HTTP_OK){
                throw new IOException(connection.getResponseMessage()+":with "+urlSpec);
            }
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }
    /**
     * getUrlString(String)：将字节流数据转化为String
     */
    public String getUrlString(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }
}

