package com.guigu.client.internet;

import com.guigu.client.internet.weather.ArrayOfString;
import com.guigu.client.internet.weather.WeatherWebServiceSoap;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

import java.util.List;


/**
 * @ClassName:CxfClient
 * @Description:webservice客户端： 该类提供两种不同的方式来调用webservice服务
 * 1：代理工厂方式
 * 2：动态调用webservice
 */
public class CxfInternetClient {


    public static void main(String[] args) {
        CxfInternetClient.main1();
//        CxfClient.main2();
    }

    /**
     * 1.代理类工厂的方式,需要拿到对方的接口地址
     */
    public static void main1() {
        try {
            // 接口地址
//            String address = "http://127.0.0.1:8080/soap/user?wsdl";
            String urlString = "http://www.webxml.com.cn/WebServices/WeatherWebService.asmx?wsdl";
//            String address = "http://127.0.0.1:8080/services/user?wsdl";
            // 代理工厂
            JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
            // 设置代理地址
            jaxWsProxyFactoryBean.setAddress(urlString);

            //创建服务名称
            //1.namespaceURI - 命名空间地址
            //2.localPart - 服务视图名
//            QName qname = new QName("http://WebXml.com.cn/", "WeatherWebService");
//            jaxWsProxyFactoryBean.setServiceName(qname);
            // 设置接口类型
            jaxWsProxyFactoryBean.setServiceClass(WeatherWebServiceSoap.class);
            // 创建一个代理接口实现
            WeatherWebServiceSoap weatherWebServiceSoap = (WeatherWebServiceSoap) jaxWsProxyFactoryBean.create();
            // 通过代理对象获取本地客户端
            Client client = ClientProxy.getClient(weatherWebServiceSoap);
            HTTPConduit conduit = (HTTPConduit) client.getConduit();
            HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
            httpClientPolicy.setConnectionTimeout(200000); // 连接超时
            httpClientPolicy.setAllowChunking(false); // 取消块编码
            httpClientPolicy.setReceiveTimeout(120000); // 响应超时
            conduit.setClient(httpClientPolicy);
            // 数据准备
            String userId = "武汉";
            // 调用代理接口的方法调用并返回结果
//            ArrayOfString weatherbyCityName = weatherWebServiceSoap.getWeatherbyCityName(userId);
            ArrayOfString weatherbyCityName = weatherWebServiceSoap.getSupportProvince();
//            System.err.println("返回结果:" + weatherbyCityName.toString());

            List<String> list = weatherbyCityName.getString();

            for(String str : list){
                System.out.println(str);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 2：动态调用
     */
    public static void main2() {

        // 接口地址
        String address = "http://127.0.0.1:8080/soap/weather?wsdl";
        // 创建动态客户端
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient(address);
        // 需要密码的情况需要加上用户名和密码
        // client.getOutInterceptors().add(new ClientLoginInterceptor(USER_NAME, PASS_WORD));
        Object[] objects = new Object[0];
        try {
            // invoke("方法名",参数1,参数2,参数3....);
            objects = client.invoke("queryWeather", "广州");
            System.err.println("返回数据:" + objects[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}