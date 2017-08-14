package com.wanhuchina.common.util.http;

import com.google.common.base.Strings;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * HttpClient  post方式
 */
public class HttpPostUtils {

	private static final Logger logger = LoggerFactory.getLogger(HttpPostUtils.class);
	
	private static final Integer connectionTimeoutTime = 30000;// 30秒
	private static final Integer soTimeoutTime = 30000;// 30秒
	

	
	public static String httpPost(String url, Map<String, String> header, String content, String contentType, Map<String, String> paramMap, String encoding) {

		if (encoding == null || "".equals(encoding))
			encoding = "UTF-8";

		StringBuffer sBuffer = new StringBuffer();

		// 构造HttpClient的实例
		HttpClient httpClient = new HttpClient();
		// 创建POS方法的实例
		NameValuePair[] pairs = null;
		PostMethod postMethod = new PostMethod(url);
		if (paramMap != null) {
			pairs = new NameValuePair[paramMap.size()];
			Set<String> set = paramMap.keySet();
			Iterator<String> it = set.iterator();
			int i = 0;
			while (it.hasNext()) {
				Object key = it.next();
				Object value = paramMap.get(key);
				pairs[i] = new NameValuePair(key.toString(), value.toString());
				i++;
			}
			postMethod.setRequestBody(pairs);
		}
		if (header != null && header.size() > 0) {
			Iterator<String> keySetIt = header.keySet().iterator();
			while (keySetIt.hasNext()) {
				String key = keySetIt.next();
				postMethod.addRequestHeader(key, header.get(key));
			}
		}
		
		try {
			
			if (!Strings.isNullOrEmpty(content)) {
				postMethod.setRequestEntity(new StringRequestEntity(content, contentType, encoding));
			}
			
			postMethod.getParams().setParameter(
					HttpMethodParams.HTTP_CONTENT_CHARSET, encoding);
	
			httpClient.getHttpConnectionManager().getParams()
					.setConnectionTimeout(connectionTimeoutTime); // 连接250秒超时
	
			httpClient.getHttpConnectionManager().getParams()
					.setSoTimeout(soTimeoutTime);// 读取10秒超时
	
			postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
					new DefaultHttpMethodRetryHandler());
		
			// 执行getMethod
			int statusCode = httpClient.executeMethod(postMethod);

			if (statusCode != HttpStatus.SC_OK && statusCode != HttpStatus.SC_CREATED) {
				// System.err.println("Method failed: " +
				// postMethod.getStatusLine());
				// sBuffer = new StringBuffer(postMethod.getStatusLine() + "");
				throw new Exception(postMethod.getStatusLine() == null? null:postMethod.getStatusLine()
						.toString());
			} else {
				// InputStream resStream = postMethod.getResponseBodyAsStream();
				sBuffer = new StringBuffer(postMethod.getResponseBodyAsString()
						+ "");

			}
		} catch (Exception exp) {
			logger.error("------http post error---url:{},e:{}", url,exp);
			throw new RuntimeException("----http post exception---" + url, exp);
		} finally {
			// 释放连接
			postMethod.releaseConnection();
		}
		return sBuffer.toString();
	}
	
	
}