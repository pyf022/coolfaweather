package android.coolfaweather.com.coolfawealther.db;

import org.litepal.crud.DataSupport;

/**
 * Created by admin on 2017/4/19.
 * 省份数据表，三个字段
 * id:数据库内部的键
 * provinceName:省份名称
 * provinceCode:省份对应的代号
 */

public class Province extends DataSupport {
    private int id;
    private String provinceName;
    private int provinceCode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public int getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(int provinceCode) {
        this.provinceCode = provinceCode;
    }
}
