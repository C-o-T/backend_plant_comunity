package com.green.backend_plant_comunity.util;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {


   @Override
   public void addResourceHandlers(ResourceHandlerRegistry registry) {
      // 실제 업로드 폴더의 절대 경로
      registry.addResourceHandler("/upload/**")
              .addResourceLocations("file:///D:/01-STUDY/dev/team/upload/"); // <- 여기를 본인의 경로로!
   }
}
