package teamproject.skycode.review;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long>{

    @Modifying
    @Query(value = "update ReviewEntity r set r.reviewHits=r.reviewHits+1 where r.id=:id")
    void updateHits(@Param("id") Long id);
//    Page<Review> findByReviewSearchCriteria(ReviewSearchDto searchDto, Pageable pageable);
//    Page<MainReviewDto> getMainReviewPage(ReviewSearchDto reviewSearchDto, Pageable pageable);
}
