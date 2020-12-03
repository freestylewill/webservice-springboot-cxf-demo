package com.guigu.service;

import com.guigu.entity.User;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(targetNamespace="http://service.lk.com")
public interface UserService {
    @WebMethod//标注该方法为webservice暴露的方法,用于向外公布，它修饰的方法是webservice方法，去掉也没影响的，类似一个注释信息。
    User getUser(@WebParam(name = "userId") String userId);

     @WebMethod
     @WebResult(name="String",targetNamespace="")
     String getUserName(@WebParam(name = "userId") String userId);


}
