package per.liuqh.common.utils;

import javax.servlet.http.HttpServletRequest;

public class StringUtils {
	public static boolean isNotBlank(String str){
		if(null!=str&&!"".equals(str)){
			return true;
		}
		return false;
	}
	public static boolean isBlank(String str){
		return !isNotBlank(str);
	}
	public static void main(String[] args) {
		System.out.println(StringUtils.isBlank("abc"));
	}
	/**
	 * 获得用户远程地址
	 */
	public static String getRemoteAddr(HttpServletRequest request) {
		String remoteAddr = request.getHeader("X-Real-IP");
		if (isNotBlank(remoteAddr)) {
			remoteAddr = request.getHeader("X-Forwarded-For");
		} else if (isNotBlank(remoteAddr)) {
			remoteAddr = request.getHeader("Proxy-Client-IP");
		} else if (isNotBlank(remoteAddr)) {
			remoteAddr = request.getHeader("WL-Proxy-Client-IP");
		}
		return remoteAddr != null ? remoteAddr : request.getRemoteAddr();
	}
}
