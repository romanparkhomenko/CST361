package com.nilfactor.activity3.filter;
import java.io.IOException;

import javax.interceptor.Interceptors;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.nilfactor.activity3.utility.LogInterceptor;
import com.nilfactor.activity3.utility.ServiceService;


@WebFilter(filterName = "AuthFilter", urlPatterns = { "*.xhtml" })
@Interceptors(LogInterceptor.class)
public class LoginFilter implements Filter {
	@Override
	@Interceptors(LogInterceptor.class)
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain c) throws IOException, ServletException {
		try {
			// cast response/request to HttpServelet versions
			HttpServletRequest req = (HttpServletRequest) request;
			HttpServletResponse res = (HttpServletResponse) response;
			HttpSession ses = req.getSession(false);
			
			// get the request string
			String reqURI = req.getRequestURI();
			
			// logic of what to do if the path is x and user is or is not logged in
			if (ses != null && ses.getAttribute("username") != null && reqURI.contains("/login.xhtml")) { // we are already logged in
				res.sendRedirect(req.getContextPath() + "/app/home.xhtml");
			} else if (reqURI.contains("/login.xhtml")
					|| (ses != null && ses.getAttribute("username") != null)
					|| reqURI.contains("/register.xhtml") // registration
					|| reqURI.contains("javax.faces.resource")) { // we either do not need to login or are logged in
				c.doFilter(request, response);
			} else { // user needs to login to access this page
				res.sendRedirect(req.getContextPath() + "/faces/login.xhtml");
			}
		} catch (Exception e) {
			ServiceService.getLogger("LoginFilter").error(e.getMessage());
			ServiceService.getLogger("LoginFilter").error(e.getStackTrace());
		}
	}

	@Override
	@Interceptors(LogInterceptor.class)
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	@Interceptors(LogInterceptor.class)
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
	}
}
