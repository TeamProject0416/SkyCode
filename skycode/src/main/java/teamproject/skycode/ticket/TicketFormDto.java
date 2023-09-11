package teamproject.skycode.ticket;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class TicketFormDto {
    private Long id; // 티켓 Id

    @NotNull(message = "수량은 필수 입력 값입니다.")
    private Integer firstNum; // firstClass 수량
    @NotNull(message = "수량은 필수 입력 값입니다.")
    private Integer businessNum; // businessClass 수량
    @NotNull(message = "수량은 필수 입력 값입니다.")
    private Integer economyNum; // economyClass 수량

    @NotNull(message = "가격은 필수 입력 값입니다.")
    private Integer firstPrice; // firstClass 가격
    @NotNull(message = "가격은 필수 입력 값입니다.")
    private Integer businessPrice; // businessClass 가격
    @NotNull(message = "가격은 필수 입력 값입니다.")
    private Integer economyPrice; // economyClass 가격

    @NotNull(message = "재고 상태은 필수 입력 값입니다.")
    private TicketStatus firstStatus; // firstClass 티켓 재고 상태
    @NotNull(message = "재고 상태은 필수 입력 값입니다.")
    private TicketStatus businessStatus; // businessClass 티켓 재고 상태
    @NotNull(message = "재고 상태은 필수 입력 값입니다.")
    private TicketStatus economyStatus; // economyClass 티켓 재고 상태

    @NotNull(message = "출발지는 필수 입력 값입니다.")
    private TicketCountry ticketStart; // 출발지
    @NotNull(message = "도착지는 필수 입력 값입니다.")
    private TicketCountry ticketEnd; // 도착지

    @NotBlank(message = "출발일은 필수 입력 값입니다.")
    private String ticketStartDate; // "yyyy-MM-dd" 출발일
    @NotBlank(message = "도착일은 필수 입력 값입니다.")
    private String ticketEndDate; // "yyyy-MM-dd" 도착일

    @NotEmpty(message = "출발 시간은 필수 입력 값입니다.")
    private String ticketStartTime; // "hh-mm" 출발 시간
    @NotEmpty(message = "도착 시간은 필수 입력 값입니다.")
    private String ticketEndTime; // "hh-mm" 도착 시간

    private static ModelMapper modelMapper = new ModelMapper();

    public TicketEntity createTicket() {
        return modelMapper.map(this, TicketEntity.class);
    } // TicketFormDto 객체를 기반으로 새로운 Ticket 객체를 생성
    // this 는 TicketFormDto 객체 자체를 나타냅니다
    // TicketFormDto 객체의 필드값을 가지고 Ticket 객체를 생성

    public static TicketFormDto of(TicketEntity ticket) {
        return modelMapper.map(ticket, TicketFormDto.class);
    }
    // Ticket 객체에서 TicketFormDto 객체로 매핑

}
