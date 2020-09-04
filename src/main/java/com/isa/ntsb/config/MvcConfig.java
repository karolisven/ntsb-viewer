//package com.isa.ntsb.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.*;
//import org.springframework.web.servlet.view.InternalResourceViewResolver;
//
//@EnableWebMvc
//@Configuration
//public class MvcConfig implements WebMvcConfigurer {
//
//    public void addViewControllers(ViewControllerRegistry registry) {
//
//        registry.addViewController("/login").setViewName("login");
//        registry.addViewController("/").setViewName("index");
//    }
//
//    @Override
//    public void configureViewResolvers(final ViewResolverRegistry registry) {
//        registry.jsp("/", ".jsp");
//    }
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("swagger-ui.html")
//                .addResourceLocations("classpath:/META-INF/resources/");
//
//        registry.addResourceHandler("/webjars/**")
//                .addResourceLocations("classpath:/META-INF/resources/webjars/");
//    }
//}
