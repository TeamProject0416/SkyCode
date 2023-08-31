package teamproject.skycode.review;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewImgRepository reviewImgRepository;

    public List<Review> findReviews() {
        return reviewRepository.findAll();
    }
    public Review show(Long id) {
        return reviewRepository.findById(id).orElseThrow(NullPointerException::new);
    }

    @Transactional(readOnly = true) // 조회만 가능하게(읽을수만 있도록)
    public ReviewFormDto getItemDtl(Long itemId) {
//        해당 상품에 연결된 이미지 정보를 id 순서대로 가져온다.
        List<ReviewImg> reviewImgList = reviewImgRepository.findByItemIdOrderByIdAsc(itemId); // 여러개니까 리스트로 받음
//        ReviewImgDto 객체 리스트를 초기화한다.
        List<ReviewImgDto> reviewImgDtoList = new ArrayList<>();
        for (ReviewImg reviewImg : reviewImgList) {
//            ReviewImgDto 클래스에 정의된 of 메서드를 호출 ReviewImg -> ReviewImgDto 로 변환하여 반환
            ReviewImgDto itemImgDto = ReviewImgDto.of(reviewImg);
//            리스트에 추가
            reviewImgDtoList.add(itemImgDto);
        }
//        해당 id 의 상품정보를 데이터베이스에서 가져옵니다. 없으면 예외처리
        Review review = reviewRepository.findById(itemId)
                .orElseThrow(EntityNotFoundException::new);
//        상품 정보를 ReviewFormDto 로 변환합니다.
        ReviewFormDto reviewFormDto = ReviewFormDto.of(review);
//        상품 정보 Dto 에 이미지 정보 DTO 리스트를 설정
        reviewFormDto.setItemImgDtoList(reviewImgDtoList);

        return reviewFormDto;
    }
}
