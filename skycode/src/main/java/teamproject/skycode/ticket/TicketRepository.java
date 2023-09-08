package teamproject.skycode.ticket;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teamproject.skycode.event.EventEntity;


@Repository
public interface TicketRepository extends JpaRepository<TicketEntity, Long>{

    Page<TicketEntity> findByRegTimeOrderByRegTimeDesc(TicketEntity ticket, Pageable pageable);
}
