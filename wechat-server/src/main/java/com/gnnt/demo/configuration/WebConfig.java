package com.gnnt.demo.configuration;

import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gnnt.demo.listener.MyListener;


/**
 * Created by LF on 2017/3/5.
 */
@Configuration
public class WebConfig {


/*    @Bean
    public ServletListenerRegistrationBean<MyListener> getDemoListener() {
        ServletListenerRegistrationBean<MyListener> registrationBean
                = new ServletListenerRegistrationBean<>();
        registrationBean.setListener(new MyListener());
        registrationBean.setOrder(1);
        return registrationBean;
    }*/

}