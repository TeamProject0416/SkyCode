package teamproject.skycode.review;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewFileRepository reviewFileRepository;

    public void save(ReviewDto reviewDto) throws IOException {
        // 파일 첨부에 따라 로직 분리
        if (reviewDto.getReviewFile().isEmpty()) {
            // 첨부 파일 없음.
            ReviewEntity reviewEntity = ReviewEntity.toSaveEntity(reviewDto);
            reviewRepository.save(reviewEntity);
        } else {
            // 첨부 파일 있음.
            MultipartFile reviewFile = reviewDto.getReviewFile(); // 1.DTO에 담긴 파일을 꺼냄
            String originalFilename = reviewFile.getOriginalFilename(); // 2. 파일의 이름 가져옴
            String storedFileName = "/review_img/" + System.currentTimeMillis() + "_" + originalFilename; // 3.서버 저장용 이름을 만듦, 내사진.jpg => 839798375892_내사진.jpg
            //String savePath = "J:/SkyCodeProject" + storedFileName; // 4.저장 경로 설정,  J:/SkyCodeProject/review_img/9802398403948_내사진.jpg (win)


            String savePath = "C:/Users/GR805/Desktop/skycode/src/main/resources/static/review_img" + storedFileName; // 4.저장 경로 설정,  J:/SkyCodeProject/review_img/9802398403948_내사진.jpg (win)
            //String savePath = "C:\\Users\\GR805\\Desktop\\skycode\\src\\main\\resources\\static\\review_img" + storedFileName; // 4.저장 경로 설정,  J:/SkyCodeProject/review_img/9802398403948_내사진.jpg (win)

            ${pageContext.request.contextPath}
[출처] [Spring] 경로 설정(상대경로/절대경로)|작성자 총아



//            String savePath = "/Users/사용자이름/springboot_img/" + storedFileName; // J:/SkyCodeProject/img/9802398403948_내사진.jpg (mac)
            System.out.println("originalFilename = " + originalFilename);
//            originalFilename = 청바지23.jpg
            System.out.println("storedFileName = " + storedFileName);
//            storedFileName = /review_img/1693918660230_청바지23.jpg
            System.out.println("savePath = " + savePath);
//            savePath = J:/SkyCodeProject//review_img/1693918286761_청바지23.jpg -> "J:/SkyCodeProject/"
//            savePath = J:/SkyCodeProject/review_img/1693918554895_청바지23.jpg -> "J:/SkyCodeProject"

            reviewFile.transferTo(new File(savePath)); // 5. 해당 경로에 파일 저장
            ReviewEntity reviewEntity = ReviewEntity.toSaveFileEntity(reviewDto);
            Long savedId = reviewRepository.save(reviewEntity).getId(); // 6. review_table에 해당 데이터 save 처리
            ReviewEntity review = reviewRepository.findById(savedId).get();

            ReviewFileEntity reviewFileEntity = ReviewFileEntity.toReviewFileEntity(review, originalFilename, storedFileName);
            reviewFileRepository.save(reviewFileEntity); // 7. review_file_table에 해당 데이터 save 처리
        }
    }

    @Transactional
    public List<ReviewDto> findReviews() {
        List<ReviewEntity> reviewEntityList = reviewRepository.findAll();
        List<ReviewDto> reviewDtoList = new ArrayList<>();
        for (ReviewEntity reviewEntity: reviewEntityList) {
            reviewDtoList.add(ReviewDto.toReviewDto(reviewEntity));
        }

        return reviewDtoList;
    }
    @Transactional
    public void updateHits(Long id) {
        reviewRepository.updateHits(id);
    }

    @Transactional
    public ReviewDto findById(Long id) {
        Optional<ReviewEntity> optionalReviewEntity = reviewRepository.findById(id);
        if (optionalReviewEntity.isPresent()) {
            ReviewEntity boardEntity = optionalReviewEntity.get();
            ReviewDto reviewDto = ReviewDto.toReviewDto(boardEntity);
            System.out.println("reviewId = " + reviewDto.getId());
            return reviewDto;
        } else {
            return null;
        }
    }

    public ReviewDto update(ReviewDto reviewDto) {
        ReviewEntity reviewEntity = ReviewEntity.toUpdateEntity(reviewDto);
        reviewRepository.save(reviewEntity);
        return findById(reviewDto.getId());
    }


//    public ReviewEntity show(Long id) {
//        return reviewRepository.findById(id).orElseThrow(NullPointerException::new);
//    }



//    public Long saveItem(ReviewDto reviewDto, List<MultipartFile> reviewImgFileList) throws Exception {
////        상품 등록
//        ReviewEntity reviewEntity = reviewDto.createItem();
//        reviewRepository.save(reviewEntity);
//    }

}
