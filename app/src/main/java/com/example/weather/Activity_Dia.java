package com.example.weather;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Activity_Dia extends AppCompatActivity {
    private final String TAG = "Activity_Dia";
    private Button yes_Button;
    private EditText mEditText;
    private String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dia);
        mEditText = (EditText)findViewById(R.id.edit_cityName);
        //yes_Button按钮
        yes_Button = (Button)findViewById(R.id.yes_button);
        yes_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.isFirstFlag=true;
                name = mEditText.getText().toString();
                SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase("/data/data/com.example.weather/databases/Weather.db",null);
                ContentValues value = new ContentValues();//数据模型
                value.put("cityName", name);
                db.insert("city", null, value);
                Intent intent = new Intent(Activity_Dia.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}


