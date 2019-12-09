package com.example.weather;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
public class SlabDetailActivity extends Fragment {
    public static int which = 0;//判断是第几天
    private String TAG = "SlabDetailActivity";
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
        //setHasOptionsMenu(true);
        registerReceiver();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_slab_detail, container, false);
        //setHasOptionsMenu(true);
        ReturnData Data = new ReturnData(which);
        final String Picture = Data.getWeatIcon();
        mImageView = (ImageView) view.findViewById(R.id.show_weather_pictures);
        mDate = (TextView) view.findViewById(R.id.showDates);
        mWeather = (TextView) view.findViewById(R.id.showWeatherMains);
        mMax = (TextView) view.findViewById(R.id.showMaxCs);
        mMin = (TextView) view.findViewById(R.id.showMinCs);
        mHumidity = (TextView) view.findViewById(R.id.showHumiditys);
        mPressure = (TextView) view.findViewById(R.id.showPressures);
        mWind = (TextView) view.findViewById(R.id.showWinds);
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
        return view;
    }
    @Override
    public void onDetach() {
        super.onDetach();
        broadcastManager.unregisterReceiver(mAdDownLoadReceiver);
    }
    /**
     * 注册广播接收器
     */
    LocalBroadcastManager broadcastManager;
    private void registerReceiver() {
        broadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("jerry");
        broadcastManager.registerReceiver(mAdDownLoadReceiver, intentFilter);
    }
    private BroadcastReceiver mAdDownLoadReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String change = intent.getStringExtra("change");
            which = Integer.valueOf(change);
            ReturnData sData = new ReturnData(which);
            final String sdate = sData.getDate();
            final String sweather = sData.getWeatMain();
            final String smax = sData.getTemp_max();
            final String smin = sData.getTemp_min();
            final String shumidity = sData.getHumidity();
            final String spressure = sData.getPressure();
            final String spicture = sData.getWeatIcon();
            final String swind = sData.getWind();
            new Handler().post(new Runnable() {
                public void run() {
                    mDate.setText(sdate);
                    mWeather.setText(sweather);
                    mMax.setText(smax);
                    mMin.setText(smin);
                    mHumidity.setText(shumidity);
                    mPressure.setText(spressure);
                    mWind.setText(swind);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            final Bitmap bitmap = new GetPictureHttps().getPicture(spicture);
                            mImageView.post(new Runnable() {
                                @Override
                                public void run() {
                                    mImageView.setImageBitmap(bitmap);
                                }
                            });
                        }
                    }).start();
                }
            });
        }
    };
}

