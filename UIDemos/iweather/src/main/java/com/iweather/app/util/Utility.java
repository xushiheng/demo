package com.iweather.app.util;

import android.text.TextUtils;

import com.iweather.app.db.MyWeatherDB;
import com.iweather.app.model.City;
import com.iweather.app.model.County;
import com.iweather.app.model.Province;

/**
 * Created by leon Xu on 2015/4/5 0005.
 */
public class Utility {

    //用于解析形如数据“123456|城市1,123456|城市2”的工具类
    public static boolean handleProvincesResponse(MyWeatherDB myWeatherDB,
                                                               String response) {
        if(!TextUtils.isEmpty(response)){
            String[] allProvinces = response.split(",");
            if(allProvinces != null && allProvinces.length > 0){
                for(String p : allProvinces){
                    String[] array = p.split("\\|");
                    Province province = new Province();
                    province.setProvinceCode(array[0]);
                    province.setProvinceName(array[1]);
                    myWeatherDB.saveProvince(province);
                }
                return true;
            }
        }
        return false;
    }

    public static boolean handleCitiesResponse(MyWeatherDB myWeatherDB,
                                                            String response, int provinceId) {
        if(!TextUtils.isEmpty(response)){
            String[] allCities = response.split(",");
            if(allCities != null && allCities.length > 0){
                for(String p : allCities){
                    String[] array = p.split("\\|");
                    City city = new City();
                    city.setCityCode(array[0]);
                    city.setCityName(array[1]);
                    city.setProvinceId(provinceId);
                    myWeatherDB.saveCity(city);
                }
                return true;
            }
        }
        return false;
    }

    public static boolean handleCountiesResponse(MyWeatherDB myWeatherDB,
                                                              String response, int cityId) {
        if(!TextUtils.isEmpty(response)){
            String[] allCountries = response.split(",");
            if(allCountries != null && allCountries.length > 0){
                for(String p : allCountries){
                    String[] array = p.split("\\|");
                    County county = new County();
                    county.setCountyCode(array[0]);
                    county.setCountyName(array[1]);
                    county.setCityId(cityId);
                    myWeatherDB.saveCounty(county);
                }
                return true;
            }
        }
        return false;
    }
}
