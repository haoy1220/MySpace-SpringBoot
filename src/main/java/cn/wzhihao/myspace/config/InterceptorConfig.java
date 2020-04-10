package cn.wzhihao.myspace.config;

import cn.wzhihao.myspace.filter.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getJwtFilter())
                .addPathPatterns("/**");//拦截所有请求
    }

    @Bean
    public JwtFilter getJwtFilter() {
        return new JwtFilter();
    }
}
