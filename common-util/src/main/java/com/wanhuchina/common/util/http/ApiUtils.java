package com.wanhuchina.common.util.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import org.apache.commons.httpclient.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Map;

/**
  * @ClassName: ApiUtils
  * @Description: http的接口访问工具
 */
public class ApiUtils {
	
	private static Logger log = LoggerFactory.getLogger(ApiUtils.class);
	
	public static final String API_ENCODING = "utf-8";
	
	
	/**
	 * @description post方式 body传递参数
	 * @param url
	 * @param content
	 * @return
	 * @throws Exception 
	 */
	public static JSONObject excutePost(String url,Map<String, String> header,String content,Map<String, String> paramMap) throws Exception{
		log.info("##post request begin!url:{}",url);
		log.info("##post request content:{}",content);
		log.info("##post request paramMap:{}",paramMap);
		
		JSONObject jsonObj = null;
		try {
			String contentType = "application/json; charset=UTF-8";
			String resultStr = HttpPostUtils.httpPost(url,header,content,contentType,paramMap,API_ENCODING);
			if(Strings.isNullOrEmpty(resultStr)){
				log.error("##post request return null");
				return jsonObj;
			}
			log.info("##post url:{} response :{}",url,resultStr);
			jsonObj = JSON.parseObject(resultStr);
			return jsonObj;  //直接返回json没有successful信息{"status":200,"errors":[]}
			
		} catch (Exception e) {
			log.error("##post request err!",e);
			throw e;
		}
	}
	
	
	/**
	 * @description get方式
	 * @param url
	 * @return
	 * @throws Exception 
	 */
	public static JSONObject excuteGet(String url,Map<String, String> headerMap,Map<String, String> paramMap) throws Exception{
		log.info("##get request url:{}",url);
		log.info("##get request paramMap:{}",paramMap);
		
		long startTime = System.currentTimeMillis();
		JSONObject jsonObj = null;
		try {
			
			String resultStr = HttpGetUtils.httpGet(url,headerMap,paramMap,API_ENCODING);
			log.info("##get response :{}",resultStr);
			jsonObj = JSON.parseObject(resultStr);
			
		} catch (Exception e) {
			log.error("##get request err!",e);
			throw e;
		}
		log.info("##get request end! costTime：{}",System.currentTimeMillis() - startTime);
		return jsonObj;
	}
	
	/**
	 * @description get方式  并且得到响应头信息
	 * @return
	 * @throws Exception 
	 */
	public static class APIResult {
		private JSONObject body;
		private Header[] headers;
		public APIResult(){}
		public APIResult(JSONObject body, Header[] headers){
			this.body = body;
			this.headers = headers;
		}
		
		public JSONObject getBody() {
			return body;
		}
		public void setBody(JSONObject body) {
			this.body = body;
		}
		public Header[] getHeaders() {
			return headers;
		}
		public void setHeaders(Header[] headers) {
			this.headers = headers;
		}
	}
	
	public static APIResult excuteGetWithResponseHeaders(String url,Map<String, String> headerMap,Map<String, String> paramMap) throws Exception{
		log.info("##get request url:{}",url);
		log.info("##get request paramMap:{}",paramMap);
		
		long startTime = System.currentTimeMillis();
		JSONObject jsonObj = null;
		HttpGetUtils.Response resp = null;
		try {
			
		    resp = HttpGetUtils.httpGetWithResponseHeaders(url,headerMap,paramMap,API_ENCODING);
			log.info("##get response :{}", resp.getBody());
			jsonObj = JSON.parseObject(resp.getBody());
			
		} catch (Exception e) {
			log.error("##get request err!",e);
			throw e;
		}
		log.info("##get request end! costTime：{}",System.currentTimeMillis() - startTime);

		return new APIResult(jsonObj, resp.getHeaders());
	}
	/**
	 * httpGet方式下载文件
	 * @param url
	 * @param filePath
	 * @return
	 */
	public static boolean downloadFile(String url, String filePath){
		log.info("文件下载url={}<<--->>文件存储filepath={}",url,filePath);
		if(Strings.isNullOrEmpty(url)||Strings.isNullOrEmpty(filePath)){
			log.error("文件下载url或者filePath为空!!!!");
			return false;
		}
		HttpGet httpget =null;
		try {
			org.apache.http.client.HttpClient client = HttpClients.createDefault();
			httpget = new HttpGet(url);
			HttpResponse response = client.execute(httpget);
			HttpEntity entity = response.getEntity();
			InputStream is = entity.getContent();
			File file = new File(filePath);
			file.getParentFile().mkdirs();
			FileOutputStream fileout = new FileOutputStream(file);
			/**
			 * 根据实际运行效果 设置缓冲区大小
			 */
			byte[] buffer = new byte[10 * 1024];
			int ch = 0;
			while ((ch = is.read(buffer)) != -1) {
				fileout.write(buffer, 0, ch);
			}
			is.close();
			fileout.flush();
			fileout.close();

		} catch (Exception e) {
			log.error("从swift服务器上下载文件失败,失败原因:",e);
			return false;
		}finally {
			if(httpget!=null){
				httpget.releaseConnection();
			}
		}
		return true;
	}
}
