package com.telekom.config;

import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.Properties;


@Configuration
@EnableWebMvc
@ComponentScan("com.telekom")
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resource/**").addResourceLocations("/WEB-INF/resource/");
       // registry.addResourceHandler("/springLine/resource/**").addResourceLocations("/WEB-INF/resource/");
    }


    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setSuffix(".jsp");
        viewResolver.setPrefix("/WEB-INF/Views/");
        return viewResolver;
    }

    @Bean
    @Scope ("prototype")
    public Logger logger(InjectionPoint injectionPoint){
        return Logger.getLogger(injectionPoint.getMember().getDeclaringClass());
    }

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("relay.t-systems.ru");
        mailSender.setPort(25);

        mailSender.setUsername("springlineproject@gmail.com");
        mailSender.setPassword("2207!sun");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        /*
       props.put("mail.smtp.proxy.host", "proxy.t-systems.ru");
        props.put("mail.smtp.proxy.port", "3128");
        props.put("mail.smtp.proxy.user", "ekochuro");
        props.put("mail.smtp.proxy.password", "moon19933");
       props.put("proxySet", "true");
*/
        props.put("mail.debug", "true");
        return mailSender;
    }

    @Bean
    public VelocityEngine velocityEngine(){
        VelocityEngine velocityEngine = new VelocityEngine();
        Properties props = new Properties();
        props.put("resource.loader", "class");
        props.put("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        //props.put("resource.loader.path", "/WEB-INF/resource/email/");
        return velocityEngine;
    }

}
