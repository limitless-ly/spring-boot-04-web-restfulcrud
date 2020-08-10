package com.atguigu.springboot.config;

import com.atguigu.springboot.component.LoginHandlerInterceptor;
import com.atguigu.springboot.component.MyLocaleResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.context.WebContext;

@Configuration
public class MyMvcConfig implements WebMvcConfigurer {


    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

        // spring boot 1.x版本中，可实现WebMvcConfigurerAdapter
        // 2.x版本中可实现WebMvcConfigurer
        // 下面代码含义为：浏览器发送“/atguigu”请求来到success
        registry.addViewController("/atguigu").setViewName("success");
    }

    // spring boot 1.5.x 中的WebMvcConfigurerAdapter已过时，在 2.x 中可使用webMvcConfigurer代替
    @Bean// 所有的webMvcConfigurer组件都会同时生效，但每个自定义组件都必须用@Bean标注，以便spring识别
    public WebMvcConfigurer webMvcConfigurer() {
        WebMvcConfigurer webMvcConfigurer = new WebMvcConfigurer() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/").setViewName("login");
                registry.addViewController("/index.html").setViewName("login");
                registry.addViewController("/main.html").setViewName("dashboard");
            }

            // 注册拦截器
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**")// /**表示任意路径下的任意请求
                        .excludePathPatterns("/index.html","/","/user/login","/asserts/**","/webjars/**");// excludePathPatterns表示将哪些请求放行，登录请求需要放行
                // spring boot已经做好了静态资源的映射，因此此处不用再放行*.css  *.js等静态资源了
                // "/asserts/**"要放行

            }

            //spring boot 2.x，在配置完addInterceptors后，某些返回页面会出现样式的丢失，这时需要添加一个addResourceHandlers静态资源处理器，
            // 将对应路径或文件标识为静态资源。两者都加上后，才算完全解决。
            /*@Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/static/**").
                        addResourceLocations("classpath:/static/").
                        addResourceLocations("/webjars/**");
            }*/
        };
        return webMvcConfigurer;
    }

    @Bean// 加载区域信息解析器
    public LocaleResolver localeResolver() {
        return new MyLocaleResolver();
    }


}
