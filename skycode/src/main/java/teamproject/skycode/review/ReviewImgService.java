package teamproject.skycode.review;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewImgService {


    @Value("${reviewImgLocation}")
    private String reviewImgLocation;

    private final ReviewImgRepository reviewImgRepository;
    private final FileService fileService;

    public void saveItemImg(ReviewImg reviewImg, MultipartFile itemImgFile) throws Exception{
        String oriImgName = itemImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";
        if(!StringUtils.isEmpty(oriImgName)){
            imgName = fileService.uploadFile(reviewImgLocation, oriImgName,
                    itemImgFile.getBytes());
            imgUrl = "/images/item/" + imgName;
        }

//        상품 이미지 정보 저장
        reviewImg.updateItemImg(oriImgName, imgName, imgUrl);
        reviewImgRepository.save(reviewImg);
    }

    public void updateItemImg(Long itemImgId, MultipartFile itemImgFile) throws Exception{
        if(!itemImgFile.isEmpty()){
            ReviewImg savedItemImg = reviewImgRepository.findById(itemImgId)
                    .orElseThrow(EntityNotFoundException::new);
//            기존 이미지 정보를 가져오기

            //기존 이미지 파일 삭제
            if(!StringUtils.isEmpty(savedItemImg.getImgName())) {
                fileService.deleteFile(reviewImgLocation+"/"+
                        savedItemImg.getImgName());
            }

            String oriImgName = itemImgFile.getOriginalFilename();  // 새로운 이미지 파일의 원본 파일 이름을 가져온다.
            String imgName = fileService.uploadFile(reviewImgLocation, oriImgName, itemImgFile.getBytes());
            String imgUrl = "reviewImages/review/" + imgName;
            savedItemImg.updateItemImg(oriImgName, imgName, imgUrl); // -> ItemImg 에 있는 updateItemImg() 메서드
//            이미지 정보 엔티티의 필드를 업데이트 합니다.
        }
    }
}
