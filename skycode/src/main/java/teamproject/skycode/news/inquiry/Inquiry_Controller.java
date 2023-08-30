package teamproject.skycode.news.inquiry;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class Inquiry_Controller {

    @GetMapping(value = "/news/inquiry")
    public String newsInquiry(){
        return "news/inquiry/inquiry";
    }

    @PostMapping(value = "/news/inquiry")
    public String newInquiryPost() {
        return null;
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




