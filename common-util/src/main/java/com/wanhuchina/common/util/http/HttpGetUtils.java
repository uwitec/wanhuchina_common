package com.wanhuchina.common.util.http;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * HttpClient  get方式
 */
public class HttpGetUtils {

	private static final Logger logger = LoggerFactory.getLogger(HttpGetUtils.class);

	private static final Integer connectionRequestTimeout = 30000;// 30秒
	private static final Integer connectionTimeoutTime = 30000;// 30秒
	private static final Integer soTimeoutTime = 30000;// 30秒

    public static class Response {
    	private String body;
    	private Header[] headers;
        public Response() {
        }
        public Response(String body, Header[] headers) {
        	this.body = body;
        	this.headers = headers;
        }
		public String getBody() {
			return body;
		}
		public void setBody(String body) {
			this.body = body;
		}
		public Header[] getHeaders() {
			return headers;
		}
		public void setHeaders(Header[] headers) {
			this.headers = headers;
		}
    }

	public static String httpGet(String url, Map<String, String> header, Map<String, String> paramMap,
			String encoding) {

		if (encoding == null || "".equals(encoding))
			encoding = "UTF-8";

		StringBuffer sBuffer = new StringBuffer();

		// 构造HttpClient的实例
		HttpClient httpClient = new HttpClient();

		String paramStr = "";
		if (paramMap != null) {
			Set<String> set = paramMap.keySet();
			Iterator<String> it = set.iterator();
			while (it.hasNext()) {
				Object key = it.next();
				Object value = paramMap.get(key);
				paramStr += "&" + key + "=" + value;
			}

		}
		GetMethod getMethod = new GetMethod();
		String requestUrl =(paramMap==null|| paramMap.isEmpty()) ? url : url + "?" + paramStr.substring(1);
		try {
			getMethod.setURI(new URI(requestUrl, false, encoding));
		} catch (Exception e) {
			logger.error("url编码异常！url:{}", requestUrl, e);
		}

		if (header != null && header.size() > 0) {
			Iterator<String> keySetIt = header.keySet().iterator();
			while (keySetIt.hasNext()) {
				String key = keySetIt.next();
				getMethod.addRequestHeader(key, header.get(key));
			}
		}

		try {

			getMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, encoding);

			httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(connectionTimeoutTime); // 连接5秒超时

			httpClient.getHttpConnectionManager().getParams().setSoTimeout(soTimeoutTime);// 读取10秒超时

			getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());

			// 执行getMethod
			int statusCode = httpClient.executeMethod(getMethod);

			if (statusCode != HttpStatus.SC_OK && statusCode != HttpStatus.SC_CREATED) {
				// System.err.println("Method failed: " +
				// postMethod.getStatusLine());
				// sBuffer = new StringBuffer(postMethod.getStatusLine() + "");
				throw new Exception(getMethod.getStatusLine().toString());
			} else {
				// InputStream resStream = postMethod.getResponseBodyAsStream();
				sBuffer = new StringBuffer(getMethod.getResponseBodyAsString() + "");

			}
		} catch (Exception exp) {
			logger.error("------http post error---url:{}-", url);
			throw new RuntimeException("----http post exception---" + url, exp);
		} finally {
			// 释放连接
			getMethod.releaseConnection();
		}
		return sBuffer.toString();
	}

	public static Response httpGetWithResponseHeaders(String url, Map<String, String> header,
			Map<String, String> paramMap, String encoding) {

		if (encoding == null || "".equals(encoding))
			encoding = "UTF-8";

		StringBuffer sBuffer = new StringBuffer();
		Header[] headers = null;

		// 构造HttpClient的实例
		HttpClient httpClient = new HttpClient();

		String paramStr = "";
		if (paramMap != null) {
			Set<String> set = paramMap.keySet();
			Iterator<String> it = set.iterator();
			while (it.hasNext()) {
				Object key = it.next();
				Object value = paramMap.get(key);
				paramStr += "&" + key + "=" + value;
			}

		}
		GetMethod getMethod = new GetMethod();
		String requestUrl = paramMap.isEmpty() ? url : url + "?" + paramStr.substring(1);
		try {
			getMethod.setURI(new URI(requestUrl, false, encoding));
		} catch (Exception e) {
			logger.error("url编码异常！url:{}", requestUrl, e);
		}

		if (header != null && header.size() > 0) {
			Iterator<String> keySetIt = header.keySet().iterator();
			while (keySetIt.hasNext()) {
				String key = keySetIt.next();
				getMethod.addRequestHeader(key, header.get(key));
			}
		}

		try {

			getMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, encoding);

			httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(connectionTimeoutTime); // 连接5秒超时

			httpClient.getHttpConnectionManager().getParams().setSoTimeout(soTimeoutTime);// 读取10秒超时

			getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());

			// 执行getMethod
			int statusCode = httpClient.executeMethod(getMethod);

			if (statusCode != HttpStatus.SC_OK && statusCode != HttpStatus.SC_CREATED) {
				// System.err.println("Method failed: " +
				// postMethod.getStatusLine());
				// sBuffer = new StringBuffer(postMethod.getStatusLine() + "");
				throw new Exception(getMethod.getStatusLine().toString());
			} else {
				// InputStream resStream = postMethod.getResponseBodyAsStream();
				sBuffer = new StringBuffer(getMethod.getResponseBodyAsString() + "");
                headers = getMethod.getResponseHeaders();
			}
		} catch (Exception exp) {
			logger.error("------http post error---url:{}-", url);
			throw new RuntimeException("----http post exception---" + url, exp);
		} finally {
			// 释放连接
			getMethod.releaseConnection();
		}
		
		return new Response(sBuffer.toString(), headers);
	}

}