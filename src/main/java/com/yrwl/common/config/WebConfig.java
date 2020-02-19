package com.yrwl.common.config;

import com.yrwl.interceptor.AuthInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author  shentao
 * @date    2019-12-24
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public AuthInterceptor getAuthInterceptor(){
        return new AuthInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getAuthInterceptor()).addPathPatterns("/**/api/**")
            .excludePathPatterns("/**/thirdPlatform/**")
            .excludePathPatterns("/**/accessToken/**");
            //.excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**", "/csrf/**")
            //.excludePathPatterns("/doc.html/**");
    }
}
