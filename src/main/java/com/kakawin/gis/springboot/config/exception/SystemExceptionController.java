package com.kakawin.gis.springboot.config.exception;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletRequestAttributes;

@RestController
public class SystemExceptionController implements ErrorController {

	/**
	 * exception transfer to json
	 */
	@RequestMapping(value = "/error")
	public Map<String, Object> error(HttpServletRequest request) {
		DefaultErrorAttributes defaultErrorAttributes = new DefaultErrorAttributes();
		Map<String, Object> errorAttributes = defaultErrorAttributes
				.getErrorAttributes(new ServletRequestAttributes(request), false);
		String exception = (String) errorAttributes.get("exception");
		if (UnauthorizedException.class.getName().equals(exception)) {
			errorAttributes.put("message", "unauthorized!");
			errorAttributes.put("status", 403);
			errorAttributes.put("error", "");
		}
		errorAttributes.put("success", false);
		return errorAttributes;
	}

	@Override
	public String getErrorPath() {
		return "/error";
	}
}
