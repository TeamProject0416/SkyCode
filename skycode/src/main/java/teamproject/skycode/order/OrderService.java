package teamproject.skycode.order;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamproject.skycode.ticket.TicketEntity;
import teamproject.skycode.ticket.TicketFormDto;
import teamproject.skycode.ticket.TicketRepository;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {


}