package com.example.weather;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
/**
 * ReturnData类
 */
public class ReturnData {
    private static final String TAG = "ReturnData";//日志
    private int Which = 0;//第几行数据
    private String ctiyName = "";//日期
    private String date = "2019-01-01";//日期
    private static Double temp_max = 15.0;//最高温度
    private static Double temp_min = 15.0;//最高温度
    private Double pressure = 1000.0;//气压
    private Double humidity = 30.0;//湿度
    private String weatMain = "Rain";//天气状况
    private String weatIcon = "305";//图标
    private Double wind = 3.0;//风速
    private static boolean isCnotF = true;
    public String getCityName() {
        return ctiyName;
    }
    public static boolean isC(){
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase("/data/data/com.example.weather/databases/Weather.db",null);
        Cursor cursor = db.query("Weather",null,null,null,null,null,null,null);
        cursor.moveToLast();
        if(Math.abs(cursor.getDouble(cursor.getColumnIndex("maxC")))<temp_max){
            //说明是华氏度
            return false;
        }
        return true;
    }
    public static void setIsCnotF(boolean cf){
        isCnotF = cf;
    }
    /**
     * 获得城市信息与天气信息
     * @param which
     */
    public ReturnData(int which) {
        Which = which;
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase("/data/data/com.example.weather/databases/Weather.db",null);
        //获取城市信息：
        Cursor cn = db.query("city",null,null,null,null,null,null,null);
        if (cn.moveToFirst()) {//如果存在数据
            cn.moveToLast();
            ctiyName = cn.getString(cn.getColumnIndex("cityName"));
            cn.close();
        }
        //获取天气信息：
        Cursor cursor = db.query("Weather",null,null,null,null,null,null,null);
        if (cursor.moveToFirst()) {//如果数据存在
            cursor.move(Which);
            date = cursor.getString(cursor.getColumnIndex("date"));
            if(isCnotF){
                temp_max = cursor.getDouble(cursor.getColumnIndex("maxC"));
                temp_min = cursor.getDouble(cursor.getColumnIndex("minC"));
            } else {
                temp_max = cursor.getDouble(cursor.getColumnIndex("maxC"))*1.8+32;
                temp_min = cursor.getDouble(cursor.getColumnIndex("minC"))*1.8+32;
            }
            pressure = cursor.getDouble(cursor.getColumnIndex("pressure"));
            humidity = cursor.getDouble(cursor.getColumnIndex("humidity"));
            weatMain = cursor.getString(cursor.getColumnIndex("weather"));
            weatIcon = cursor.getString(cursor.getColumnIndex("picture"));
            wind = cursor.getDouble(cursor.getColumnIndex("wind"));
            cursor.close();
        }
    }
    public String  getTemp_max() {
        int temp = (new Double(temp_max)).intValue();
        String tempS = String.valueOf(temp)+"°";
        return tempS;
    }
    public String getTemp_min() {
        int temp = (new Double(temp_min)).intValue();
        String tempS = " "+String.valueOf(temp)+"°";
        return tempS;
    }
    public String getPressure() {
        String temp = "Pressure: "+String.valueOf(pressure)+"hPa";
        return temp;
    }
    public String getHumidity() {
        String temp = "Humidity: "+String.valueOf(humidity)+"%";
        return temp;
    }
    public String getWeatMain() {
        return weatMain;
    }
    public String getWeatIcon() {
        //       weatIcon = "305";//测试
        return weatIcon;
    }
    public String getWind() {
        String temp = "Wind: "+String.valueOf(wind)+"km/h SE";
        return temp;
    }
    /**
     * 日期格式转化
     * @return String
     */
    public String getDate() {
        String returnDateString = null;
        String month = date.substring(5,7);
        String day = date.substring(8,10);
        int m = Integer.valueOf(month).intValue();
        int d = Integer.valueOf(day).intValue();
        Log.i("date:",date);
        Log.i("month:",month);
        Log.i("day:",day);
        switch (m){
            case 1:returnDateString="Jan";break;
            case 2:returnDateString="Feb";break;
            case 3:returnDateString="Mar";break;
            case 4:returnDateString="Apr";break;
            case 5:returnDateString="May";break;
            case 6:returnDateString="Jun";break;
            case 7:returnDateString="Jul";break;
            case 8:returnDateString="Aug";break;
            case 9:returnDateString="Sept";break;
            case 10:returnDateString="Oct";break;
            case 11:returnDateString="Nov";break;
            case 12:returnDateString="Dec";break;
        }
        if (Which == 0) {
            returnDateString = "Today,"+returnDateString+" "+Integer.toString(d);
        } else {
            returnDateString = returnDateString+" "+Integer.toString(d);
        }
        return returnDateString;
    }
}

