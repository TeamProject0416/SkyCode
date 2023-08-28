package teamproject.skycode.login;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Member {

@Id
    private Long id;

    private String name;

    private String password;

    private String birthday;

    private String address;

    private String number;

    private String email;

    //id 의 getter setter
    public Long getId(){
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // password 의 getter setter

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }
}
