package com.guigu.client.internet;

import com.guigu.client.internet.weather.ArrayOfString;
import com.guigu.client.internet.weather.WeatherWebServiceSoap;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

import java.net.MalformedURLException;
import java.util.List;

public class ProxyFactoryBeanClient {

	public static void main(String[] args) throws MalformedURLException {

		String urlString = "http://www.webxml.com.cn/WebServices/WeatherWebService.asmx?wsdl";
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(WeatherWebServiceSoap.class);
		factory.setAddress(urlString);
		//创建服务名称
		//1.namespaceURI - 命名空间地址
		//2.localPart - 服务视图名
//		QName qname = new QName("http://WebXml.com.cn/", "WeatherWebService");
//		factory.setServiceName(qname);

		WeatherWebServiceSoap service = (WeatherWebServiceSoap) factory.create();
		// 通过代理对象获取本地客户端
		Client client = ClientProxy.getClient(service);
		// 需要密码的情况需要加上用户名和密码
		// client.getOutInterceptors().add(new
		// ClientLoginInterceptor(USER_NAME,PASS_WORD));
		HTTPConduit conduit = (HTTPConduit) client.getConduit();
		HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
		httpClientPolicy.setConnectionTimeout(20000); // 连接超时
		httpClientPolicy.setAllowChunking(false); // 取消块编码
		httpClientPolicy.setReceiveTimeout(120000); // 响应超时
		conduit.setClient(httpClientPolicy);
		// client.getOutInterceptors().addAll(interceptors);//设置拦截器

		// 数据准备
		String userId = "武汉";
		// 调用代理接口的方法调用并返回结果
//		ArrayOfString weatherbyCityName = service.getWeatherbyCityName(userId);
//		ArrayOfString weatherbyCityName = service.getSupportProvince();
		ArrayOfString weatherbyCityName = service.getSupportCity("湖北");
//            System.err.println("返回结果:" + weatherbyCityName.toString());

		List<String> list = weatherbyCityName.getString();

		for(String str : list){
			System.out.println(str);
		}

		try {
//			UserDto user = service.getUserByName("王五");
//			System.out.println("返回数据:" + user);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
