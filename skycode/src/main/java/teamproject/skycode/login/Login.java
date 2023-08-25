package teamproject.skycode.login;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="member")
@Getter
@Setter
@ToString
public class Login {

    @Id
    @Column(name = "Member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    private String address;

}

//    public static Login createMember(LoginFormDto loginFormDto){
//        Login login = new Login();
//        login.setName(loginFormDto.getName());
//        login.setEmail(loginFormDto.getEmail());
//        login.setAddress(loginFormDto.getAddress());
//        login.setPassword(password);
//        return login;
//    }
//}
