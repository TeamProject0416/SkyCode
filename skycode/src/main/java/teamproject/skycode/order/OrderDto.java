package teamproject.skycode.order;

import lombok.Getter;
import lombok.Setter;
import teamproject.skycode.constant.OrderStatus;

@Getter
@Setter
public class OrderDto {
    private Long id;
    // 가는 편
    private String goingStart; // 출발지
    private String goingArrive; // 도착지
    private String goingTime; // 가는 편 날짜 + 시간
    private Integer goingPrice; // 가격
    private String userGrade; // 등급

    // 오는 편
    private String comingStart; // 출발지
    private String comingArrive; // 도착지
    private String comingTime; // 오는 편 날짜 + 시간
    private Integer comingPrice; // 가격

    // 총 금액
    private Integer totalPrice; // 총가격

    private OrderStatus orderStatus; // 구매 상태

    private Long memberId;
    private String email;
    private String memberName;

}
