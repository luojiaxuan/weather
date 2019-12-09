package com.example.weather;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
public class MorePhoneDetailActivity extends AppCompatActivity {
    private int which;//判断是第几天
    private ImageView mImageView;
    private TextView mDate;
    private TextView mWeather;
    private TextView mMax;
    private TextView mMin;
    private TextView mHumidity;
    private TextView mPressure;
    private TextView mWind;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_detail);
        which = getIntent().getIntExtra("isWhichDate",0);
        ReturnData Data = new ReturnData(which);
        final String Picture = Data.getWeatIcon();
        mImageView = (ImageView) findViewById(R.id.show_weather_picture);
        mDate = (TextView) findViewById(R.id.showDate);
        mWeather = (TextView) findViewById(R.id.showWeatherMain);
        mMax = (TextView) findViewById(R.id.showMaxC);
        mMin = (TextView) findViewById(R.id.showMinC);
        mHumidity = (TextView) findViewById(R.id.showHumidity);
        mPressure = (TextView) findViewById(R.id.showPressure);
        mWind = (TextView) findViewById(R.id.showWind);
        mDate.setText(Data.getDate());
        mWeather.setText(Data.getWeatMain());
        mMax.setText(Data.getTemp_max());
        mMin.setText(Data.getTemp_min());
        mHumidity.setText(Data.getHumidity());
        mPressure.setText(Data.getPressure());
        mWind.setText(Data.getWind());
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Bitmap bitmap = new GetPictureHttps().getPicture(Picture);
                mImageView.post(new Runnable() {
                    @Override
                    public void run() {
                        mImageView.setImageBitmap(bitmap);
                    }
                });
            }
        }).start();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //使用菜单填充器获取menu下的菜单资源文件
        getMenuInflater().inflate(R.menu.share_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id =item.getItemId();
        if(id==R.id.myshare){
            String sharestring = mDate.getText().toString()+"\n"+mWeather.getText().toString()+"\nMax Temp:"
                    +mMax.getText().toString()+"\nMin Temp:"+mMin.getText().toString()+"\n"+mHumidity.getText().toString()
                    +"\n"+mPressure.getText().toString()+"\n"+mWind.getText().toString()+"\n";
            Intent share_intent = new Intent();
            share_intent.setAction(Intent.ACTION_SEND);
            share_intent.setType("text/plain");//设置分享内容的类型
            share_intent.putExtra(Intent.EXTRA_SUBJECT,"share");//分享标题内容
            share_intent.putExtra(Intent.EXTRA_TEXT,sharestring);
            share_intent = Intent.createChooser(share_intent,"share");
            startActivity(share_intent);
            return true;
        }
        else if(id==R.id.mapLocation){
            Intent in = new Intent(MorePhoneDetailActivity.this, com.example.weather.MapActivity.class);
            startActivity(in);
        }
        else if(id==R.id.setting){
            Intent intent = new Intent(MorePhoneDetailActivity.this,SettingActivity.class);
            startActivity(intent);
        }
        else{
                //对没有处理的事件，交给父类来处理
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}

