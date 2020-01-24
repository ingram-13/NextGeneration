package personal.personalblogreturn.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by Ljh
 */

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/").setViewName("/public/index");
        registry.addViewController("/index.html").setViewName("/public/index");
        registry.addViewController("/blog.html").setViewName("/public/blog");
        registry.addViewController("/types.html").setViewName("/public/types");
        registry.addViewController("/tags.html").setViewName("/public/tags");
        registry.addViewController("/aboutMe.html").setViewName("/public/aboutMe");
        registry.addViewController("/file.html").setViewName("/public/file");
        registry.addViewController("/blogManage.html").setViewName("/admin/blogManage");
        registry.addViewController("/blogRelease.html").setViewName("/admin/blogRelease");
    }


}
