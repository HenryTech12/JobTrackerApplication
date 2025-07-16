package org.jobtracker.app.JobTracker.configuration;

import feign.RequestInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class UserFeignConfiguration {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

            if(attributes != null) {
                HttpServletRequest servletRequest = attributes.getRequest();
                String header = servletRequest.getHeader("Authorization");
                if(header != null) {
                    requestTemplate.header("Authorization",header);
                }
            }
        };
    }

    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }
}
