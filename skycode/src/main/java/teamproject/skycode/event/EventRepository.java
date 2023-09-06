package teamproject.skycode.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import teamproject.skycode.constant.EventStatus;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Long>{

        @Query("SELECT e FROM EventEntity e WHERE e.eventStatus = 'ONGOING' ORDER BY e.regTime DESC")
        List<EventEntity> findByONGOING();
        // 이벤트 상태가 진행중인 것만 가져오기

        @Query("SELECT e FROM EventEntity e WHERE e.eventStatus = 'END' ORDER BY e.regTime DESC")
        List<EventEntity> findByEND();
        // 이벤트 상태가 종료된 것만 가져오기

        @Query("SELECT e FROM EventEntity e WHERE e.eventStatus = 'WINNER' ORDER BY e.regTime DESC")
        List<EventEntity> findByWINNER();
        // 이벤트 상태가 당첨자인 것만 가져오기

}
