package com.gnnt.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;


/**
 * Created by Song on 2017/2/15.
 * 
 */
@SpringBootApplication(scanBasePackages = "com.gnnt.demo")
@ServletComponentScan
public class Entry {
    public static void main(String[] args) throws Exception {
        //SpringApplication.run(Entry.class, args);
        
       SpringApplication app = new SpringApplication(Entry.class);
        //关闭Banner
        //app.setBannerMode(Mode.OFF);
        //设置监听
        //app.addListeners(new MyListener());
        app.run(args);


    }
}