package com.wanhuchina.common.util.http.base;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import javax.security.auth.x500.X500Principal;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.List;


/**
  * @ClassName: HttpClientFactory
  * @Description: 对http连接进行池化管理 
  * @author shenguanhao
  * @date 2016年07月25日 下午6:23:24
 */
public class HttpClientFactory {
	
	private static Logger log = LoggerFactory.getLogger(HttpClientFactory.class);
	
	private static final Integer connectionRequestTimeout = 30000;// 30秒
	private static final Integer connectionTimeoutTime = 30000;// 30秒
	private static final Integer soTimeoutTime = 30000;// 30秒
	
	
	private static final int MaxTotal = 300;
	private static final int DefaultMaxPerRoute = 300;
	private static final int MaxPerRoute = 20;
	
	private static HttpClientBuilder httpBulder = null;
	
	
//	private static Config config = ConfigProvider.getConfigInstance();

    private static final String contentType = "Content-Type";
    private static final String BASP_CONTENT_TYPE = "application/Json";
    private static KeyStore myKeyStore = null;
    private static KeyStore myTrustStore = null;
	
	static{
		//init();
	}
	
	public static HttpClient getClient(){
        CloseableHttpClient httpClient = httpBulder.build();  
		return httpClient;
	}
	
	private static void init(){
		try{
			ConnectionSocketFactory plainsf = PlainConnectionSocketFactory.getSocketFactory();
	        LayeredConnectionSocketFactory sslsf = SSLConnectionSocketFactory.getSocketFactory();
	        
	        /*SSLContext sslContext = SSLContextBuilder.create()
    		.loadKeyMaterial(myKeyStore, "devops2013")
    		.loadTrustMaterial(myTrustStore, new BaspTrustStrategy()).build();
    
    		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
    		sslContext,
            new String[] { "SSLv2Hello","SSLv3","TLSv1","TLSv1.1","TLSv1.2"},
            null,
            SSLConnectionSocketFactory.getDefaultHostnameVerifier());*/
	        
	        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
	                .register("http", plainsf)
	                .register("https", sslsf)
	                .build();
	        
	        PoolingHttpClientConnectionManager poolConnManager = new PoolingHttpClientConnectionManager(registry);
	        // 将最大连接数增加到200
	        poolConnManager.setMaxTotal(MaxTotal);
	        // 将每个路由基础的连接最大  单个路由 跟总的一致
	        poolConnManager.setDefaultMaxPerRoute(DefaultMaxPerRoute);
	        
	        // 将目标主机的最大连接数增加到50
	        HttpHost localhost = new HttpHost("basp-api.local-kaiyuan.com",8989);
	        poolConnManager.setMaxPerRoute(new HttpRoute(localhost), MaxPerRoute);
	        
	        SocketConfig socketConfig = SocketConfig.custom().setSoTimeout(soTimeoutTime).build();  
	        poolConnManager.setDefaultSocketConfig(socketConfig);  
	        
	        RequestConfig requestConfig = RequestConfig.custom()
					.setConnectionRequestTimeout(connectionRequestTimeout)  
	                .setConnectTimeout(connectionTimeoutTime)
	                .setSocketTimeout(soTimeoutTime).build();
	        
	        //请求重试处理
	        HttpRequestRetryHandler httpRequestRetryHandler = new HttpRequestRetryHandler() {
	            public boolean retryRequest(IOException exception,int executionCount, HttpContext context) {
	                if (executionCount >= 5) {// 如果已经重试了5次，就放弃                    
	                    return false;
	                }
	                if (exception instanceof NoHttpResponseException) {// 如果服务器丢掉了连接，那么就重试                    
	                    return true;
	                }
	                if (exception instanceof SSLHandshakeException) {// 不要重试SSL握手异常                    
	                    return false;
	                }                
	                if (exception instanceof InterruptedIOException) {// 超时                    
	                    return false;
	                }
	                if (exception instanceof UnknownHostException) {// 目标服务器不可达                    
	                    return false;
	                }
	                if (exception instanceof ConnectTimeoutException) {// 连接被拒绝                    
	                    return false;
	                }
	                if (exception instanceof SSLException) {// ssl握手异常                    
	                    return false;
	                }
	                 
	                HttpClientContext clientContext = HttpClientContext.adapt(context);
	                HttpRequest request = clientContext.getRequest();
	                // 如果请求是幂等的，就再次尝试
	                if (!(request instanceof HttpEntityEnclosingRequest)) {                    
	                    return true;
	                }
	                return false;
	            }
	        };  
	        
	        httpBulder = HttpClients.custom()  
            .setConnectionManager(poolConnManager)
            .setDefaultRequestConfig(requestConfig)
            .setRetryHandler(httpRequestRetryHandler);
	         
		}catch(Exception e){
			log.error("httpClient初始化异常了！");
			throw new RuntimeException("初始化HttpClient连接池失败了！",e);
		}
	}
		
		
	protected static class BaspTrustStrategy implements TrustStrategy {

        public boolean isTrusted(final X509Certificate[] chain, final String authType) throws CertificateException {
            if (chain.length > 0 && myTrustStore != null) {
                for (X509Certificate cert : chain) {
                    X500Principal principal = cert.getIssuerX500Principal();
                    try {
                        List<String> aliases = Collections.list(myTrustStore.aliases());
                        for (String alias : aliases) {
                            X509Certificate tCert = (X509Certificate) myTrustStore.getCertificate(alias);
                            X500Principal tprincipal = tCert.getIssuerX500Principal();
                            if (principal.equals(tprincipal)) {
                                cert.verify(tCert.getPublicKey());
                                return true;
                            }
                        }
                    } catch (KeyStoreException | InvalidKeyException | NoSuchAlgorithmException | NoSuchProviderException | SignatureException e) {
                        log.error("Can't trust this server certificate, error: ", e);
                    }
                }
            }
            return false;
        }
    }

}
