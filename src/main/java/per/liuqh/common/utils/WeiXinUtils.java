/** 
 * Project Name:userscorejob_dev 
 * File Name:WeiXinUtils.java 
 * Package Name:ppdai.risk.userscorejob.utils 
 * Date:2016年3月8日下午10:01:01 
 * Copyright (c) 2016,Alex.Lee All Rights Reserved. 
 * 
 */  
package per.liuqh.common.utils;
import com.google.gson.Gson;

/** 
 * ClassName: WeiXinUtils 
 * 微信工具类 .   
 * date: 2016年3月8日 下午10:01:01
 * 
 * @author Alex.Lee 
 * @version  
 * @since JDK 1.7 
 */
public class WeiXinUtils {

	
	private final static String corpid = "wx2cbf9f677c40410f"; //企业应用的id，整型。可在应用的设置页面查看
	private final static String corpsecret = "ydqnhzecWZ4-I3ayMXkXsra8lvpy2ecGuhSzNycKB2oH2nehnjuJMYut7EK0GZNE";
	private final static String getAccessTokenBaseUrl = "https://qyapi.weixin.qq.com/cgi-bin/gettoken";
	private final static String sendMessageBaseUrl = "https://qyapi.weixin.qq.com/cgi-bin/message/send";
	
	private static String getAccessToken(String url) {
		try {
			String s= HttpRequestUtils.get(url);
			Token t= new Gson().fromJson(s, Token.class);
			return t.getAccess_token();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static String sendTextMessage(TextRequestEntity textRequestEntity){
		if(textRequestEntity == null )
			return "";
		
		//1.获取access_token
		String access_token = getAccessToken(new StringBuilder(getAccessTokenBaseUrl).append("?corpid=").append(corpid)
				.append("&corpsecret=").append(corpsecret).toString());
		
		String sendMessageUrl = new StringBuilder(sendMessageBaseUrl).append("?access_token=").append(access_token).toString();
		try {
			String JsonStr = new Gson().toJson(textRequestEntity);
			return HttpRequestUtils.postForString(sendMessageUrl, JsonStr, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	public static void main(String[] args) {
//		System.err.println(WeiXinUtils.getAccessToken(new StringBuilder(getAccessTokenBaseUrl).append("?corpid=").append(corpid)
//				.append("&corpsecret=").append(corpsecret).toString()));
		String sysconfig = "/sysconfig.properties";
		
		TextRequestEntity textRequestEntity = new TextRequestEntity();
		textRequestEntity.setAgentid(3);
		textRequestEntity.setTouser("wangyijun333");
		textRequestEntity.setMsgtype("text");
		textRequestEntity.setSafe("0");
		Text t=new Text();
		t.setContent("test1");
		textRequestEntity.setText(t);
		
		System.out.println(WeiXinUtils.sendTextMessage(textRequestEntity));
	}
}
