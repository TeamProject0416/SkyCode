package teamproject.skycode.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDate;
import java.time.LocalDateTime;

@EntityListeners(value = {AuditingEntityListener.class})
// 생성일 수정일 자동 관리
@MappedSuperclass
// 다른 엔티티 클래스에서 상속 받아 사용 가능
@Getter
@Setter
public abstract class BaseTimeEntity {
    @CreatedDate
    //엔티티 생성될때 해당 필드에 자동으로 생성일 저장
    @Column(updatable = false)
    private LocalDateTime regTime;

    @LastModifiedDate
    //jpa 의 auditing 기능으로, 엔티티가 수정될 때 해당 필드에 자동으로 수정일을 넣어줌
    private LocalDateTime updateTime;
}
