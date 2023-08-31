package teamproject.skycode.event;

import jdk.jfr.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.Document;
import java.io.File;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final FileService fileService;

    @Value("${eventImgLocation}")
    private String eventImgLocation;

//    public void saveEvent(EventFormDto eventFormDto, MultipartFile eventImgFile1,MultipartFile eventImgFile2) throws Exception {
//        /*우리의 프로젝트경로를 담아주게 된다 - 저장할 경로를 지정*/
//        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\subImages\\event";
//
//        /*식별자 . 랜덤으로 이름 만들어줌*/
//        UUID uuid = UUID.randomUUID();
//
//        /*랜덤식별자_원래파일이름 = 저장될 파일이름 지정*/
//        String fileName = uuid + "_" + eventImgFile.getOriginalFilename();
//
//        /*빈 껍데기 생성*/
//        /*File을 생성할건데, 이름은 "name" 으로할거고, projectPath 라는 경로에 담긴다는 뜻*/
//        File saveFile = new File(projectPath, fileName);
//
//        file.transferTo(saveFile);
//
//        /*디비에 파일 넣기*/
//        board.setFilename(fileName);
//        /*저장되는 경로*/
//        board.setFilepath("/files/" + fileName); /*저장된파일의이름,저장된파일의경로*/
//
//        /*파일 저장*/
//        boardRepository.save(board);
//    }


    public Long saveEvent(EventFormDto eventFormDto, MultipartFile eventImgFile1 , MultipartFile eventImgFile2 ) throws Exception {
        // 상품 등록
        EventEntity event = eventFormDto.createEvent();


        // 파일 경로 설정
        String basePath = "/event";

        // 이미지 업로드 처리 및 파일명 생성
        String miniImgName = "";
        String miniOriImgName = eventImgFile1.getOriginalFilename();
        String miniImgUrl = "";

        if (miniOriImgName != null && !miniOriImgName.isEmpty()) {
            // 파일명 생성
            miniImgName = fileService.uploadFile(eventImgLocation+ basePath, miniOriImgName,
                    eventImgFile1.getBytes());

            // 파일 경로 생성
            miniImgUrl = "/img/event/" + miniImgName;
        }

        String bigImgName = "";
        String bigOriImgName = eventImgFile2.getOriginalFilename();
        String bigImgUrl = "";

        if (bigOriImgName != null && !bigOriImgName.isEmpty()) {
            // 파일명 생성
            bigImgName = fileService.uploadFile(eventImgLocation+ basePath, bigOriImgName,
                    eventImgFile1.getBytes());

            // 파일 경로 생성
            bigImgUrl = "/img/event/" + bigImgName;
        }

        // 시간 저장
        event.setRegTime(LocalDateTime.now());

        LocalDateTime regTime = event.getRegTime();
        String formattedDate = regTime.toLocalDate().toString(); // "yyyy-MM-dd"

        event.setTime(formattedDate);


        // 상품 이미지 정보 저장
        event.updateEventImg(miniOriImgName, miniImgName, miniImgUrl,
                bigOriImgName, bigImgName, bigImgUrl);

        // 이벤트 저장
        eventRepository.save(event);
        return event.getId();
    }

    // 가상적인 파일명 생성 로직, 실제로는 더 복잡한 로직이 필요할 수 있음
    private String generateFileName() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        int randomSuffix = new Random().nextInt(10000); // 0부터 9999 사이의 랜덤 숫자
        return "file_" + timeStamp + "_" + randomSuffix + ".jpg";
    }
}