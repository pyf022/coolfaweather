package android.coolfaweather.com.coolfawealther.db;

import org.litepal.crud.DataSupport;

/**
 * Created by admin on 2017/4/19.
 * id:数据库生成的ID
 * cityName:城市的名称
 * cityCode:城市对应代码
 * provinceId:所属省份对应的ID
 */

public class City extends DataSupport {
    private int id;
    private String cityName;
    private int cityCode;
    private int provinceId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getCityCode() {
        return cityCode;
    }

    public void setCityCode(int cityCode) {
        this.cityCode = cityCode;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }
}
