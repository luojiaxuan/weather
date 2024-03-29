package com.example.weather;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class SlabListActivity extends Fragment {
    private LinearLayout mFirstLinear;
    private ImageView mFirstImageView;
    private TextView mFirstDate;
    private TextView mFirstWeather;
    private TextView mFirstMax;
    private TextView mFirstMin;
    private LinearLayout mSecondLinear;
    private ImageView mSecondImageView;
    private TextView mSecondDate;
    private TextView mSecondWeather;
    private TextView mSecondMax;
    private TextView mSecondMin;
    private LinearLayout mThirdLinear;
    private ImageView mThirdImageView;
    private TextView mThirdDate;
    private TextView mThirdWeather;
    private TextView mThirdMax;
    private TextView mThirdMin;
    private LinearLayout mFourthLinear;
    private ImageView mFourthImageView;
    private TextView mFourthDate;
    private TextView mFourthWeather;
    private TextView mFourthMax;
    private TextView mFourthMin;
    private LinearLayout mFifthLinear;
    private ImageView mFifthImageView;
    private TextView mFifthDate;
    private TextView mFifthWeather;
    private TextView mFifthMax;
    private TextView mFifthMin;
    private LinearLayout mSixthLinear;
    private ImageView mSixthImageView;
    private TextView mSixthDate;
    private TextView mSixthWeather;
    private TextView mSixthMax;
    private TextView mSixthMin;
    private LinearLayout mSeventhLinear;
    private ImageView mSeventhImageView;
    private TextView mSeventhDate;
    private TextView mSeventhWeather;
    private TextView mSeventhMax;
    private TextView mSeventhMin;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_slab_list, container, false);
        //第一行列表
        ReturnData firstData = new ReturnData(0);
        final String firstPicture = firstData.getWeatIcon();
        mFirstImageView = (ImageView) view.findViewById(R.id.soneImageView);
        mFirstDate = (TextView) view.findViewById(R.id.soneDate);
        mFirstWeather = (TextView) view.findViewById(R.id.soneWeather);
        mFirstMax = (TextView) view.findViewById(R.id.soneTextMax);
        mFirstMin = (TextView) view.findViewById(R.id.soneTextMin);
        String firstDay = firstData.getDate().substring(6);
        mFirstDate.setText(firstDay);
        mFirstWeather.setText(firstData.getWeatMain());
        mFirstMax.setText(firstData.getTemp_max());
        mFirstMin.setText(firstData.getTemp_min());
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Bitmap bitmap = new GetPictureHttps().getPicture(firstPicture);
                mFirstImageView.post(new Runnable() {
                    @Override
                    public void run() {
                        mFirstImageView.setImageBitmap(bitmap);
                    }
                });
            }
        }).start();
        mFirstLinear = (LinearLayout) view.findViewById(R.id.soneLinear);
        mFirstLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("jerry");
                intent.putExtra("change","0");
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
            }
        });
        //第二行列表
        ReturnData SecondData = new ReturnData(1);
        final String SecondPicture = SecondData.getWeatIcon();
        mSecondImageView = (ImageView) view.findViewById(R.id.stwoImageView);
        mSecondDate = (TextView) view.findViewById(R.id.stwoDate);
        mSecondWeather = (TextView) view.findViewById(R.id.stwoWeather);
        mSecondMax = (TextView) view.findViewById(R.id.stwoTextMax);
        mSecondMin = (TextView) view.findViewById(R.id.stwoTextMin);
        mSecondDate.setText(SecondData.getDate());
        mSecondWeather.setText(SecondData.getWeatMain());
        mSecondMax.setText(SecondData.getTemp_max());
        mSecondMin.setText(SecondData.getTemp_min());
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Bitmap bitmap = new GetPictureHttps().getPicture(SecondPicture);
                mSecondImageView.post(new Runnable() {
                    @Override
                    public void run() {
                        mSecondImageView.setImageBitmap(bitmap);
                    }
                });
            }
        }).start();
        mSecondLinear = (LinearLayout) view.findViewById(R.id.stwoLinear);
        mSecondLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("jerry");
                intent.putExtra("change","1");
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
            }
        });
        //第三行列表
        ReturnData ThirdData = new ReturnData(2);
        final String ThirdPicture = ThirdData.getWeatIcon();
        mThirdImageView = (ImageView) view.findViewById(R.id.sthreeImageView);
        mThirdDate = (TextView) view.findViewById(R.id.sthreeDate);
        mThirdWeather = (TextView) view.findViewById(R.id.sthreeWeather);
        mThirdMax = (TextView) view.findViewById(R.id.sthreeTextMax);
        mThirdMin = (TextView) view.findViewById(R.id.sthreeTextMin);
        mThirdDate.setText(ThirdData.getDate());
        mThirdWeather.setText(ThirdData.getWeatMain());
        mThirdMax.setText(ThirdData.getTemp_max());
        mThirdMin.setText(ThirdData.getTemp_min());
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Bitmap bitmap = new GetPictureHttps().getPicture(ThirdPicture);
                mThirdImageView.post(new Runnable() {
                    @Override
                    public void run() {
                        mThirdImageView.setImageBitmap(bitmap);
                    }
                });
            }
        }).start();
        mThirdLinear = (LinearLayout) view.findViewById(R.id.sthreeLinear);
        mThirdLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("jerry");
                intent.putExtra("change","2");
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
            }
        });
        //第四行列表
        ReturnData FourthData = new ReturnData(3);
        final String FourthPicture = FourthData.getWeatIcon();
        mFourthImageView = (ImageView) view.findViewById(R.id.sfourImageView);
        mFourthDate = (TextView) view.findViewById(R.id.sfourDate);
        mFourthWeather = (TextView) view.findViewById(R.id.sfourWeather);
        mFourthMax = (TextView) view.findViewById(R.id.sfourTextMax);
        mFourthMin = (TextView) view.findViewById(R.id.sfourTextMin);
        mFourthDate.setText(FourthData.getDate());
        mFourthWeather.setText(FourthData.getWeatMain());
        mFourthMax.setText(FourthData.getTemp_max());
        mFourthMin.setText(FourthData.getTemp_min());
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Bitmap bitmap = new GetPictureHttps().getPicture(FourthPicture);
                mFourthImageView.post(new Runnable() {
                    @Override
                    public void run() {
                        mFourthImageView.setImageBitmap(bitmap);
                    }
                });
            }
        }).start();
        mFourthLinear = (LinearLayout) view.findViewById(R.id.sfourLinear);
        mFourthLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("jerry");
                intent.putExtra("change","3");
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
            }
        });
        //第五行列表
        ReturnData FifthData = new ReturnData(4);
        final String FifthPicture = FifthData.getWeatIcon();
        mFifthImageView = (ImageView) view.findViewById(R.id.sfiveImageView);
        mFifthDate = (TextView) view.findViewById(R.id.sfiveDate);
        mFifthWeather = (TextView) view.findViewById(R.id.sfiveWeather);
        mFifthMax = (TextView) view.findViewById(R.id.sfiveTextMax);
        mFifthMin = (TextView) view.findViewById(R.id.sfiveTextMin);
        mFifthDate.setText(FifthData.getDate());
        mFifthWeather.setText(FifthData.getWeatMain());
        mFifthMax.setText(FifthData.getTemp_max());
        mFifthMin.setText(FifthData.getTemp_min());
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Bitmap bitmap = new GetPictureHttps().getPicture(FifthPicture);
                mFifthImageView.post(new Runnable() {
                    @Override
                    public void run() {
                        mFifthImageView.setImageBitmap(bitmap);
                    }
                });
            }
        }).start();
        mFifthLinear = (LinearLayout) view.findViewById(R.id.sfiveLinear);
        mFifthLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("jerry");
                intent.putExtra("change","4");
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
            }
        });
        //第六行列表
        ReturnData SixthData = new ReturnData(5);
        final String SixthPicture = SixthData.getWeatIcon();
        mSixthImageView = (ImageView) view.findViewById(R.id.ssixImageView);
        mSixthDate = (TextView) view.findViewById(R.id.ssixDate);
        mSixthWeather = (TextView) view.findViewById(R.id.ssixWeather);
        mSixthMax = (TextView) view.findViewById(R.id.ssixTextMax);
        mSixthMin = (TextView) view.findViewById(R.id.ssixTextMin);
        mSixthDate.setText(SixthData.getDate());
        mSixthWeather.setText(SixthData.getWeatMain());
        mSixthMax.setText(SixthData.getTemp_max());
        mSixthMin.setText(SixthData.getTemp_min());
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Bitmap bitmap = new GetPictureHttps().getPicture(SixthPicture);
                mSixthImageView.post(new Runnable() {
                    @Override
                    public void run() {
                        mSixthImageView.setImageBitmap(bitmap);
                    }
                });
            }
        }).start();
        mSixthLinear = (LinearLayout) view.findViewById(R.id.ssixLinear);
        mSixthLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("jerry");
                intent.putExtra("change","5");
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
            }
        });
        //第七行列表
        ReturnData SeventhData = new ReturnData(6);
        final String SeventhPicture = SeventhData.getWeatIcon();
        mSeventhImageView = (ImageView) view.findViewById(R.id.ssevenImageView);
        mSeventhDate = (TextView) view.findViewById(R.id.ssevenDate);
        mSeventhWeather = (TextView) view.findViewById(R.id.ssevenWeather);
        mSeventhMax = (TextView) view.findViewById(R.id.ssevenTextMax);
        mSeventhMin = (TextView) view.findViewById(R.id.ssevenTextMin);
        mSeventhDate.setText(SeventhData.getDate());
        mSeventhWeather.setText(SeventhData.getWeatMain());
        mSeventhMax.setText(SeventhData.getTemp_max());
        mSeventhMin.setText(SeventhData.getTemp_min());
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Bitmap bitmap = new GetPictureHttps().getPicture(SeventhPicture);
                mSeventhImageView.post(new Runnable() {
                    @Override
                    public void run() {
                        mSeventhImageView.setImageBitmap(bitmap);
                    }
                });
            }
        }).start();
        mSeventhLinear = (LinearLayout) view.findViewById(R.id.ssevenLinear);
        mSeventhLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("jerry");
                intent.putExtra("change","6");
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
            }
        });
        return view;
    }
}

