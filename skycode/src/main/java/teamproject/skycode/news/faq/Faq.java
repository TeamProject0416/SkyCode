package teamproject.skycode.news.faq;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
//@Table(name = "faq")
public class Faq {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String type;

    @Column
    private String faqQuestion;

    @Column
    private String faqAnswer;

    @Column
    private LocalDateTime regTime;

    @PrePersist
    protected void onCreate(){ regTime = LocalDateTime.now(); }

}
