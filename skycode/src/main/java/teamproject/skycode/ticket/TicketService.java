package teamproject.skycode.ticket;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamproject.skycode.common.FileService;
import teamproject.skycode.event.EventEntity;
import teamproject.skycode.event.EventFormDto;
import teamproject.skycode.event.EventStatus;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;

    @Transactional
    public Long ticketSave(TicketFormDto ticketFormDto) {
        // 상품 등록
        TicketEntity ticket = ticketFormDto.createTicket();

        // DB 시간 저장
        LocalDateTime now = LocalDateTime.now();
        ticket.setRegTime(now);
        ticket.setUpdateTime(now);

        // 이벤트 저장
        ticketRepository.save(ticket);
        return ticket.getId();
    }

    @Transactional(readOnly = true)
    public TicketFormDto getTicketDtl(Long ticketId) {
        // 해당 id의 상품 정보를 데이터 베이스에서 가져옴, 없으면 예외처리
        TicketEntity ticket = ticketRepository.findById(ticketId)
                .orElseThrow(EntityNotFoundException::new);
        // 상품 정보를 ticketFormDto 로 변환합니다
        TicketFormDto ticketFormDto = TicketFormDto.of(ticket);
        return ticketFormDto;
    }

    public Long updateTicket(TicketFormDto ticketFormDto) {
        // 상품 수정
        TicketEntity ticket = ticketRepository.findById(ticketFormDto.getId())
                .orElseThrow(EntityNotFoundException::new);

        // DB 시간 저장
        LocalDateTime now = LocalDateTime.now();
        ticket.setUpdateTime(now);

        ticket.updateTicket(ticketFormDto);

        return ticket.getId();
    }

    // 이벤트 삭제
    public void deleteTicket(Long ticketId) {
        TicketEntity ticket = ticketRepository.findById(ticketId)
                .orElseThrow(EntityNotFoundException::new);

        // 데이터 삭제
        ticketRepository.delete(ticket);
    }

    @Transactional(readOnly = true)
    public Page<TicketEntity> getTicketPage(Pageable pageable) {
        return ticketRepository.findAll(pageable);
    }

}