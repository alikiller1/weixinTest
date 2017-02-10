package per.liuqh.common.utils.weixin;

import java.lang.reflect.Type;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.collections.map.LinkedMap;

import com.google.gson.Gson;

import net.sf.json.JSONObject;
import per.liuqh.common.utils.HttpRequest;
import per.liuqh.common.utils.StringUtils;



public class WeixinAPIRequestUtils {
	
	private  static String appid="wx8e45721cbba90cae";
	private static String appsecret="104514e6fda2da941a50ff0dc203d4e6";
	public static String getAccesstokenUrl="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={0}&secret={1}";
	public static String getImgIds="https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token={0}";
	public static String getAccestoken() throws Exception{
		String resp = HttpRequest.sendPost(MessageFormat.format(getAccesstokenUrl, appid,appsecret),"");
		System.out.println("getAccestoken-->resp="+resp);
		if(StringUtils.isNotBlank(resp)){
			JSONObject  result=JSONObject.fromObject(resp);
			resp = "{\"access_token\":\"xxJX0MB4A\",\"exin\":7200}";
			if(result.get("access_token")!=null){
				return result.get("access_token").toString();
			}
		}
		throw new Exception("获取accestoken失败");
	}
	public static String getImgId() throws Exception{
		
		Map<String,String> body=new HashMap<String, String>();
		body.put("type", "voice");
		body.put("offset", "0");
		body.put("count", "2");
		String token=getAccestoken();
		String resp = HttpRequest.sendPost(MessageFormat.format(getImgIds, token),GsonUtils.getJson(body));
		System.out.println(resp);
		return "";
	}
	
	public static void main(String[] args) throws Exception {
		getImgId();
	}
	
	

}
