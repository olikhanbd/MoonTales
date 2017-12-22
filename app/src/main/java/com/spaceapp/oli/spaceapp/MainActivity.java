package com.spaceapp.oli.spaceapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView lunarDataBtn, planetsBtn, nasaBtn, settingsBtn, aboutBtn, exitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lunarDataBtn =(ImageView) findViewById(R.id.lunarDataBtn);
        planetsBtn = (ImageView)findViewById(R.id.planetsBtn);
        nasaBtn = (ImageView)findViewById(R.id.nasaBtn);
        settingsBtn = (ImageView)findViewById(R.id.settingsBtn);
        aboutBtn = (ImageView)findViewById(R.id.aboutBtn);
        exitBtn = (ImageView)findViewById(R.id.exitBtn);

        lunarDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LunarDataActivity.class);
                startActivity(intent);
            }
        });

        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });
    }

}
