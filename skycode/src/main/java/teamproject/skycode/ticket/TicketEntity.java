package teamproject.skycode.ticket;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import teamproject.skycode.common.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter
@Entity
@Table(name = "ticket")
@Setter
@ToString
public class TicketEntity extends BaseEntity {
    @Id
    @Column(name = "ticket_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // 티켓 Id

    @Enumerated(EnumType.STRING)
    private TicketStatus firstStatus; // firstClass 티켓 재고 상태

    @Enumerated(EnumType.STRING)
    private TicketStatus businessStatus; // businessClass 티켓 재고 상태

    @Enumerated(EnumType.STRING)
    private TicketStatus economyStatus; // economyClass 티켓 재고 상태

    private int firstNum; // firstClass 수량
    private int businessNum; // businessClass 수량
    private int economyNum; // economyClass 수량

    private Integer firstPrice; // firstClass 가격
    private Integer businessPrice; // businessClass 가격
    private Integer economyPrice; // economyClass 가격

    @Enumerated(EnumType.STRING)
    private TicketCountry ticketStart; // 출발지
    @Enumerated(EnumType.STRING)
    private TicketCountry ticketEnd; // 도착지

    private String ticketStartDate; // "yyyy-MM-dd" 출발일
    private String ticketEndDate; // "yyyy-MM-dd" 도착일

    private String ticketStartTime; // "hh-mm" 출발 시간
    private String ticketEndTime; // "hh-mm" 도착 시간

    public void updateTicket(TicketFormDto ticketFormDto) {
        this.firstNum = ticketFormDto.getFirstNum();
        this.businessNum = ticketFormDto.getBusinessNum();
        this.economyNum = ticketFormDto.getEconomyNum();

        this.firstPrice = ticketFormDto.getFirstPrice();
        this.businessPrice = ticketFormDto.getBusinessPrice();
        this.economyPrice = ticketFormDto.getEconomyPrice();

        this.firstStatus = ticketFormDto.getFirstStatus();
        this.businessStatus = ticketFormDto.getBusinessStatus();
        this.economyStatus = ticketFormDto.getEconomyStatus();

        this.ticketStart = ticketFormDto.getTicketStart();
        this.ticketEnd = ticketFormDto.getTicketEnd();

        this.ticketStartDate = ticketFormDto.getTicketStartDate();
        this.ticketEndDate = ticketFormDto.getTicketEndDate();

        this.ticketStartTime = ticketFormDto.getTicketEndTime();
        this.ticketEndTime = ticketFormDto.getTicketEndTime();

    }




}
