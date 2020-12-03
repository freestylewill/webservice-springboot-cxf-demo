package com.guigu.client.internet;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

public class DynamicClientClient {
	// 创建动态客户端
	private static Client client = null;
	static {
		JaxWsDynamicClientFactory factory = JaxWsDynamicClientFactory.newInstance();
		String urlString = "http://www.webxml.com.cn/WebServices/WeatherWebService.asmx?wsdl";
		client = factory.createClient(urlString);
		// 需要密码的情况需要加上用户名和密码
		// client.getOutInterceptors().add(new
		// ClientLoginInterceptor(USER_NAME,PASS_WORD));
		HTTPConduit conduit = (HTTPConduit) client.getConduit();
		HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
		httpClientPolicy.setConnectionTimeout(2000); // 连接超时
		httpClientPolicy.setAllowChunking(false); // 取消块编码
		httpClientPolicy.setReceiveTimeout(120000); // 响应超时
		conduit.setClient(httpClientPolicy);
	}

	public static void main(String[] args) {
		// client.getOutInterceptors().addAll(interceptors);//设置拦截器
		try {
			Object[] objects = null;
			// invoke("方法名",参数1,参数2,参数3....);
			objects = client.invoke("getSupportProvince");
//			System.out.println((UserDto) objects[0]);
			System.out.println(objects.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(client);
	}
}
