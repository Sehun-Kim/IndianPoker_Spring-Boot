package indianpoker;

import indianpoker.security.AutoPlayerHandlerMethodArgumentResolver;
import indianpoker.security.BasicAuthInterceptor;
import indianpoker.security.LoginPlayerHandlerMethodArgumentResolver;
import indianpoker.security.LoginUserHandlerMethodArgumentResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public BasicAuthInterceptor basicAuthInterceptor() {
        return new BasicAuthInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(basicAuthInterceptor());
    }

    @Bean
    public LoginUserHandlerMethodArgumentResolver loginUserArgumentResolver() {
        return new LoginUserHandlerMethodArgumentResolver();
    }

    @Bean
    public LoginPlayerHandlerMethodArgumentResolver loginPlayerArgumentResolver() {
        return new LoginPlayerHandlerMethodArgumentResolver();
    }

    @Bean
    public AutoPlayerHandlerMethodArgumentResolver autoPlayerArgumentResolver() {
        return new AutoPlayerHandlerMethodArgumentResolver();
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(loginUserArgumentResolver());
        argumentResolvers.add(loginPlayerArgumentResolver());
        argumentResolvers.add(autoPlayerArgumentResolver());
    }
}
