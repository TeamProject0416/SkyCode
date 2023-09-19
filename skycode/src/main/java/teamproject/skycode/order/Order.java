package teamproject.skycode.order;


import lombok.Getter;
import lombok.Setter;
import teamproject.skycode.login.MemberEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orderEntity")
@Getter
@Setter
public class Order {
    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;

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
    private Integer totalPrice; // 총 가격

    private LocalDateTime orderDate; // 주문일

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;
}