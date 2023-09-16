package teamproject.skycode.review;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import teamproject.skycode.login.MemberEntity;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long>{

    @Modifying
    @Query(value = "update ReviewEntity r set r.reviewHits=r.reviewHits+1 where r.id=:id")
    void updateHits(@Param("id") Long id);
    @Query("SELECT r FROM ReviewEntity r ORDER BY r.regTime DESC")
    List<ReviewEntity> findByAllEntity();

//    0907 페이징 처리 하기
    Page<ReviewEntity> findAll(Pageable pageable);

//    조회수 순서로 리뷰 찾기
    List<ReviewEntity> findAllByOrderByReviewHitsDesc();

//    검색 기능
//    List<ReviewEntity> findByReviewTitleContaining(String searchValue);
//    List<ReviewEntity> findByContentsContaining(String searchValue);
// 내림차순으로 reviewTitle 검색 결과를 가져오는 메서드
    @Query("SELECT r FROM ReviewEntity r WHERE r.reviewTitle LIKE %:searchValue% ORDER BY r.id DESC")
    List<ReviewEntity> findByReviewTitleContainingOrderByIdDesc(@Param("searchValue") String searchValue);
// 내림차순으로 contents 검색 결과를 가져오는 메서드
    @Query("SELECT r FROM ReviewEntity r WHERE r.contents LIKE %:searchValue% ORDER BY r.id DESC")
    List<ReviewEntity> findByContentsContainingOrderByIdDesc(@Param("searchValue") String searchValue);

    List<ReviewEntity> findAllByMemberEntityOrderByIdDesc(MemberEntity memberEntity);


//    List<ReviewEntity> findByReviewTitleContainingOrContentsContaining(String keyword, String keyword1);


//    Page<Review> findByReviewSearchCriteria(ReviewSearchDto searchDto, Pageable pageable);
//    Page<MainReviewDto> getMainReviewPage(ReviewSearchDto reviewSearchDto, Pageable pageable);
}
