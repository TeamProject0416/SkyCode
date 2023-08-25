package teamproject.skycode.news.inquiry;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.catalina.User;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString

public class Inquiry {

    @Id
    @Column(name = "INQUIRY_ID")
    private Long id;

    @JsonIgnoreProperties({"inquiry"})
    @JoinColumn(name = "USER_UUID")
    @ManyToOne
    private User user;

    @JsonIgnoreProperties({"inquiry"})
    @OneToMany(mappedBy = "inquiry", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private List<Inquiry_file> inquiry_file;

}
