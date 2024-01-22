package com.example.usermngt.configuration;

import com.example.usermngt.util.TokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new TokenInterceptor()).addPathPatterns("/api/v1/**");

//        registry.addInterceptor(new TokenInterceptor())
//                .addPathPatterns("/**") // 匹配所有路径
//                .excludePathPatterns("/api/v1/**"); // 排除某个路径
    }

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        // 添加CORS配置
//        registry.addMapping("/api/v1/**")
//                .allowedOrigins("*") // 允许任何来源访问
//                .allowedMethods("GET", "POST", "PUT", "DELETE") // 允许的方法
//                .allowedHeaders("*") // 允许的请求头
//                .maxAge(3600); // 预检请求的缓存时间（秒）
//    }
}
