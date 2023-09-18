package teamproject.skycode.point;


import lombok.Getter;
import lombok.Setter;
import teamproject.skycode.coupon.CouponEntity;
import teamproject.skycode.login.MemberEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member_PointEntity {
    @Id
    @Column(name = "MemPoint_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;

    @Column
    private Integer point;

    @Column
    private String pointStart; // 포인트 시작일 "yyyy-MM-dd"

    @Column
    private String pointEnd; // 포인트 종료일 "yyyy-MM-dd" 최대 3개월?

    @Column
    private String memberEmail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "point_id")
    private PointEntity pointEntity;

    @OneToMany(mappedBy = "memberPointEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PointHistoryEntity> pointHistoryEntityList = new ArrayList<>();

}
