package teamproject.skycode.news.inquiry;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

public class Inquiry_file {
    @Id
    @SequenceGenerator(name = "INQUIRY_FILE_SEQ_GENERATOR", sequenceName = "INQUIRY_FILE_SEQUENCE", allocationSize = 1, initialValue = 20000)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "INQUIRY_FILE_SEQ_GENERATOR")

    @Column(name = "INQUIRY_FILE_ID")
    private Long id;

    @Column(name = "INQUIRY_FILE_URL")
    private String url;

    @Column(name = "INQUIRY_FILE_TYPE")
    private String type;

    @Column(name = "INQUIRY_FILE_NAME")
    private String name;

    @Column(name = "INQUIRY_FILE_SIZE")
    private Long size;

    @ManyToOne
    @JoinColumn(name = "INQUIRY_ID")
    @JsonIgnoreProperties({"inquiry_file"})
    private Inquiry inquiry;
}
