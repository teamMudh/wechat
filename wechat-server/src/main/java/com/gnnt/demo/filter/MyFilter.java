package com.gnnt.demo.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;


import java.io.IOException;

@Order(1)
//重点  order 序号越小 级别越高
@WebFilter(filterName = "testFilter1", urlPatterns = "/*")
public class MyFilter implements Filter {
	
	private static Logger logger = LoggerFactory.getLogger(MyFilter.class);

	public void destroy() {
		
	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		System.out.println("==============TestFilter=======");
		logger.info("==============TestFilter=======");
		  arg2.doFilter(arg0, arg1);
		
	}

	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
