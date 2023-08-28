package teamproject.skycode.news.faq;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
@Table(name = "faq")
public class Faq {

    @Id
    @Column(name = "faq_id")
    private Long id;

    @Column
    private String faqTitle;

    @Column
    private String faqContent;

}
