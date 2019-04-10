package com.example.bubba.coolweather.util;

import android.text.TextUtils;

import com.example.bubba.coolweather.db.City;
import com.example.bubba.coolweather.db.County;
import com.example.bubba.coolweather.db.Province;
import com.example.bubba.coolweather.gson.Weather;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Bubba on 2019/4/1.
 */

public class Utility {


    /**
     * 处理请求返回的省级数据 并将数据存储到数据库中
     */
    public static boolean onHandleProvinceResponse(String response){

        if(!TextUtils.isEmpty(response)){
            try {
                JSONArray allProvinces = new JSONArray(response);
                for (int i = 0; i < allProvinces.length(); i++) {
                    JSONObject provinceObject = allProvinces.getJSONObject(i);
                    Province province = new Province();
                    province.setProvinceName(provinceObject.getString("name"));
                    province.setProvinceCode(provinceObject.getInt("id"));
                    province.save();
                }
                return true;
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }


    /**
     * 处理请求返回的市级数据 并将数据存储到数据库中
     */
    public static boolean onHandleCityResponse(String response,int provinceId){

        if(!TextUtils.isEmpty(response)){
            try{
                JSONArray citiesJsonArray=new JSONArray(response);
                for(int i=0;i<citiesJsonArray.length();i++){
                    JSONObject cityObject=citiesJsonArray.getJSONObject(i);
                    City city=new City();

                    city.setCityName(cityObject.getString("name"));
                    city.setCityCode(cityObject.getInt("id"));
                    city.setProvinceId(provinceId);
                    city.save();
                }
                return true;
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }


    /**
     * 处理请求返回的县级的额数据 并将数据存储到数据库中
     */
    public static boolean onHandleCountyResponse(String response,int cityId){
        if(!TextUtils.isEmpty(response)){
            try{
                JSONArray countiseJsonArray=new JSONArray(response);
                for(int i=0;i<countiseJsonArray.length();i++){
                    JSONObject countyObject=countiseJsonArray.getJSONObject(i);
                    County county=new County();
                    county.setCityId(cityId);
                    county.setCountyName(countyObject.getString("name"));
                    county.setWeatherId(countyObject.getString("weather_id"));
                    county.save();
                }
                return true;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }


    /**
     *将返回的JSON数据解析成weatherr实体类
     */
    public static Weather onHandleWeatherResponse(String resoonse){

        try{
            JSONObject jsonObject=new JSONObject(resoonse);
            JSONArray jsonArray=jsonObject.getJSONArray("HeWeather");
            String weatherContent=jsonArray.get(0).toString();
            return new Gson().fromJson(weatherContent,Weather.class);
        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }











}
