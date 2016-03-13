package com.zzia.wngn.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUtil {

	private static final Logger logger = LoggerFactory.getLogger(FtpUtil.class);

	protected static int SOCKET_TIMEOUT = 60000;
	protected static int CONNECT_TIMEOUT = 60000;

	/**
	 * 调用 API
	 * 
	 * @param parameters
	 * @return
	 */
	public String post(String uri, String parameters) {
		return post(uri, parameters, SOCKET_TIMEOUT, CONNECT_TIMEOUT);
	}

	public String post(String uri, String parameters, String socketTimeout, String connectTimeout) {
		Integer socketTimeout_ = null;// 读取超时时间
		Integer connectTimeout_ = null;// 链接超时时间
		if (socketTimeout != null && !"".equals(socketTimeout)) {
			socketTimeout_ = Integer.parseInt(socketTimeout);
		}
		if (connectTimeout != null && !"".equals(connectTimeout)) {
			connectTimeout_ = Integer.parseInt(connectTimeout);
		}
		return post(uri, parameters, socketTimeout_, connectTimeout_);
	}

	@SuppressWarnings("deprecation")
	public String post(String uri, String parameters, Integer socketTimeout, Integer connectTimeout) {
		int socketTimeout_ = SOCKET_TIMEOUT;// 读取超时时间
		int connectTimeout_ = CONNECT_TIMEOUT;// 链接超时时间

		if (socketTimeout != null) {
			socketTimeout_ = socketTimeout;
		}
		if (connectTimeout != null) {
			connectTimeout_ = connectTimeout;
		}
		String body = null;
		HttpClient client = HttpClientBuilder.create().build();
		if (uri == null || "".equals(uri)) {
			logger.error("请求url不能为空");
			return null;
		}
		HttpPost method = new HttpPost(uri);
		// 设置请求和传输超时时间
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout_).setConnectTimeout(connectTimeout_).build();
		method.setConfig(requestConfig);
		if (method != null) {
			try {
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				String sign = CheckSignUtil.generateSignMd5(parameters, "c3a648f5-fa1e-4ffd-85be-075ff73b1434", "utf-8");
				params.add(new BasicNameValuePair("sign", sign));
				params.add(new BasicNameValuePair("data", parameters));
				logger.info("请求URL：" + uri);
				logger.info("请求字符串：data=" + parameters);
				method.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
				HttpResponse response = client.execute(method);
				int statusCode = response.getStatusLine().getStatusCode();
				if (statusCode != HttpStatus.SC_OK) {
					logger.info("Method failed:" + response.getStatusLine());
					return null;
				}
				body = EntityUtils.toString(response.getEntity());
				logger.info("响应字符串：body=" + body);

			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return body;
	}

	/**
	 * 调用 API
	 * 
	 * @param parameters
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public String post(String uri, Map<String, String> parameters, String socketTimeout, String connectTimeout) {
		Integer socketTimeout_ = null;// 读取超时时间
		Integer connectTimeout_ = null;// 链接超时时间
		if (socketTimeout != null && !"".equals(socketTimeout)) {
			socketTimeout_ = Integer.parseInt(socketTimeout);
		} else {
			socketTimeout_ = SOCKET_TIMEOUT;
		}
		if (connectTimeout != null && !"".equals(connectTimeout)) {
			connectTimeout_ = Integer.parseInt(connectTimeout);
		} else {
			connectTimeout_ = CONNECT_TIMEOUT;
		}
		String body = null;
		HttpClient client = HttpClientBuilder.create().build();
		if (uri == null || "".equals(uri)) {
			return null;
		}
		HttpPost method = new HttpPost(uri);
		// 设置请求和传输超时时间
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout_).setConnectTimeout(connectTimeout_).build();
		method.setConfig(requestConfig);
		if (method != null) {
			try {
				String parameterString = "";
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				if (parameters != null && parameters.size() > 0) {
					Iterator<Map.Entry<String, String>> it = parameters.entrySet().iterator();
					while (it.hasNext()) {
						Map.Entry<String, String> entry = it.next();
						params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
						if ("".endsWith(parameterString)) {
							parameterString = entry.getKey() + "=" + entry.getValue();
						} else {
							parameterString = parameterString + "&" + entry.getKey() + "=" + entry.getValue();
						}
					}
				}
				logger.info("请求URL：" + uri);
				logger.info("请求字符串：" + parameterString);
				method.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
				HttpResponse response = client.execute(method);
				int statusCode = response.getStatusLine().getStatusCode();
				if (statusCode != HttpStatus.SC_OK) {
					logger.error("Method failed:" + response.getStatusLine());
					return null;
				}
				body = EntityUtils.toString(response.getEntity());
				logger.info("响应字符串：body=" + body);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return body;
	}
}
