package com.gnnt.demo.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.ServletContextInitializer;


@WebListener("MyListener")
public class MyListener implements  ServletContextListener,ServletContextInitializer  {
	
	  private static Logger logger = LoggerFactory.getLogger(MyListener.class);

	public void contextDestroyed(ServletContextEvent arg0) {

		logger.info("==========================================监听启动contextDestroyed");
		System.out.println("==========================================监听启动contextDestroyed");
		
	}

	public void contextInitialized(ServletContextEvent arg0) {
		
		logger.info("==========================================监听启动contextInitialized");
		System.out.println("==========================================监听启动contextInitialized");
		
	}

	public void onStartup(ServletContext servletContext)
			throws ServletException {
		servletContext.addListener(MyListener.class);
	}

}
