

package android.coolfaweather.com.coolfawealther;

import android.content.Intent;
import android.content.SharedPreferences;
import android.coolfaweather.com.coolfawealther.gson.Forecast;
import android.coolfaweather.com.coolfawealther.gson.Weather;
import android.coolfaweather.com.coolfawealther.service.AutoUpdateService;
import android.coolfaweather.com.coolfawealther.util.HttpUtil;
import android.coolfaweather.com.coolfawealther.util.LogUtil;
import android.coolfaweather.com.coolfawealther.util.Utility;
import android.graphics.Color;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity {
    private ScrollView weatherLayout;

    private TextView titleCity;
    private TextView titleUpateTime;
    private TextView degreeText;
    private TextView weatherInfoText;
    private LinearLayout forecastLayout;
    private TextView aqiText;
    private TextView pm25Text;
    private TextView comfortText;
    private TextView carWashText;
    private TextView sportText;
    private TextView qualityText;

    private ImageView bingPicImg;//从必应中获取每天图片，作为背景

    SwipeRefreshLayout swipeRefresh ;
    public DrawerLayout drawerLayout;
    private Button navButton;

    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.d("WeatherActity debug","^^^onStart info^^^");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.d("WeatherActity debug","^^^onPause info^^^");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.d("WeatherActity debug","^^^onDestroy info^^^");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.d("WeatherActity debug","^^^onStop info^^^");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString=pref.getString("weather",null);
        if(weatherString !=null){
            Weather weather = Utility.handleWeatherResponse(weatherString);
            requestWeather(weather.basic.weatherId);
        }
        LogUtil.d("WeatherActity debug","^^^onRestart info^^^");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d("WeatherActity debug","^^^onCreate info^^^");
        if(Build.VERSION.SDK_INT>=21){
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_weather);
        titleCity =(TextView) findViewById(R.id.title_city);
        weatherLayout = (ScrollView) findViewById(R.id.weather_layout);
        degreeText = (TextView) findViewById(R.id.degree_text);
        weatherInfoText = (TextView) findViewById(R.id.weather_info_text);
        forecastLayout = (LinearLayout) findViewById(R.id.forecast_layout);
        aqiText = (TextView) findViewById(R.id.aqi_text);
        pm25Text = (TextView) findViewById(R.id.pm25_text);
        comfortText = (TextView) findViewById(R.id.comfort_text);
        carWashText = (TextView) findViewById(R.id.car_wash_text);
        sportText = (TextView) findViewById(R.id.sport_text);
        titleUpateTime = (TextView) findViewById(R.id.title_update_time);
        qualityText =(TextView) findViewById(R.id.quality_text);
        //滑动菜单
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navButton = (Button)findViewById(R.id.nva_button);
        navButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        bingPicImg = (ImageView)findViewById(R.id.bing_pic_img);
        //手动更新天气
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);

        final SharedPreferences pres = PreferenceManager.getDefaultSharedPreferences(this);
        String bingPic = pres.getString("bing_pic",null);
        if(bingPic !=null){
            Glide.with(this).load(bingPic).into(bingPicImg);
        }else{
            loadBingPic();
        }
        String weatherString = pres.getString("weather",null);
        final String weatherId;
        if(weatherString !=null){
            //有缓存时直接解析天气数据
            Weather weather = Utility.handleWeatherResponse(weatherString);
            weatherId = weather.basic.weatherId;
            showWeatherInfo(weather);

           // LogUtil.d("weatherString not null","****"+weatherId+"***");
        }else{
            //无缓冲去服务器查询
            weatherId = getIntent().getStringExtra("weather_id");//获取MainActivity传过来的weatherId
            weatherLayout.setVisibility(View.INVISIBLE);//隐藏天气信息布局 要不然界面不美观
            requestWeather(weatherId);//到服务器中请求
            LogUtil.d("weatherString is null","****"+weatherId+"***");
        }
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {

                String weatherId_ref = pres.getString("weatherId",null);

                if(weatherId_ref !=null){
                    requestWeather(weatherId_ref);
                }else{
                    requestWeather(weatherId);
                }
            }
        });

    }

    /*
       到必应服务器中加载每日图片

     */
    private void loadBingPic(){

        LogUtil.d("WeatherActivity","***loadBingPic***");
        String requestBingPic ="http://guolin.tech/api/bing_pic";
        HttpUtil.sendOkHttpRequest(requestBingPic, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
               e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
               final String bingPic =response.body().string();
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                editor.putString("bing_pic",bingPic);
                editor.apply();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(WeatherActivity.this).load(bingPic).into(bingPicImg);
                    }
                });
            }
        });
    }

    //根据天气ID 请求天气信息
    public void requestWeather(final String weatherId){

      //  LogUtil.d("onrefsh","-----requestWeather----");
        /*
        处理业务逻辑
         */
        String weatherUrl = "http://guolin.tech/api/weather?cityid="+weatherId+"&key=8cbd362e8e654d52aa35308bf1286058";
        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherActivity.this,"获取天气信息失败",Toast.LENGTH_SHORT).show();
                        swipeRefresh.setRefreshing(false);
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response)throws IOException  {
                final String responseText = response.body().string();
                final Weather weather= Utility.handleWeatherResponse(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (weather !=null&&"ok".equals(weather.status)){
                            //保存到缓存中，不用多次请求
                            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                            editor.putString("weather",responseText);
                            editor.apply();
                            showWeatherInfo(weather);
                        }else{
                            Toast.makeText(WeatherActivity.this,"获取天气信息失败",Toast.LENGTH_SHORT).show();
                        }
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        });
        loadBingPic();
    }

    //展示天气信息到页面
    private void showWeatherInfo(Weather weather){
        LogUtil.d("*---showWeatherInfo----","进入了这个方法");
        /*
        处理业务逻辑
         */
        String cityName = weather.basic.cityName;
        String updateTime =weather.basic.update.updateTime.split(" ")[1];
        String degrees =weather.now.temperature+"°C";
        String weatherInfo =weather.now.more.info;
        titleCity.setText(cityName);
        titleUpateTime.setText(updateTime);
        degreeText.setText(degrees);
        weatherInfoText.setText(weatherInfo);
        forecastLayout.removeAllViews();
        for(Forecast forecast:weather.forecastList){
            View view = LayoutInflater.from(this).inflate(R.layout.forecast_item,forecastLayout,false);
            TextView dataText = (TextView)view.findViewById(R.id.date_text);
            TextView infoText = (TextView)view.findViewById(R.id.info_text);
            TextView maxText = (TextView)view.findViewById(R.id.max_text);
            TextView minText = (TextView)view.findViewById(R.id.min_text);
            dataText.setText(forecast.date);
            infoText.setText(forecast.more.info);
            maxText.setText(forecast.temperature.max+"°C");
            minText.setText(forecast.temperature.min+"°C");
            forecastLayout.addView(view);
        }

        if(weather.aqi !=null){
            aqiText.setText(weather.aqi.city.aqi);
            pm25Text.setText(weather.aqi.city.pm25);
            String quality = weather.aqi.city.quality;
            if(quality.equals("良")||quality.equals("优")){
                qualityText.setTextColor(Color.GREEN);
            }else{
                qualityText.setTextColor(Color.YELLOW);

            }
            qualityText.setText(quality);
        }

        String comfort = "舒适度: "+weather.suggestion.comfort.info;
        String carWash = "洗车指数: "+weather.suggestion.carWash.info;
        String sport ="运动建议: "+weather.suggestion.sport.info;
        comfortText.setText(comfort);
        carWashText.setText(carWash);
        sportText.setText(sport);
        weatherLayout.setVisibility(View.VISIBLE);//暂时weather 布局
        Intent intent = new Intent(this,AutoUpdateService.class);//开启服务
        startService(intent);
    }
}
