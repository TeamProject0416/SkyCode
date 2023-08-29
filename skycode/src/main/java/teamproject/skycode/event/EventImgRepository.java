package teamproject.skycode.event;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventImgRepository extends JpaRepository<EventImg, Long> {
    List<EventImg> findByEventIdOrderByIdAsc(Long eventId);
    // 상품 이미지 아이디의 오름차순위로 가져오는 쿼리 메소드

}

