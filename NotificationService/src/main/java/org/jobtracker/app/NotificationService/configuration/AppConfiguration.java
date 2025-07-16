package org.jobtracker.app.NotificationService.configuration;

import feign.RequestInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class AppConfiguration {

    @Bean
    public NewTopic topic() {
        return TopicBuilder.name("job-tracker")
                .build();
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            ServletRequestAttributes servletRequestAttributes =
                    (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

            HttpServletRequest httpServletRequest =
                    servletRequestAttributes.getRequest();

            String header = httpServletRequest.getHeader("Authorization");
            if(header != null) {
                requestTemplate.header("Authorization",header);
            }
         };
    }
}
