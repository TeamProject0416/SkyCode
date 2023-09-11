package teamproject.skycode.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teamproject.skycode.ticket.TicketEntity;


@Repository
public interface OrderRepository extends JpaRepository<TicketEntity, Long> {

}
