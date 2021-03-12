package com.yiqiandewo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MVCConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/about").setViewName("about");
        registry.addViewController("/blog").setViewName("blog");
        registry.addViewController("/tags").setViewName("tags");
        registry.addViewController("/archives").setViewName("archives");
        registry.addViewController("/types").setViewName("types");
        registry.addViewController("/blogs-input").setViewName("/admin/blogs-input");


    }

}
