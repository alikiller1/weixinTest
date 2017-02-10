/*package per.liuqh.common.utils;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.valves.ValveBase;
import org.apache.tomcat.util.buf.MessageBytes;
import org.apache.tomcat.util.http.MimeHeaders;
import org.apache.tomcat.util.http.ServerCookie;

public class SessionCrossDomainValve extends ValveBase {
	public void invoke(Request request, org.apache.catalina.connector.Response response)
			throws IOException, ServletException {
		HttpSession session=request.getSession(true);

		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie = cookies[i];
				System.out.println("CrossSubdomainSessionValve: Cookie name is " + cookie.getName());
				if ("JSESSIONID".contentEquals(cookie.getName())) {
					replaceCookie(request, response, cookie);
				}
			}
		}else{
			Cookie cookie=new Cookie("JSESSIONID", session.getId());
			cookie.setPath("/");
			replaceCookie(request, response, cookie);
		}
	
		getNext().invoke(request, response);
	}

	protected void replaceCookie(Request request, Response response, Cookie cookie) {
		Cookie newCookie = new Cookie(cookie.getName(), cookie.getValue());
		if (cookie.getPath() != null){
			newCookie.setPath(cookie.getPath());
		}else{
			newCookie.setPath("/");
		}
			
		newCookie.setDomain(getCookieDomain(request));
		newCookie.setMaxAge(cookie.getMaxAge());
		newCookie.setVersion(cookie.getVersion());
		if (cookie.getComment() != null)
			newCookie.setComment(cookie.getComment());
		newCookie.setSecure(cookie.getSecure());

		if (response.isCommitted()) {
			System.out.println("CrossSubdomainSessionValve: response was already committed!");
		}

		MimeHeaders headers = response.getCoyoteResponse().getMimeHeaders();
		int i = 0;
		for (int size = headers.size(); i < size; i++)
			if (headers.getName(i).equals("Set-Cookie")) {
				MessageBytes value = headers.getValue(i);
				if (value.indexOf(cookie.getName()) >= 0) {
					StringBuffer buffer = new StringBuffer();
					ServerCookie.appendCookieValue(buffer, newCookie.getVersion(), newCookie.getName(),
							newCookie.getValue(), newCookie.getPath(), newCookie.getDomain(), newCookie.getComment(),
							newCookie.getMaxAge(), newCookie.getSecure(), true);
					System.out.println("CrossSubdomainSessionValve: old Set-Cookie value: " + value.toString());
					System.out.println("CrossSubdomainSessionValve: new Set-Cookie value: " + buffer);
					value.setString(buffer.toString());
				}
			}
	}
	protected String getCookieDomain(Request request) {
		String cookieDomain = request.getServerName();
		String[] parts = cookieDomain.split("\\.");
		if (parts.length >= 2)
			cookieDomain = parts[(parts.length - 2)] + "." + parts[(parts.length - 1)];
		return "." + cookieDomain;
	}

	public String toString() {
		return "CrossSubdomainSessionValve[container=" + this.container.getName() + ']';
	}
}*/