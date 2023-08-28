package teamproject.skycode.login;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@ToString
@Getter
@Setter
@Table(name = "member")
public class Member {

    @Id
    // 밑 두개는 더미데이터때문에 에러날까봐 넣은거임
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long id;

    @Column(unique = true)
    private String email;

    private String name;

    private String password;

    private String birthday;

    private String address;

    private String phone; // 핸드폰 번호

    //id 의 getter setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // password 의 getter setter

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
