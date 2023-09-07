package teamproject.skycode.news.notion;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaxxer.hikari.HikariConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import teamproject.skycode.news.notion.ImageToBase64;

import javax.validation.Valid;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.time.LocalDateTime;
import java.util.zip.GZIPOutputStream;

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

    @Autowired
    private NotionViewCountRepository notionViewCountRepository;

    @Autowired
    private ObjectMapper objectMapper;
    private HikariConfig validator;


    @Autowired
    public void NotionController(NotionRepository notionRepository) {
        this.notionRepository = notionRepository;
    }



//    public ResponseEntity<String> createNotion(@RequestBody Notion notion) {
//        // Convert Notion to NotionForm
//        NotionForm notionForm = notionMapper.toForm(notion);
//
//        // Call the service method
//        notionService.saveNotion(notionForm);
//
//        // ...
//    }



    // 공지사항 등록 화면
    @GetMapping(value = "/notionUp")
    public String newsNotionUp(Model model){
        model.addAttribute("notionForm", new NotionForm());
        System.out.println("등록화면");
        return "news/notion/notionUp";
    }
//
//    @PostMapping("/notion/notionUp")
//    public String handleNotionUpForm(@ModelAttribute("notionForm") @Valid NotionForm notionForm,
//                                     BindingResult bindingResult,
//                                     RedirectAttributes redirectAttributes,
//                                     @RequestParam("file") MultipartFile file) {
//
//        // 코드는 이전에 제공한 것과 동일합니다.
//
//        // 이미지를 Base64로 변환하여 모델에 추가
//        try {
//            String base64Image = ImageToBase64.convertToBase64(uploadDir + fileName);
//            notionForm.setBase64Image(base64Image);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        Notion notion = Notion.builder()
//                .type(notionForm.getType())
//                .notionTitle(notionForm.getNotionTitle())
//                .notionContent(notionForm.getNotionContent())
//                .filePath(notionForm.getFilePath())
//                .fileName(notionForm.getFileName())
//                .base64Image(notionForm.getBase64Image())
//                .build();
//
//        notionService.save(notion);
//
//        redirectAttributes.addFlashAttribute("successMessage", "공지사항이 등록되었습니다.");
//        return "redirect:/news/notion/notion";
//    }

//    아거 아님
//    @PostMapping("/notion/notionUp")
//    public String handleNotionUpForm(@ModelAttribute("notionForm") @Valid NotionForm notionForm,
//                                     BindingResult bindingResult,
//                                     RedirectAttributes redirectAttributes,
//                                     @RequestParam("file") MultipartFile file) {
//
//        String uploadDir = "C:/SkyCodeProject/notionImg/";
//
//        if (bindingResult.hasErrors()) {
//            return "news/notion/notionUp";
//        }
//
//        try {
//            if (!file.isEmpty()) {
//                String originalFilename = file.getOriginalFilename();
//                if(originalFilename != null && !originalFilename.isEmpty()) {
//                    String fileName = originalFilename;
//                    Path filePath = Paths.get(uploadDir + fileName);
//                    Files.createDirectories(filePath.getParent());
//                    Files.write(filePath, file.getBytes());
//
//                    notionForm.setFilePath("notionImg/" + fileName);
//                    notionForm.setFileName(fileName);
//
//                    String base64Image = ImageToBase64.convertToBase64(uploadDir + fileName);
//                    notionForm.setBase64Image(base64Image);
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        Notion notion = Notion.builder()
//                .type(notionForm.getType())
//                .notionTitle(notionForm.getNotionTitle())
//                .notionContent(notionForm.getNotionContent())
//                .filePath(notionForm.getFilePath())
//                .fileName(notionForm.getFileName())
//                .base64Image(notionForm.getBase64Image())
//                .build();
//
//        notionService.save(notion);
//
//        redirectAttributes.addFlashAttribute("successMessage", "공지사항이 등록되었습니다.");
//        return "redirect:/news/notion/notion";
//    }

