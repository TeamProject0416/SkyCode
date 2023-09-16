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
import teamproject.skycode.login.MemberEntity;
import teamproject.skycode.login.MemberRepository;
import teamproject.skycode.review.ReviewEntity;
import teamproject.skycode.review.ReviewRepository;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/news")
@RequiredArgsConstructor

public class notionController {

    @Autowired
    private NotionRepository notionRepository;

    private final NotionService notionService;

    private final NotionViewCountService notionViewCountService;
    private final NotionViewCountRepository notionViewCountRepository;
    private final ObjectMapper objectMapper;
    private final HikariConfig validator;

    private final MemberRepository memberRepository;
    private final ReviewRepository reviewRepository;


    @Autowired
    public void NotionController(NotionRepository notionRepository) {
        this.notionRepository = notionRepository;
    }


    // 공지사항 등록 화면
    @GetMapping(value = "/notionUp")
    public String newsNotionUp(Model model, Principal principal) {

        // 유저 로그인
        if (principal != null) {
            String user = principal.getName();
            MemberEntity userInfo = memberRepository.findByEmail(user);
            model.addAttribute("userInfo", userInfo);
            List<ReviewEntity> review = reviewRepository.findByMemberEntityId(userInfo.getId());
            int reviewNum = review.size();
            model.addAttribute("reviewNum", reviewNum);
        }

        model.addAttribute("notionForm", new NotionForm());
        System.out.println("등록화면");
        return "news/notion/notionUp";
    }


    @PostMapping("/notion/notionUp")
    public String handleNotionUpForm(@ModelAttribute("notionForm") @Valid NotionForm notionForm,
                                     BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes,
                                     @RequestParam("file") MultipartFile file) {

        String uploadDir = "/SkyCodeProject/img/notion/";

        if (bindingResult.hasErrors()) {
            return "news/notion/notionUp";
        }

        try {
            if (!file.isEmpty()) {
                String originalFilename = file.getOriginalFilename(); // 파일 이름 가져오기
                if (originalFilename != null && !originalFilename.isEmpty()) { // 파일 이름이 null 또는 빈 문자열인지 확인
                    String fileName = originalFilename;
                    Path filePath = Paths.get(uploadDir + fileName);
                    Files.createDirectories(filePath.getParent()); // 디렉토리가 없으면 생성
                    Files.write(filePath, file.getBytes());

                    notionForm.setFilePath("/notion/" + fileName);
                    notionForm.setFileName(fileName); // 파일 이름 설정
//                    System.out.println("img/" + fileName);

                    // 이미지 업로드
                    String imagePath = saveImage(file);
                    notionForm.setImagePath(imagePath);
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
                .fileName(notionForm.getFileName()) // 파일 이름 설정
                .build();

        notionService.save(notion);
        System.out.println(notion);

        redirectAttributes.addFlashAttribute("successMessage", "공지사항이 등록되었습니다.");
        return "redirect:/news/notion/notion";
    }

    private String saveImage(MultipartFile file) throws IOException {
        String uploadDir = "/notion/"; // 이미지를 저장할 경로
        String fileName = file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir + fileName);
        Files.write(filePath, file.getBytes());
        return fileName;
    }

    // 공지사항 리스트 출력
    @GetMapping("/notion/notion")
    public String notionList(
            Model model, Principal principal,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false) String sortBy
    ) {

        // 유저 로그인
        if (principal != null) {
            String user = principal.getName();
            MemberEntity userInfo = memberRepository.findByEmail(user);
            model.addAttribute("userInfo", userInfo);
            List<ReviewEntity> review = reviewRepository.findByMemberEntityId(userInfo.getId());
            int reviewNum = review.size();
            model.addAttribute("reviewNum", reviewNum);
        }

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

        String uploadDir = "C:/SkyCodeProject/img/notion/";
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
    public String showNotionById(@PathVariable Long id, Model model, Principal principal) {

        // 유저 로그인
        if (principal != null) {
            String user = principal.getName();
            MemberEntity userInfo = memberRepository.findByEmail(user);
            model.addAttribute("userInfo", userInfo);
            List<ReviewEntity> review = reviewRepository.findByMemberEntityId(userInfo.getId());
            int reviewNum = review.size();
            model.addAttribute("reviewNum", reviewNum);
        }

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
    public String deleteNotion(@RequestParam Long notionId) {
        notionService.deleteNotion(notionId);
        System.out.println("삭제");
        return "redirect:/news/notion/notion";
    }

    @GetMapping("/notion/edit/{notionId}")
    public String showEditForm(@PathVariable Long notionId, Model model, Principal principal) {

        // 유저 로그인
        if (principal != null) {
            String user = principal.getName();
            MemberEntity userInfo = memberRepository.findByEmail(user);
            model.addAttribute("userInfo", userInfo);
            List<ReviewEntity> review = reviewRepository.findByMemberEntityId(userInfo.getId());
            int reviewNum = review.size();
            model.addAttribute("reviewNum", reviewNum);
        }

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
    public String editNotion(@ModelAttribute("notionForm") NotionForm notionForm) {

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