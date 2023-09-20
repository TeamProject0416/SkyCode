package teamproject.skycode.point;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import teamproject.skycode.common.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "point")
@Getter
@Setter
@ToString
public class PointEntity extends BaseEntity {
    @Id
    @Column(name = "point_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // 포인트 Id

    private String pointName; // 포인트 이름

    private Integer pointNum; // 포인트

    @OneToMany(mappedBy = "pointEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Member_PointEntity> member_pointEntityList = new ArrayList<>();

    public void updateEvent(PointFormDto pointFormDto) {
        this.pointName = pointFormDto.getPointName();
        this.pointNum = pointFormDto.getPointNum();
    }
}
