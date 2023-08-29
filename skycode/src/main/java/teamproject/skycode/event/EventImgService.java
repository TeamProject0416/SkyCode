package teamproject.skycode.event;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional
public class EventImgService {
    @Value("${eventImgLocation}")
    private String eventImgLocation;

    private final EventImgRepository eventImgRepository;

    private final FileService fileService;

    public void saveEventImg(EventImg eventImg, MultipartFile eventImgFile) throws Exception {
        String oriImgName = eventImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";
        if (!StringUtils.isEmpty(oriImgName)) {
            imgName = fileService.uploadFile(eventImgLocation, oriImgName, eventImgFile.getBytes());
            imgUrl = "/images/event/" + imgName;
        }

        // 상품 이미지 정보 저장
        eventImg.updateEventImg(oriImgName, imgName, imgUrl);
        eventImgRepository.save(eventImg);
    }
    // saveEventImg(EventImg eventImg - 업로드된 이미지와 관련된 상품 이미지 종보를 가진 EventImg 객체입니다
    // MultipartFile eventImgFile - 업로드할 상품 이미지 파일 나타내는 MultipartFile
    // oriImgName - 업로드된 이미지 파일의 원래 파일명을 저장

    // eventImg.updateEventImg(oriImgName, imgName, imgUrl);
    // 업로드된 이미지 파일의 원래파일명, 저장된파일명, 이미지 url 을 업데이트

    // 업데이트된 상품의 이미지 정보를 데이터베이스에 저장

    public void updateEventImg(Long eventImgId, MultipartFile eventImgFile) throws Exception {
        if (!eventImgFile.isEmpty()) {
            EventImg savedEventImg = eventImgRepository.findById(eventImgId)
                    .orElseThrow(EntityNotFoundException::new);
            // 기존 이미지 정보를 가져오기

            //기존 이미지 파일 삭제
            if (!StringUtils.isEmpty(savedEventImg.getImgName())) {
                fileService.deleteFile(eventImgLocation + "/" +
                        savedEventImg.getImgName());
            }

            String oriImgName = eventImgFile.getOriginalFilename();
            // 새로운 이미지 파일의 원본 파일 이름을 가져온다
            String imgName = fileService.uploadFile(eventImgLocation, oriImgName, eventImgFile.getBytes());
            String imgUrl = "/images/event/" + imgName;
            savedEventImg.updateEventImg(oriImgName, imgName, imgUrl);
            // 이미지 정보 엔티티의 필드를 업데이트 한다
        }
    }
}
