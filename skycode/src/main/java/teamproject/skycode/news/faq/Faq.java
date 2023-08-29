package teamproject.skycode.news.faq;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "faq")
public class Faq {

    @Id
    @Column(name = "faq_id")
    private Long id;

    @Column
    private String faqType;

    @Column
    private String faqQuestion;

    @Column
    private String faqAnswer;

    @Column
    private LocalDateTime regTime;



}
