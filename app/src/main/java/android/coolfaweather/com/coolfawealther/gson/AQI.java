package android.coolfaweather.com.coolfawealther.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 2017/4/26.
 */

public class AQI {
    public AQICity city;
    public class AQICity{
        public String aqi;
        public String pm25;
        @SerializedName("qlty")
        public String quality;
    }
}
