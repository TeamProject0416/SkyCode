package teamproject.skycode.news.notion;

import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class NotionImgForm {

    private Long id;

    private String notionImgName;

    private String notionImgUrl;

    private static  ModelMapper modelMapper = new ModelMapper();

    public static NotionImgForm of(NotionImg notionImg) {
        return modelMapper.map(notionImg,NotionImgForm.class);
    }

}
