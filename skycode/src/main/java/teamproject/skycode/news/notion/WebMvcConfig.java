package teamproject.skycode.news.notion;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@Configuration
public class WebMvcConfig {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("notionImg/**")
                .addResourceLocations("file:///SkyCodeProject/notionImg");
    }
}
