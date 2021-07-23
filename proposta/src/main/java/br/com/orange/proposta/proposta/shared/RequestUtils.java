package br.com.orange.proposta.proposta.shared;

import javax.servlet.http.HttpServletRequest;

public class RequestUtils {
	
	public static String retornaIp(HttpServletRequest request) {
		return request.getHeader("X-Forwarded-For") != null ? request.getHeader("X-Forwarded-For")
				: request.getRemoteAddr();
	}

	public static String retornaUserAgent(HttpServletRequest request) {
		return request.getHeader("User-Agent");
	}
}
