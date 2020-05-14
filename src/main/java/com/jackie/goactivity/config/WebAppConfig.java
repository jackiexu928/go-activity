package com.jackie.goactivity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created with IntelliJ IDEA
 * Description:
 *
 * @author xujj
 * @date 2020-04-29
 */
@Configuration
public class WebAppConfig implements WebMvcConfigurer {

    @Bean
    public InterceptorConfig interceptorConfig() {
        return new InterceptorConfig();
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptorConfig())
                .addPathPatterns("/**")
                .excludePathPatterns("/account/login")
                .excludePathPatterns("/backDoor/**");
    }
}
