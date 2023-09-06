//package teamproject.skycode.review;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//    // savePath 에 경로를 resourcePath 이름으로 접근을 하겠다라는 설정
////    private String resourcePath = "/review_img/**"; // view 에서 접근할 경로
////    @Value("${savePath}")
////    String savePath;
////    private String savePath = "file:///j:/SkyCodeProject/review_img"; // 실제 파일 저장 경로(win)
////    private String savePath = "file:///Users/사용자이름/springboot_img/"; // 실제 파일 저장 경로(mac)
//
////    @Override
////    public void addResourceHandlers(ResourceHandlerRegistry registry) {
////        registry.addResourceHandler("/review_img/**")
////                .addResourceLocations("file:///SkyCodeProject/review_img");
////
////    }
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/review/**")
////                .addResourceLocations("file:///C:/review_img/");
//                .addResourceLocations("file:///C:/Users/GR805/Desktop/SkyCodeProject/skycode/src/main/resources/static/review_img");
//
//
//    }
//}
