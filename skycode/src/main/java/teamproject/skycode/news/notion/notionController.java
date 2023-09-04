package teamproject.skycode.news.notion;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/news")
@RequiredArgsConstructor

public class notionController {

    @Autowired
    private NotionRepository notionRepository;

    @Autowired
    private NotionService notionService;

    @Autowired
    private NotionViewCountService notionViewCountService;

//    private final NotionFileService notionFileService;
//
//    @Autowired
//    public notionController(NotionService notionService, NotionFileService notionFileService) {
//        this.notionService = notionService;
//        this.notionFileService = notionFileService;
//    }

    @GetMapping(value = "/notionUp")
    public String newsNotionUp(Model model){
        model.addAttribute("notionForm", new NotionForm());
        return "news/notion/notionUp";
    }
    @PostMapping(value = "notionUp/create")
    public String createNotion(NotionForm form){
        Notion notion = form.toEntity();
        System.out.println("1234");
        notionRepository.save(notion);
        return "redirect:/news/notion/notion";
    }

    @GetMapping("/notion/notion")
    public String notionList(Model model){
        List<Notion> notions = notionService.getAllNotions();
        long totalNotionCount = notionService.getTotalNotionCount();
        System.out.println("1");

        model.addAttribute("notions", notions);
        model.addAttribute("totalNotionCount", totalNotionCount);

        return "/news/notion/notion";
    }

    @PostMapping("/notion/notion")
    public String submitInquiry(@ModelAttribute NotionForm notionForm, Model model) {
        Notion savedNotion = notionService.saveNotion(notionForm); // Save or update inquiry
        System.out.println("제발1");
        if (savedNotion != null) {
            Long id = savedNotion.getId(); // Get the id of the saved/updated inquiry

            // Now, based on the id, determine the URL to redirect to
            String redirectUrl = "redirect:/news/notion/notionSub" + id; // Adjust the URL pattern according to your mapping
            System.out.println("제발2");

            return redirectUrl;
        }

        // Handle error case if savedInquiry is null
        // You can return an error view or redirect to an error page
        return "error"; // Change to the appropriate view name

    }


    @GetMapping("/notion/notionSub/{id}")
    public String showNotionById(@PathVariable Long id, Model model) {
        Notion notion = notionService.getNotionById(id);

        if (notion != null) {
            NotionViewCount viewCount = notionViewCountService.notionViewCount(id);

            model.addAttribute("notion", notion);
            model.addAttribute("viewCount", viewCount.getCount());
            System.out.println("좀");
            return "news/notion/notionSub"; // Adjust the view name
        }
        System.out.println("제발");

        // Handle case when inquiry is not found
        return "error"; // Change to the appropriate view name
    }

    // Handle file upload
//    @PostMapping("/uploadFile")
//    public String uploadFile(@RequestParam("file") MultipartFile file) {
//        NotionFile notionFile = new NotionFile(file);
//        notionFile.setFile(file);
//        String fileName = notionFileService.storeFile(notionFile);
//        System.out.println("이것은 이미지 업로드");
//        return "File uploaded successfully: " + fileName;
//    }
//
//    // Implement other controller methods for your Notion entity
//    // ...
//
//    // Example method for saving Notion entity with uploaded file
//    @PostMapping("/createFile")
//    public String createNotionFile(@ModelAttribute NotionForm notionForm) {
//        try {
//            // Save the uploaded file and get the saved file name
//            String uploadedFileName = saveUploadedFile(notionForm.getFile());
//
//            // Create a new Notion entity and set its properties
//            Notion newNotion = new Notion();
//            newNotion.setType(notionForm.getType());
//            newNotion.setNotionTitle(notionForm.getNotionTitle());
//            newNotion.setNotionContent(notionForm.getNotionContent());
//            newNotion.setFileName(uploadedFileName);
//            System.out.println("이것은 이미지");
//
//            // Call the service method to save the Notion entity
//            notionService.createNotion(newNotion);
//
//            return "Notion created successfully";
//        } catch (IOException e) {
//            // Handle the exception (e.g., log the error or show an error message to the user)
//            return "Error creating Notion: " + e.getMessage();
//        }
//    }
//
//
//
//
//
//
//
//    // Method to save the uploaded file and return the file name
//    private String saveUploadedFile(MultipartFile file) throws IOException {
//        if (!file.isEmpty()) {
//            // Get the original file name
//            String originalFileName = file.getOriginalFilename();
//
//
//
//// Generate a unique file name (e.g., UUID)
//            String uniqueFileName = UUID.randomUUID().toString() + "_" + originalFileName;
//
//            // Specify the directory where you want to save the file
//            Path uploadPath = Paths.get("upload-directory"); // Replace with your actual directory path
//
//            // Create the directory if it doesn't exist
//            if (!Files.exists(uploadPath)) {
//                Files.createDirectories(uploadPath);
//            }
//
//            // Save the file to the server
//            try (InputStream inputStream = file.getInputStream()) {
//                Files.copy(inputStream, uploadPath.resolve(uniqueFileName), StandardCopyOption.REPLACE_EXISTING);
//            }
//
//            // Return the saved file name
//            return uniqueFileName;
//        }
//
//        return null; // Return null if no file was uploaded
//    }
//

}
