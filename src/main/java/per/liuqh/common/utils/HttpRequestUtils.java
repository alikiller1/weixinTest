package per.liuqh.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * ClassName: HttpRequestUtils 
 * Http 请求工具类.   
 * date: 2016年3月4日 下午6:56:01
 * 
 * @author Alex.Lee 
 * @version  
 * @since JDK 1.7
 */
public class HttpRequestUtils {
	//传入参数特定类型
	private final static  String ENTITY_STRING="$ENTITY_STRING$";
	private final static  String ENTITY_FILE="$ENTITY_FILEE$";
	private final static  String ENTITY_BYTES="$ENTITY_BYTES$";
	private final static  String ENTITY_INPUTSTREAM="$ENTITY_INPUTSTREAM$";
	private final static  String ENTITY_SERIALIZABLE="$ENTITY_SERIALIZABLE$";
	private final static  List<String> SPECIAL_ENTITIY = Arrays.asList(ENTITY_STRING, ENTITY_BYTES, ENTITY_FILE, ENTITY_INPUTSTREAM, ENTITY_SERIALIZABLE);
	
	private final static  String defaultEncoding = "utf-8";
	
	//HttpClient的实例是线程安全的，可以被多个线程共享访问
	private final static CloseableHttpClient httpClient = HttpClients.createDefault();

	/**
     * 通过GET方式发起http请求
	 * @throws HttpRequestException 
     */
    public static String get(String url) throws Exception{
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse httpResponse = null;
        try {
            //用get方法发送http请求
            HttpGet get = new HttpGet(url);
            httpResponse = httpClient.execute(get);
            return getResponseBody(httpResponse);
        } catch (Exception e) {
        	throw new Exception("GET 请求"  + url + "异常！",e);
        }finally{
           close(httpClient);
        }
    }
	
	/**
	 * 
	 * 发送post请求.
	 * 
	 * @param url
	 * @param map
	 * @param encoding
	 */
	public static String postForString(String url, Map<String, Object> map, String encoding) {
		return getResponseBody(post(httpClient, url, map, encoding));
	}
	
	public static String postAddCookieForString(String url, Map<String, Object> map, String encoding) {
		return getResponseBody(postAddCookie(httpClient, url, map, encoding));
	}
	
	/**
	 * 
	 * 对send方法的再封装，仅用于处理Json字符串且 header 仅有contentType：text/json..
	 * 
	 * @param url
	 * @param jsonStringParam
	 * @param headers
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String postForString(String url, String jsonStringParam,Header... headers) throws ClientProtocolException, IOException{
		HttpResponse response = null;
		//创建请求对象
		HttpPost request = new HttpPost(url);
		//设置header信息
		if(headers != null){
			request.setHeaders(headers);
		}
		
		// 装填参数
		if(jsonStringParam != null){
			HttpEntity entity = new StringEntity(jsonStringParam);
			request.setEntity(entity);
			response = httpClient.execute(request);
			return getResponseBody(response);
		}
		return "";
	}
	
	/**
	 * 
	 * 发送post请求.
	 * 
	 * @param url
	 * @param map
	 * @param encoding
	 */
	public static HttpResponse post(HttpClient client, String url, Map<String, Object> map, String encoding) {
		HttpResponse response = null;
		HttpPost request = new HttpPost(url);
		try {
			HttpEntity entity = getHttpEntity(map, defaultEncoding);
			request.setEntity(entity);
			response = client.execute(request);
		} catch (Exception e) {
			throw new RuntimeException("请求" + url + "失败!", e);
		}

		return response;
	}

	/**
	 * 参数转换，将map中的参数，转到参数列表中
	 * 
	 * @param nameValuePairList
	 *            参数列表
	 * @param map
	 *            参数列表（map）
	 * @throws UnsupportedEncodingException
	 */
	private static HttpEntity getHttpEntity(Map<String, Object> map, String encoding)
			throws UnsupportedEncodingException {
		HttpEntity entity = null;
		List<BasicNameValuePair> nameValuePairList = new ArrayList<BasicNameValuePair>();

		if (encoding == null || "".equals(encoding)) {
			encoding = defaultEncoding;
		}

		if (map != null && map.size() > 0) {
			boolean isSpecial = false;
			// 拼接参数
			for (Entry<String, Object> entry : map.entrySet()) {
				if (SPECIAL_ENTITIY.contains(entry.getKey())) {// 判断是否在之中
					isSpecial = true;
					if (ENTITY_STRING.equals(entry.getKey())) {// string
						entity = new StringEntity(String.valueOf(entry.getValue()), encoding);
						break;
					} else if (ENTITY_BYTES.equals(entry.getKey())) {// file
						entity = new ByteArrayEntity((byte[]) entry.getValue());
						break;
					} else {
						nameValuePairList.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
					}
				} else {
					nameValuePairList.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
				}
			}
			if (!isSpecial) {
				entity = new UrlEncodedFormEntity(nameValuePairList, encoding);
			}
		}

		return entity;
	}


	/**
	 * 
	 * 关闭HttpClient
	 * 
	 * @param client
	 */
	public static void close(CloseableHttpClient client) {
		try {
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 根据请求响应的response 获取响应的内容.
	 * 
	 * @param response
	 * @return
	 */
	private static String getResponseBody(HttpResponse response) {
		String body = "";

		if (response == null) {
			return body;
		}

		try {
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				final InputStream instream = entity.getContent();
				try {
					final StringBuilder sb = new StringBuilder();
					final char[] tmp = new char[1024];
					final Reader reader = new InputStreamReader(instream, defaultEncoding);
					int l;
					while ((l = reader.read(tmp)) != -1) {
						sb.append(tmp, 0, l);
					}
					body = sb.toString();
				} finally {
					instream.close();
					EntityUtils.consume(entity);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(response);
		}

		return body;
	}
	
	/**
	 * 尝试关闭response
	 * 
	 * @param resp HttpResponse对象
	 */
	private static void close(HttpResponse response) {
		try {
			if (response == null)
				return;
			// 如果CloseableHttpResponse 是resp的父类，则支持关闭
			if (CloseableHttpResponse.class.isAssignableFrom(response.getClass())) {
				((CloseableHttpResponse) response).close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * 添加cookie发送post请求.
	 * 
	 * @param url
	 * @param map
	 * @param encoding
	 */
	public static HttpResponse postAddCookie(HttpClient client, String url, Map<String, Object> map, String encoding) {
		HttpResponse response = null;
		HttpPost request = new HttpPost(url);
		try {
			HttpEntity entity = getHttpEntity(map, defaultEncoding);
			request.setEntity(entity);
			request.addHeader(new BasicHeader("JSESSIONID", "9C941C3C820DA59173C43E2A98E31541"));
			request.addHeader(new BasicHeader("current_user", "160427104510000862-b24579fde5e592058f01b897a06e97ec"));
			response = client.execute(request);
		} catch (Exception e) {
			throw new RuntimeException("请求" + url + "失败!", e);
		}

		return response;
	}
}