// Assuming you have imported java.util.zip.GZIPOutputStream

    @PostMapping("/notion/notionUp")
    public String handleNotionUpForm(@ModelAttribute("notionForm") @Valid NotionForm notionForm,
                                     BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes,
                                     @RequestParam("file") MultipartFile file) {

        String uploadDir = "C:/SkyCodeProject/notionImg/";

        if (bindingResult.hasErrors()) {
            return "news/notion/notionUp";
        }

        try {
            if (!file.isEmpty()) {
                String originalFilename = file.getOriginalFilename();
                if (originalFilename != null && !originalFilename.isEmpty()) {
                    String fileName = originalFilename;
                    Path filePath = Paths.get(uploadDir + fileName);
                    Files.createDirectories(filePath.getParent());
                    Files.write(filePath, file.getBytes());

                    notionForm.setFilePath("notionImg/" + fileName);
                    notionForm.setFileName(fileName);

                    // 이미지를 Base64로 변환하여 모델에 추가
                    String base64Image = ImageToBase64.convertToBase64(uploadDir + fileName);
                    notionForm.setBase64Image(base64Image);
                    byte[] imageBytes = Base64.getDecoder().decode(base64Image);


                    // 이미지를 바이트 배열로 변환
                    try {
//                        System.out.println("Base64 Image Before Compression: " + base64Image);
                        // 이미지 압축
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        try (GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream)) {
                            gzipOutputStream.write(imageBytes);
                        }
                        byte[] compressedImageBytes = byteArrayOutputStream.toByteArray();

                        // 압축된 이미지를 다시 Base64로 변환하여 모델에 설정
                        String compressedBase64Image = Base64.getEncoder().encodeToString(compressedImageBytes);
                        notionForm.setBase64Image(compressedBase64Image);
                        System.out.println("Base64 Image After Compression: " + compressedBase64Image);
                    } catch (IllegalArgumentException e) {
                        System.err.println("Base64 디코딩 중 오류 발생: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Notion notion = Notion.builder()
                .type(notionForm.getType())
                .notionTitle(notionForm.getNotionTitle())
                .notionContent(notionForm.getNotionContent())
                .filePath(notionForm.getFilePath())
                .fileName(notionForm.getFileName())
                .base64Image(notionForm.getBase64Image())
                .build();

        notionService.save(notion);

        redirectAttributes.addFlashAttribute("successMessage", "공지사항이 등록되었습니다.");
        return "redirect:/news/notion/notion";
    }






//    @PostMapping("/notion/notionUp")
//    public String handleNotionUpForm(@ModelAttribute("notionForm") @Valid NotionForm notionForm,
//                                     BindingResult bindingResult,
//                                     RedirectAttributes redirectAttributes,
//                                     @RequestParam("file") MultipartFile file) {
//
//        String uploadDir = "/SkyCodeProject/notionImg/";
//
//        if (bindingResult.hasErrors()) {
//            return "news/notion/notionUp";
//        }
//
//        try {
//            if (!file.isEmpty()) {
//                String originalFilename = file.getOriginalFilename(); // 파일 이름 가져오기
//                if(originalFilename != null && !originalFilename.isEmpty()) { // 파일 이름이 null 또는 빈 문자열인지 확인
//                    String fileName = originalFilename;
//                    Path filePath = Paths.get(uploadDir + fileName);
//                    Files.createDirectories(filePath.getParent()); // 디렉토리가 없으면 생성
//                    Files.write(filePath, file.getBytes());
//
//                    notionForm.setFilePath("notionImg/" + fileName);
//                    notionForm.setFileName(fileName); // 파일 이름 설정
//                    System.out.println("notionImg/" + fileName);
//
//                    // 이미지 업로드
//                    String imagePath = saveImage(file);
//                    notionForm.setImagePath(imagePath);
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        Notion notion = Notion.builder()
//                .type(notionForm.getType())
//                .notionTitle(notionForm.getNotionTitle())
//                .notionContent(notionForm.getNotionContent())
//                .filePath(notionForm.getFilePath())
//                .fileName(notionForm.getFileName()) // 파일 이름 설정
//                .build();
//
//        notionService.save(notion);
//        System.out.println(notion);
//
//        redirectAttributes.addFlashAttribute("successMessage", "공지사항이 등록되었습니다.");
//        return "redirect:/news/notion/notion";
//    }
//
//    private String saveImage(MultipartFile file) throws IOException {
//        String uploadDir = "C:/SkyCodeProject/notionImg/"; // 이미지를 저장할 경로
//        String fileName = file.getOriginalFilename();
//        Path filePath = Paths.get(uploadDir + fileName);
//        Files.write(filePath, file.getBytes());
//        return fileName;
//    }



//  최종 사진 안되면 이거 사용하기
//    @PostMapping("/notion/notionUp")
//    public ModelAndView createNotion(@Valid NotionForm notionForm, BindingResult bindingResult) {
//        ModelAndView modelAndView = new ModelAndView();
//        System.out.println("왜!!!");
//        if (bindingResult.hasErrors()) {
//            Notion notionEntity = notionForm.toEntity();
//            Notion savedNotion = notionService.saveNotion(notionEntity);
//            System.out.println("왜 안돼");
//            modelAndView.setViewName("news/notion/notionUp");
//            modelAndView.addObject("notion", savedNotion);
//        } else {
//            System.out.println("이유가 뭐야");
//            modelAndView.setViewName("redirect:/news/notion/notion");
//        }
//
//        return modelAndView;
//    }



//    @PostMapping(value = "notionUp/create")
//    public String createNotion(@ModelAttribute NotionForm form){
//        Notion notion = form.toEntity();
//        System.out.println("1234");
//        notionRepository.save(notion);
//        return "redirect:/news/notion/notion";
//@PostMapping(value = "notionUp/create")
//public String createNotion(@ModelAttribute NotionForm form, Model model) {
//    // Check if the required fields are empty
//    if (form.getNotionTitle() == null || form.getNotionContent() == null) {
//
//        model.addAttribute("errorMessage", "Please fill in the form completely.");
//        System.out.println("오류가 떠야함");
//        return "news/notion/notionUp"; // Replace with the actual template name
//    }
//
//    Notion notion = form.toEntity();
//    System.out.println("1234");
//    notionRepository.save(notion);
//    return "redirect:/news/notion/notion";
//}
//    @PostMapping(value = "notionUp/create")
//    public String createNotion(@ModelAttribute @Valid NotionForm form, BindingResult result, Model model) {
//        if (result.hasErrors()) {
//            model.addAttribute("errorMessage", "Please fill in the form completely.");
//            System.out.println("오류가 떠야함");
//            return "news/notion/notionUp"; // Replace with the actual template name
//        }
//
//        Notion notion = form.toEntity();
//        System.out.println("1234");
//        notionRepository.save(notion);
//        return "redirect:/news/notion/notion";
//    }
//@PostMapping("/news/notion/create")
//public ModelAndView createNotion(@Valid NotionForm notionForm, BindingResult bindingResult) {
//    ModelAndView modelAndView = new ModelAndView();
//
//    if (bindingResult.hasErrors()) {
//        modelAndView.setViewName("redirect:/news/notion/notion");
//    } else {
//        Notion notionEntity = notionForm.toEntity(); // Convert NotionForm to Notion
//        Notion savedNotion = notionService.saveNotion(notionForm);
//        modelAndView.setViewName("news/notion/notionUp");
//        modelAndView.addObject("notion");
//    }
//
//    return modelAndView;
//}



    // 공지사항 등록시 전송하는
//    @PostMapping(value = "notionUp/create")
//    public String createNotion(@ModelAttribute NotionForm form){
//        Notion notion = form.toEntity();
//        System.out.println("1234");
//        notionRepository.save(notion);
//        return "redirect:/news/notion/notion";
//    }

    // 공지사항 리스트 출력
    @GetMapping("/notion/notion")
    public String notionList(
            Model model,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false) String sortBy
    ){
        int pageSize = 4;
        Pageable pageable;


        if ("popularity".equals(sortBy)) {
            pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "countView"));
        } else {
            pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "regTime"));
        }

        Page<Notion> notionPage = notionRepository.findAll(pageable);
        List<Notion> notions = notionPage.getContent();

        long totalNotionCount = notionService.getTotalNotionCount();
        model.addAttribute("totalNotionCount", totalNotionCount);

        String uploadDir = "C:/SkyCodeProject/notionImg/";
        System.out.println("된다");
        for (Notion notion : notions) {
            // 예시로 파일 경로를 어떻게 생성할지에 대한 로직을 추가합니다.
            String filePath = uploadDir + notion.getFileName(); // 예시 경로입니다. 실제로 사용하는 경로로 바꿔주세요.
            notion.setFilePath(filePath);
            System.out.println(filePath);
        }

        model.addAttribute("notions", notions);
        model.addAttribute("notions", notionPage);

        return "/news/notion/notion";
    }

    @PostMapping("/notion/notion")
    public String submitNotion(@ModelAttribute NotionForm notionForm, Model model) {
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
//            NotionViewCount viewCount = notionViewCountService.incrementViewCount(id);
            notion.setCountView(notion.getCountView() + 1); // Increment the view count
            notionRepository.save(notion);

            model.addAttribute("notion", notion);
            model.addAttribute("viewCount", notion.getCountView());
            System.out.println("좀");
            return "news/notion/notionSub"; // Adjust the view name
        }
        System.out.println("제발");

        // Handle case when inquiry is not found
        return "error"; // Change to the appropriate view name
    }

    @PostMapping("/notion/delete")
    public String deleteNotion(@RequestParam Long notionId){
        notionService.deleteNotion(notionId);
        System.out.println("삭제");
        return "redirect:/news/notion/notion";
    }

    @GetMapping("/notion/edit/{notionId}")
    public String showEditForm(@PathVariable Long notionId, Model model){

        Notion notion = notionService.findById(notionId);

        NotionForm notionForm = new NotionForm();
        notionForm.setId(notion.getId());
        notionForm.setType(notion.getType());
        notionForm.setNotionTitle(notion.getNotionTitle());
        notionForm.setNotionContent(notion.getNotionContent());

        model.addAttribute("notionForm", notionForm);
        System.out.println("수정중");

        return "news/notion/notionEdit";
    }

    @PostMapping("/notionUp/edit")
    public String editNotion(@ModelAttribute("notionForm") NotionForm notionForm){

        Notion existingNotion = notionService.findById(notionForm.getId());

        // 기존 Notion의 필드를 업데이트합니다.
        existingNotion.setType(notionForm.getType());
        existingNotion.setNotionTitle(notionForm.getNotionTitle());
        existingNotion.setNotionContent(notionForm.getNotionContent());
        existingNotion.setRegTime(LocalDateTime.now());

        // 수정된 Notion을 저장합니다.
        notionService.editNotion(existingNotion);

        return "redirect:/news/notion/notionSub/" + existingNotion.getId();
    }

}