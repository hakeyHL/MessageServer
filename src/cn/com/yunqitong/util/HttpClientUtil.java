package cn.com.yunqitong.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;


public class HttpClientUtil {
	private static Logger log = Logger.getLogger(HttpClientUtil.class);
	public static String doPostHttp(String url, String txt) throws ClientProtocolException, IOException{
		log.info("发送请求："+url);
		log.info("请求内容："+txt);
		String responseText = null;
		CloseableHttpClient closeableHttpClient =  HttpClients.createDefault();
		HttpPost method = new HttpPost(url);
		StringEntity entity = new StringEntity(txt, "utf-8");// 解决中文乱码问题
		entity.setContentEncoding("UTF-8");
		entity.setContentType("application/json");
		method.setEntity(entity);	
		HttpResponse httpResponse = closeableHttpClient.execute(method);
		HttpEntity httpEntity2 = httpResponse.getEntity();
		if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			String result = EntityUtils.toString(httpEntity2);
			responseText  = result;
		}
		log.info("响应结果："+responseText);
		closeableHttpClient.close();
		return responseText;
	}
	
	public static String doPostHttps(String url, List<NameValuePair> nvps) throws KeyManagementException, NoSuchAlgorithmException, ClientProtocolException, IOException  {
		String responseText = null;
		CloseableHttpClient closeableHttpClient = createHttpsClient();
		HttpPost httppost = new HttpPost(url);
		httppost.setEntity(new UrlEncodedFormEntity(nvps));
		HttpResponse httpResponse = closeableHttpClient.execute(httppost);
		HttpEntity httpEntity2 = httpResponse.getEntity();
		System.out.println("httpResponse.getStatusLine().getStatusCode():" + httpResponse.getStatusLine().getStatusCode());
		if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			String result = EntityUtils.toString(httpEntity2);
			responseText  = result;
		} else {
			String result = EntityUtils.toString(httpEntity2);
			responseText  = result;
		}
		closeableHttpClient.close();
		return responseText;
	}
	
	public static CloseableHttpClient createHttpsClient() throws NoSuchAlgorithmException, KeyManagementException{
		X509TrustManager x509mgr = new X509TrustManager() {
			@Override
			public void checkClientTrusted(X509Certificate[] xcs, String string) {
			}

			@Override
			public void checkServerTrusted(X509Certificate[] xcs, String string) {
			}

			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};
		SSLContext sslContext = SSLContext.getInstance("TLS");
		sslContext.init(null, new TrustManager[] { x509mgr }, new java.security.SecureRandom());
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext,
				SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		return HttpClients.custom().setSSLSocketFactory(sslsf).build();
	}
	public static void sendAppMessage(String	message,HttpServletResponse response) {
		log.info("发送信息="+message);
		PrintWriter out = null;
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		try {
			out = response.getWriter();
			out.println(message);
		} catch (Exception ee) {
		} finally {
			out.close();
		}
	}
	
	public static String getJsonFromRequest(HttpServletRequest request) {
		StringBuffer info = new java.lang.StringBuffer();
		try {
			InputStream in = request.getInputStream();
			BufferedInputStream buf = new BufferedInputStream(in);
			byte[] buffer = new byte[1024];
			int iRead;
			while ((iRead = buf.read(buffer)) != -1) {
				info.append(new String(buffer, 0, iRead, "UTF-8"));
			}
		} catch (Exception ee) {
		}
		log.info("接受信息="+info.toString());
		return URLDecoder.decode(info.toString());
	}
}
