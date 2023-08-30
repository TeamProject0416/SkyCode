package teamproject.skycode.review;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewImgRepository extends JpaRepository<ReviewImg, Long> {

//    List<ReviewImg> findByItemIdOrderByIdAsc(Long reviewId);

//    ReviewImg findByItemIdAndRepimgYn(Long reviewId, String repimgYn);
}
