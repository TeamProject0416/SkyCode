package teamproject.skycode.point;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import teamproject.skycode.constant.ActionType;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "point_history")
@Getter
@Setter
@ToString
public class PointHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id")
    private Long id;

    private String pointName;

    private Integer pointsUsed;

    private Integer pointsEarned;

    @Temporal(TemporalType.TIMESTAMP)
    private Date usageDate;

    @Enumerated(EnumType.STRING)
    private ActionType actionType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_point_id")
    private Member_PointEntity memberPointEntity;


}
