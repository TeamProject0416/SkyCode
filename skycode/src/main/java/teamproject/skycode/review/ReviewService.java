package teamproject.skycode.review;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import teamproject.skycode.common.FileService;

import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final FileService fileService;

//    public List<ReviewDto> findAll() {
//        List<ReviewEntity> reviewEntityList = reviewRepository.findAll();
//        List<ReviewDto> reviewDtoList = new ArrayList<>();
//        for (ReviewEntity reviewEntity: reviewEntityList) {
//            reviewDtoList.add(ReviewDto.toReviewDto(reviewEntity));
//        }
//
//        return reviewDtoList;
//    }

    @Value("${reviewImgLocation}")
    private String reviewImgLocation;


    public Long saveReview(ReviewDto reviewDto, MultipartFile reviewImgFile1, MultipartFile reviewImgFile2) throws Exception {
        // 상품 등록
        ReviewEntity review = reviewDto.createReview();

        // 폴더 없을시 폴더 생성
        Path directoryPath = Paths.get("/SkyCodeProject/img/review");

        try {
            // 디렉토리 생성
            Files.createDirectories(directoryPath);
            System.out.println(directoryPath + " 디렉토리가 생성되었습니다.");

        } catch (IOException e) {
            System.out.println(directoryPath + " 디렉토리 생성이 실패하였습니다.");
            e.printStackTrace();
        }

        // 파일 경로 설정
        String basePath = "/review";

        // 이미지 업로드 처리 및 파일명 생성
        String miniImgName = "";
        String miniOriImgName = reviewImgFile1.getOriginalFilename();
        String miniImgUrl = "";

        if (miniOriImgName != null && !miniOriImgName.isEmpty()) {
            // 파일명 생성
            miniImgName = fileService.uploadFile(reviewImgLocation + basePath, miniOriImgName,
                    reviewImgFile1.getBytes());

            // 파일 경로 생성
            miniImgUrl = "/img/review/" + miniImgName;
        }

        String bigImgName = "";
        String bigOriImgName = reviewImgFile2.getOriginalFilename();
        String bigImgUrl = "";
//        List<String> bigImgNameList = new ArrayList<>();
//        List<String> bigOriImgNameList = new ArrayList<>();
//        List<String> bigImgUrlList = new ArrayList<>();
//        List<MultipartFile> reviewImgFiles = new ArrayList<>();

        if (bigOriImgName != null && !bigOriImgName.isEmpty()) {
            // 파일명 생성
            bigImgName = fileService.uploadFile(reviewImgLocation + basePath, bigOriImgName,
                    reviewImgFile2.getBytes());

            // 파일 경로 생성
            bigImgUrl = "/img/review/" + bigImgName;
        }
//        list
//        for (MultipartFile reviewImgFile : reviewImgFiles) {
//            if (reviewImgFile != null && !reviewImgFile.isEmpty()) {
//                String bigOriImgName = reviewImgFile.getOriginalFilename();
//                String bigImgName = fileService.uploadFile(reviewImgLocation + basePath, bigOriImgName,
//                        reviewImgFile.getBytes());
//                String bigImgUrl = "/img/review/" + bigImgName;
//
//                bigImgNameList.add(bigImgName);
//                bigOriImgNameList.add(bigOriImgName);
//                bigImgUrlList.add(bigImgUrl);
//            }
//        }

        // 상품 이미지 정보 저장
        review.updateReviewImg(miniImgName, miniOriImgName, miniImgUrl,
                bigImgName, bigOriImgName, bigImgUrl);
//        list
//        review.updateReviewImg(miniImgName, miniOriImgName, miniImgUrl,
//                bigImgNameList, bigOriImgNameList, bigImgUrlList);

        // DB 시간 저장
        LocalDateTime now = LocalDateTime.now();
        review.setRegTime(now);
        review.setUpdateTime(now);

        // 게시글 시간 저장 - 날짜까지만
        String formattedDate = now.toLocalDate().toString(); // "yyyy-MM-dd"
        review.setReviewTime(formattedDate);


        // 이벤트 저장
        reviewRepository.save(review);
        return review.getId();
    }

    @Transactional(readOnly = true)
    public ReviewDto getReviewDtl(Long reviewId) {
        // 해당 id의 상품 정보를 데이터 베이스에서 가져옴, 없으면 예외처리
        ReviewEntity review = reviewRepository.findById(reviewId)
                .orElseThrow(EntityNotFoundException::new);
        // 상품 정보를 eventFormDto 로 변환합니다
        ReviewDto reviewDto = ReviewDto.of(review);
        return reviewDto;
    }

    public Long updateReview(ReviewDto reviewDto, MultipartFile reviewImgFile1, MultipartFile reviewImgFile2) throws Exception {
        // 상품 수정
        ReviewEntity review = reviewRepository.findById(reviewDto.getId())
                .orElseThrow(EntityNotFoundException::new);

        // 파일 경로 설정
        String basePath = "/review";

        // 이미지 업로드 처리 및 파일명 생성
        String miniImgName = review.getMiniImgName();
        String miniOriImgName = review.getMiniOriImgName();
        String miniImgUrl = review.getMiniImgUrl();

        if (reviewImgFile1 != null && !reviewImgFile1.isEmpty()) {

            // 수정 전 파일 삭제하기
            String filePath = reviewImgLocation + basePath + "/" + miniImgName;
            File fileToDelete = new File(filePath);
            if (fileToDelete.delete()) {
                System.out.println("파일이 성공적으로 삭제되었습니다.");
            } else {
                System.err.println("파일 삭제 중 오류가 발생했습니다.");
            }

            // 파일명 가져오기
            miniOriImgName = reviewImgFile1.getOriginalFilename();

            // 파일명 생성
            miniImgName = fileService.uploadFile(reviewImgLocation + basePath, miniOriImgName,
                    reviewImgFile1.getBytes());

            // 파일 경로 생성
            miniImgUrl = "/img/review/" + miniImgName;
        }

        // 이미지 업로드 처리 및 파일명 생성
        String bigImgName = review.getBigImgName();
        String bigOriImgName = review.getBigOriImgName();
        String bigImgUrl = review.getBigImgUrl();
//        List<String> newBigImgNameList = new ArrayList<>();
//        List<String> newBigOriImgNameList = new ArrayList<>();
//        List<String> newBigImgUrlList = new ArrayList<>();
//        List<MultipartFile> reviewImgFiles = new ArrayList<>();

        if (reviewImgFile2 != null && !reviewImgFile2.isEmpty()) {

            // 수정 전 파일 삭제하기
            String filePath = reviewImgLocation + basePath + "/" + bigImgName;
            File fileToDelete = new File(filePath);
            if (fileToDelete.delete()) {
                System.out.println("파일이 성공적으로 삭제되었습니다.");
            } else {
                System.err.println("파일 삭제 중 오류가 발생했습니다.");
            }

            // 파일명 가져오기
            bigOriImgName = reviewImgFile2.getOriginalFilename();

            // 파일명 생성
            bigImgName = fileService.uploadFile(reviewImgLocation + basePath, bigOriImgName,
                    reviewImgFile2.getBytes());

            // 파일 경로 생성
            bigImgUrl = "/img/review/" + bigImgName;

        }
//        list
//        for (MultipartFile reviewImgFile : reviewImgFiles) {
//            if (reviewImgFile != null && !reviewImgFile.isEmpty()) {
//                String newBigOriImgName = reviewImgFile.getOriginalFilename();
//                String newBigImgName = fileService.uploadFile(reviewImgLocation + basePath, newBigOriImgName,
//                        reviewImgFile.getBytes());
//                String newBigImgUrl = "/img/review/" + newBigImgName;
//
//                newBigImgNameList.add(newBigImgName);
//                newBigOriImgNameList.add(newBigOriImgName);
//                newBigImgUrlList.add(newBigImgUrl);
//            }
//        }
//
//        // 기존 이미지 파일 삭제
//        if (!review.getBigImgNameList().isEmpty()) {
//            for (String oldBigImgName : review.getBigImgNameList()) {
//                String filePath = reviewImgLocation + basePath + "/" + oldBigImgName;
//                File fileToDelete = new File(filePath);
//                if (fileToDelete.delete()) {
//                    System.out.println("파일이 성공적으로 삭제되었습니다.");
//                } else {
//                    System.err.println("파일 삭제 중 오류가 발생했습니다.");
//                }
//            }
//        }

        // DB 시간 저장
        LocalDateTime now = LocalDateTime.now();
        review.setUpdateTime(now);

        reviewDto.setMiniImgName(miniImgName);
        reviewDto.setMiniOriImgName(miniOriImgName);
        reviewDto.setMiniImgUrl(miniImgUrl);

        reviewDto.setBigImgName(bigImgName);
        reviewDto.setBigOriImgName(bigOriImgName);
        reviewDto.setBigImgUrl(bigImgUrl);
//        list
//        reviewDto.setBigImgNameList(newBigImgNameList);
//        reviewDto.setBigOriImgNameList(newBigOriImgNameList);
//        reviewDto.setBigImgUrlList(newBigImgUrlList);

        review.updateReview(reviewDto);

        return review.getId();
    }
    @Transactional
    public void updateHits(Long id) {
        reviewRepository.updateHits(id);
    }
    // 이벤트 삭제
    public void deleteReview(Long reviewId) {
        ReviewEntity review = reviewRepository.findById(reviewId)
                .orElseThrow(EntityNotFoundException::new);

        // 파일 경로 설정
        String basePath = "/review";

        // 파일 삭제하기
        if (!review.getMiniImgName().isEmpty()) {
            String filePath = reviewImgLocation + basePath + "/" + review.getMiniImgName();
            System.out.println("filePath : " + filePath);
            File fileToDelete = new File(filePath);
            if (fileToDelete.delete()) {
                System.out.println("파일이 성공적으로 삭제되었습니다.");
            } else {
                System.err.println("파일 삭제 중 오류가 발생했습니다.");
            }
        }

        if (!review.getBigImgName().isEmpty()) {
            String filePath = reviewImgLocation + basePath + "/" + review.getBigImgName();
            File fileToDelete = new File(filePath);
            if (fileToDelete.delete()) {
                System.out.println("파일이 성공적으로 삭제되었습니다.");
            } else {
                System.err.println("파일 삭제 중 오류가 발생했습니다.");
            }
        }

        // 데이터 삭제
        reviewRepository.delete(review);
    }

    /* Paging  */
    @Transactional(readOnly = true)
    public Page<ReviewEntity> pageList(Pageable pageable) {
        return reviewRepository.findAll(pageable);
    }
    public Page<ReviewEntity> getList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("id"));
        Pageable pageable = PageRequest.of(page, 5, Sort.by(sorts));
        return this.reviewRepository.findAll(pageable);
    }



    public List<ReviewEntity> getTop3BestReviews() {
        List<ReviewEntity> bestReviews = reviewRepository.findAllByOrderByReviewHitsDesc(); // 모든 리뷰를 조회수 내림차순으로 가져옵니다.

        // 리스트를 조회수 내림차순, ID 오름차순으로 정렬합니다.
        bestReviews.sort(Comparator.comparing(ReviewEntity::getReviewHits).reversed().thenComparing(ReviewEntity::getId));

        // 상위 3개 리뷰를 선택합니다.
        int topCount = Math.min(3, bestReviews.size());
        return bestReviews.subList(0, topCount);
    }




    public List<ReviewEntity> getAllReviews() {
        // 모든 리뷰를 가져옵니다.
        return reviewRepository.findAll();
    }


//    public List<ReviewEntity> searchReviews(String keyword) {
//        // 검색어를 이용하여 리뷰를 검색하고 결과를 반환합니다.
//        return reviewRepository.findByReviewTitleContainingOrContentsContaining(keyword, keyword);
//    }
    public List<ReviewEntity> findByReviewTitleContaining(String searchValue) {

        return reviewRepository.findByReviewTitleContainingOrderByIdDesc(searchValue);
    }

    public List<ReviewEntity> findByContentsContaining(String searchValue) {
        return reviewRepository.findByContentsContainingOrderByIdDesc(searchValue);
    }

    public List<ReviewEntity> searchReviews(String searchType, String searchValue) {
        // 검색 유형에 따라 검색을 수행하고 결과를 반환합니다.
        switch (searchType) {
            case "reviewTitle":
                return findByReviewTitleContaining(searchValue);

            case "contents":
                return findByContentsContaining(searchValue);
            default:
                throw new IllegalArgumentException("잘못된 검색 유형입니다. '제목' 또는 '내용'을 선택해주세요");
        }
    }
}
