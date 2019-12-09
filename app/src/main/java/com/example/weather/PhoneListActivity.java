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
public class PhoneListActivity extends Fragment {
    private static final String TAG = "PhoneListActivity";//日志
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
    private LinearLayout mFiveLinear;
    private ImageView mFiveImageView;
    private TextView mFiveDate;
    private TextView mFiveWeather;
    private TextView mFiveMax;
    private TextView mFiveMin;
    private LinearLayout mSixLinear;
    private ImageView mSixImageView;
    private TextView mSixDate;
    private TextView mSixWeather;
    private TextView mSixMax;
    private TextView mSixMin;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_phone_list, container, false);
        //第一行列表
        ReturnData firstData = new ReturnData(1);
        final String firstPicture = firstData.getWeatIcon();
        mFirstImageView = (ImageView) view.findViewById(R.id.oneImageView);
        mFirstDate = (TextView) view.findViewById(R.id.oneDate);
        mFirstWeather = (TextView) view.findViewById(R.id.oneWeather);
        mFirstMax = (TextView) view.findViewById(R.id.oneTextMax);
        mFirstMin = (TextView) view.findViewById(R.id.oneTextMin);
        mFirstDate.setText(firstData.getDate());
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
        mFirstLinear = (LinearLayout) view.findViewById(R.id.oneLinear);
        mFirstLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),MorePhoneDetailActivity.class);
                intent.putExtra("isWhichDate",1);
                startActivity(intent);
            }
        });
        //第二行列表：
        ReturnData SecondData = new ReturnData(2);
        final String secondPicture = SecondData.getWeatIcon();
        mSecondImageView = (ImageView) view.findViewById(R.id.twoImageView);
        mSecondDate = (TextView) view.findViewById(R.id.twoDate);
        mSecondWeather = (TextView) view.findViewById(R.id.twoWeather);
        mSecondMax = (TextView) view.findViewById(R.id.twoTextMax);
        mSecondMin = (TextView) view.findViewById(R.id.twoTextMin);
        mSecondDate.setText(SecondData.getDate());
        mSecondWeather.setText(SecondData.getWeatMain());
        mSecondMax.setText(SecondData.getTemp_max());
        mSecondMin.setText(SecondData.getTemp_min());
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Bitmap bitmap = new GetPictureHttps().getPicture(secondPicture);
                mSecondImageView.post(new Runnable() {
                    @Override
                    public void run() {
                        mSecondImageView.setImageBitmap(bitmap);
                    }
                });
            }
        }).start();
        mSecondLinear = (LinearLayout) view.findViewById(R.id.twoLinear);
        mSecondLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),MorePhoneDetailActivity.class);
                intent.putExtra("isWhichDate",2);
                startActivity(intent);
            }
        });
        //第三行列表：
        ReturnData ThirdData = new ReturnData(3);
        final String thirdPicture = ThirdData.getWeatIcon();
        mThirdImageView = (ImageView) view.findViewById(R.id.threeImageView);
        mThirdDate = (TextView) view.findViewById(R.id.threeDate);
        mThirdWeather = (TextView) view.findViewById(R.id.threeWeather);
        mThirdMax = (TextView) view.findViewById(R.id.threeTextMax);
        mThirdMin = (TextView) view.findViewById(R.id.threeTextMin);
        mThirdDate.setText(ThirdData.getDate());
        mThirdWeather.setText(ThirdData.getWeatMain());
        mThirdMax.setText(ThirdData.getTemp_max());
        mThirdMin.setText(ThirdData.getTemp_min());
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Bitmap bitmap = new GetPictureHttps().getPicture(thirdPicture);
                mThirdImageView.post(new Runnable() {
                    @Override
                    public void run() {
                        mThirdImageView.setImageBitmap(bitmap);
                    }
                });
            }
        }).start();
        mThirdLinear = (LinearLayout) view.findViewById(R.id.threeLinear);
        mThirdLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),MorePhoneDetailActivity.class);
                intent.putExtra("isWhichDate",3);
                startActivity(intent);
            }
        });
        //第四行列表：
        ReturnData FourthData = new ReturnData(4);
        final String fourthPicture = FourthData.getWeatIcon();
        mFourthImageView = (ImageView) view.findViewById(R.id.fourImageView);
        mFourthDate = (TextView) view.findViewById(R.id.fourDate);
        mFourthWeather = (TextView) view.findViewById(R.id.fourWeather);
        mFourthMax = (TextView) view.findViewById(R.id.fourTextMax);
        mFourthMin = (TextView) view.findViewById(R.id.fourTextMin);
        mFourthDate.setText(FourthData.getDate());
        mFourthWeather.setText(FourthData.getWeatMain());
        mFourthMax.setText(FourthData.getTemp_max());
        mFourthMin.setText(FourthData.getTemp_min());
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Bitmap bitmap = new GetPictureHttps().getPicture(fourthPicture);
                mFourthImageView.post(new Runnable() {
                    @Override
                    public void run() {
                        mFourthImageView.setImageBitmap(bitmap);
                    }
                });
            }
        }).start();
        mFourthLinear = (LinearLayout) view.findViewById(R.id.fourLinear);
        mFourthLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),MorePhoneDetailActivity.class);
                intent.putExtra("isWhichDate",4);
                startActivity(intent);
            }
        });
        //第五行列表：
        ReturnData FiveData = new ReturnData(5);
        final String fivePicture = FiveData.getWeatIcon();
        mFiveImageView = (ImageView) view.findViewById(R.id.fiveImageView);
        mFiveDate = (TextView) view.findViewById(R.id.fiveDate);
        mFiveWeather = (TextView) view.findViewById(R.id.fiveWeather);
        mFiveMax = (TextView) view.findViewById(R.id.fiveTextMax);
        mFiveMin = (TextView) view.findViewById(R.id.fiveTextMin);
        mFiveDate.setText(FiveData.getDate());
        mFiveWeather.setText(FiveData.getWeatMain());
        mFiveMax.setText(FiveData.getTemp_max());
        mFiveMin.setText(FiveData.getTemp_min());
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Bitmap bitmap = new GetPictureHttps().getPicture(fivePicture);
                mFiveImageView.post(new Runnable() {
                    @Override
                    public void run() {
                        mFiveImageView.setImageBitmap(bitmap);
                    }
                });
            }
        }).start();
        mFiveLinear = (LinearLayout) view.findViewById(R.id.fiveLinear);
        mFiveLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),MorePhoneDetailActivity.class);
                intent.putExtra("isWhichDate",5);
                startActivity(intent);
            }
        });
        //第六行列表：
        ReturnData SixData = new ReturnData(6);
        final String sixPicture = SixData.getWeatIcon();
        mSixImageView = (ImageView) view.findViewById(R.id.sixImageView);
        mSixDate = (TextView) view.findViewById(R.id.sixDate);
        mSixWeather = (TextView) view.findViewById(R.id.sixWeather);
        mSixMax = (TextView) view.findViewById(R.id.sixTextMax);
        mSixMin = (TextView) view.findViewById(R.id.sixTextMin);
        mSixDate.setText(SixData.getDate());
        mSixWeather.setText(SixData.getWeatMain());
        mSixMax.setText(SixData.getTemp_max());
        mSixMin.setText(SixData.getTemp_min());
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Bitmap bitmap = new GetPictureHttps().getPicture(sixPicture);
                mSixImageView.post(new Runnable() {
                    @Override
                    public void run() {
                        mSixImageView.setImageBitmap(bitmap);
                    }
                });
            }
        }).start();
        mSixLinear = (LinearLayout) view.findViewById(R.id.sixLinear);
        mSixLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),MorePhoneDetailActivity.class);
                intent.putExtra("isWhichDate",6);
                startActivity(intent);
            }
        });
        return view;
    }
}

