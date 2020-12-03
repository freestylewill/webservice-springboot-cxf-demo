package com.guigu.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(targetNamespace="http://service.lk.com")
public interface WeatherService {
    /**
     * 根据城市获取天气信息
     * @param cityName
     * @return
     */
    @WebMethod
    @WebResult(name = "String", targetNamespace = "")
    String queryWeather(@WebParam(name = "cityName") String cityName);
}
