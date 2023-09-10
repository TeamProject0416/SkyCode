package teamproject.skycode.ticket;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teamproject.skycode.event.EventEntity;

import java.time.LocalDateTime;


@Repository
public interface TicketRepository extends JpaRepository<TicketEntity, Long>{

}
