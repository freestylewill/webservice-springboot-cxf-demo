package com.guigu.service.impl;

import com.guigu.service.WeatherService;
import org.springframework.stereotype.Component;

import javax.jws.WebService;

@WebService(serviceName = "WeatherService",//对外发布的服务名
        targetNamespace = "http://service.lk.com",//指定你想要的名称空间，通常使用使用包名反转
        endpointInterface = "com.guigu.service.WeatherService")//服务接口全路径, 指定做SEI（Service EndPoint Interface）服务端点接口
@Component
public class WeatherServiceImpl implements WeatherService {
    @Override
    public String queryWeather(String cityName) {
        if ("广州".equals(cityName)) {
            return cityName + ": 天气晴，白天最高温度30度,未天三天都晴";
        } else {
            return cityName + ": 天气雨，白天最高温度30度,未天三天都雨";
        }
    }
}
