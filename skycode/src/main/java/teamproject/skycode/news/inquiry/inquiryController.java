package teamproject.skycode.news.inquiry;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class inquiryController {

    @GetMapping(value = "/news/inquiry")
    public String newsInquiry(){
        return "news/inquiry/inquiry";
    }

    @GetMapping(value = "/news/inquiryList")
    public String newsInquiryList(){
        return "news/inquiry/inquiryList";
    }

    @GetMapping(value = "/news/inquiryShow")
    public String newsInquiryShow(){
        return "news/inquiry/inquiryShow";
    }
}
