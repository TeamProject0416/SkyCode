package teamproject.skycode.event;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import teamproject.skycode.constant.EventStatus;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Long>{

        @Query("SELECT e FROM EventEntity e WHERE e.eventStatus = 'ONGOING' ORDER BY e.regTime DESC")
        List<EventEntity> findByONGOING();
        // 이벤트 상태가 진행중인 것만 가져오기

        Page<EventEntity> findByEventStatusOrderByRegTimeDesc(EventStatus eventStatus, Pageable pageable);
        // 이벤트 상태에 따라 페이징하여 가져오기
}
