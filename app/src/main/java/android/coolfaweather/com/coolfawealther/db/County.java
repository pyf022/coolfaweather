package android.coolfaweather.com.coolfawealther.db;

import org.litepal.crud.DataSupport;

/**
 * Created by admin on 2017/4/19.
 * id:数据库生成的id
 * countyName:县的名称
 * weatherid:天气对应的id
 * cityId：县所属的城市ID
 */

public class County extends DataSupport {
    private int id;
    private String countyName;
    private String weatherId;
    private int cityId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(String weatherId) {
        this.weatherId = weatherId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }
}
