package com.example.weather;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
public class MainActivity extends AppCompatActivity {
    private int which;
    private static final String TAG = "MainActivity";//记录主类的日志
    private DatabaseHelper mDatabaseHelper;//SQLite实例化对象
    private static final String API_KEY = "a2b2330c385b47ac8292db7990f0f1bd";//key
    private static final String WEB_KEY = "dda27bca16444a88966cb27bffe11fb0";//web_key
    private JSONObject jsonObject;
    private String CityName="changsha";
    private boolean flagispanel=false;
    public static boolean isFirstFlag=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //创建数据库
        mDatabaseHelper = new DatabaseHelper(this,"Weather.db",null,1);
        final SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        ReturnData cns = new ReturnData(0);
        if (cns.getCityName().equals("")){
            //往数据库city表中写入初始值changsha
            isFirstFlag=true;
            ContentValues value = new ContentValues();//数据模型
            value.put("cityName", CityName);
            db.insert("city", null, value);//插入数据
        }

        //新建一个线程联网
        Thread test=new Thread(new Runnable() {//创建线程，从网上获取JSON数据
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
        });
        test.start();
        if(isFirstFlag) {
            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        isFirstFlag=false;
        //获取屏幕的尺寸，调用布局
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        //判断采用平板还是手机
        if (displayMetrics.widthPixels >= displayMetrics.heightPixels) {
            flagispanel=true;
            SlabActivity slabActivity = new SlabActivity();
            getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, slabActivity).commit();
        } else {
            flagispanel=false;
            PhoneActivity phoneActivity = new PhoneActivity();
            getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,phoneActivity).commit();
        }
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
            //抛出错误
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
    /**
     * 创建菜单
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(flagispanel){
            getMenuInflater().inflate(R.menu.share_menu, menu);
        }
        else {
            getMenuInflater().inflate(R.menu.fragment_menu, menu);
        }
        return true;
    }
    /**
     * 菜单点击事件
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(flagispanel){
            switch (item.getItemId()){
                case R.id.mapLocation:
                    Toast.makeText(this,"成功点击了，Map Location",Toast.LENGTH_SHORT).show();
                    Intent in = new Intent(MainActivity.this, com.example.weather.MapActivity.class);
                    startActivity(in);
                    break;
                case R.id.setting://启动设置页面
                    Toast.makeText(this,"成功点击了，Setting",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this,SettingActivity.class);
                    startActivity(intent);
                    break;
                case R.id.myshare://分享按钮
                    ReturnData rd=new ReturnData(SlabDetailActivity.which);
                    String sharestring = rd.getDate()+"\n"+rd.getWeatMain()+"\nMax Temp:"
                            +rd.getTemp_max()+"\nMin Temp:"+rd.getTemp_min()+"\n"+rd.getHumidity()
                            +"\n"+rd.getPressure()+"\n"+rd.getWind()+"\n";
                    Intent share_intent = new Intent();
                    share_intent.setAction(Intent.ACTION_SEND);
                    share_intent.setType("text/plain");//设置分享内容的类型
                    share_intent.putExtra(Intent.EXTRA_SUBJECT,"share");//分享标题内容
                    share_intent.putExtra(Intent.EXTRA_TEXT,sharestring);
                    share_intent = Intent.createChooser(share_intent,"share");
                    startActivity(share_intent);
                    return true;
                default:
                    break;
            }
        }
        else {
            switch (item.getItemId()) {
                case R.id.mapLocation:
                    Toast.makeText(this, "成功点击了，Map Location", Toast.LENGTH_SHORT).show();
                    Intent in = new Intent(MainActivity.this, com.example.weather.MapActivity.class);
                    startActivity(in);
                    break;
                case R.id.setting://启动设置页面
                    Toast.makeText(this, "成功点击了，Setting", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                    startActivity(intent);
                    break;
                default:
                    break;
            }
        }
        return true;
    }
}

