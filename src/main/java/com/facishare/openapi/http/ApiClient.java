package com.facishare.openapi.http;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import com.facishare.openapi.util.JsonUtil;
import com.facishare.openapi.util.Log;
import com.facishare.openapi.util.UrlUtil;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ApiClient {
	private static int httpCode = -1;
	private static HttpPost post;
	private static HttpGet get;
	private static HttpResponse response;
	private static HttpEntity entity;
	static String result="";
	
	private final static ThreadLocal<CloseableHttpClient> CLIENT_CACHE = new InheritableThreadLocal<CloseableHttpClient>() {
		@Override
		protected CloseableHttpClient initialValue() {

			SSLContext sslContext = null;
			try {
				sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
					public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
						return true;
					}
				}).build();
			} catch (KeyManagementException e) {
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (KeyStoreException e) {
				e.printStackTrace();
			}

			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);

			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(HttpConstants.Connection_Timeout)
					.setConnectTimeout(HttpConstants.Connection_Timeout)
					.setConnectionRequestTimeout(HttpConstants.Connection_Timeout).build();

			CloseableHttpClient client = HttpClients.custom().setDefaultRequestConfig(requestConfig)
					.setSSLSocketFactory(sslsf).build();

			return client;

		}
	};

	private static Header JSON_TYPE_HEADER = new BasicHeader(HttpConstants.Content_Type, HttpConstants.JSON_MEDIA_TYPE);

	public static void closeConnection(){
		try {
			CLIENT_CACHE.get().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static int getHttpCode() {
		return httpCode;
	}

	public static String Post(String url, Map<String, Object> httpbody) {
		Log.logInfo("Url:"+UrlUtil.getTotalUrl(url, null));
		post = new HttpPost(UrlUtil.getTotalUrl(url, null));
		post.setHeader(JSON_TYPE_HEADER);
		StringEntity s;
		String jsonBody = JsonUtil.MapToJson(httpbody);
		s = new StringEntity(jsonBody,"utf-8");
		s.setContentType("application/json");    
		post.setEntity(s);
		try {
			response = CLIENT_CACHE.get().execute(post);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		entity = response.getEntity();
		httpCode = response.getStatusLine().getStatusCode();

		try {
			result = EntityUtils.toString(entity);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String Get(String url){
		Log.logInfo("Url:"+UrlUtil.getTotalUrl(url, null));
		get = new HttpGet(UrlUtil.getTotalUrl(url, null));
		get.setHeader(JSON_TYPE_HEADER);

		try {
			response = CLIENT_CACHE.get().execute(get);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		entity = response.getEntity();
		httpCode = response.getStatusLine().getStatusCode();

		try {
			result = EntityUtils.toString(entity);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static String Post(String url, String jsonBody){

		post = new HttpPost(UrlUtil.getTotalUrl(url, null));
		post.setHeader(JSON_TYPE_HEADER);

		StringEntity s;
		s = new StringEntity(jsonBody,"utf-8");
		s.setContentType("application/json");
		//s.s
		post.setEntity(s);
		try {
			response = CLIENT_CACHE.get().execute(post);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		entity = response.getEntity();
		httpCode = response.getStatusLine().getStatusCode();

		try {
			result = EntityUtils.toString(entity);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	public static String Post(String url, String jsonBody,HashMap<String ,Object> requestHeads){

		post = new HttpPost(UrlUtil.getTotalUrl(url, null));
		post.setHeader(JSON_TYPE_HEADER);
		post.setHeader("Cookie",requestHeads.get("Cookie").toString());
		StringEntity s;
		s = new StringEntity(jsonBody,"utf-8");
		s.setContentType("application/json");
		post.setEntity(s);

		try {
			response = CLIENT_CACHE.get().execute(post);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		entity = response.getEntity();
		httpCode = response.getStatusLine().getStatusCode();

		try {
			result = EntityUtils.toString(entity);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}
