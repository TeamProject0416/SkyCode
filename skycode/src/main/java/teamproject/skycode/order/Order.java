package teamproject.skycode.order;


import lombok.Getter;
import lombok.Setter;
import teamproject.skycode.login.MemberEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Order {
    @Id
    @Column(name = "MemTicket_id")
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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGoingStart() {
        return goingStart;
    }

    public void setGoingStart(String goingStart) {
        this.goingStart = goingStart;
    }

    public String getGoingArrive() {
        return goingArrive;
    }

    public void setGoingArrive(String goingArrive) {
        this.goingArrive = goingArrive;
    }

    public String getGoingTime() {
        return goingTime;
    }

    public void setGoingTime(String goingTime) {
        this.goingTime = goingTime;
    }

    public Integer getGoingPrice() {
        return goingPrice;
    }

    public void setGoingPrice(Integer goingPrice) {
        this.goingPrice = goingPrice;
    }

    public String getUserGrade() {
        return userGrade;
    }

    public void setUserGrade(String userGrade) {
        this.userGrade = userGrade;
    }

    public String getComingStart() {
        return comingStart;
    }

    public void setComingStart(String comingStart) {
        this.comingStart = comingStart;
    }

    public String getComingArrive() {
        return comingArrive;
    }

    public void setComingArrive(String comingArrive) {
        this.comingArrive = comingArrive;
    }

    public String getComingTime() {
        return comingTime;
    }

    public void setComingTime(String comingTime) {
        this.comingTime = comingTime;
    }

    public Integer getComingPrice() {
        return comingPrice;
    }

    public void setComingPrice(Integer comingPrice) {
        this.comingPrice = comingPrice;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public MemberEntity getMemberEntity() {
        return memberEntity;
    }

    public void setMemberEntity(MemberEntity memberEntity) {
        this.memberEntity = memberEntity;
    }
}