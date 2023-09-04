package teamproject.skycode.review;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public void save(ReviewDto reviewDto) {
        ReviewEntity reviewEntity = ReviewEntity.toSaveEntity(reviewDto);
        reviewRepository.save(reviewEntity);
    }

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
