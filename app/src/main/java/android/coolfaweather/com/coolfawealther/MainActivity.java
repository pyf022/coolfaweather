package android.coolfaweather.com.coolfawealther;

import android.content.Intent;
import android.content.SharedPreferences;
import android.coolfaweather.com.coolfawealther.gson.Weather;
import android.coolfaweather.com.coolfawealther.util.LogUtil;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LogUtil.d("MainActivity debug","^^oncreate^^");
    setContentView(R.layout.activity_main);
    SharedPreferences pres = PreferenceManager.getDefaultSharedPreferences(this);
        if(pres.getString("weather",null) !=null){
                Intent intent = new Intent(this, WeatherActivity.class);
        startActivity(intent);
        finish();
        }

        }
        }
