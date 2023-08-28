package teamproject.skycode.myPage.users;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Entity
@Setter
@ToString
public class EditEntity {

    @Id
    private Long id;

    private String name;

    private String gender;

    // 여기까지 member에서 가져올것들


    private String imgName;
    private String oriImgName;
    private String imgUrl;

}
