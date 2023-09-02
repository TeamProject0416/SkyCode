package teamproject.skycode.event;

import jdk.jfr.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.swing.text.Document;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final FileService fileService;

    @Value("${eventImgLocation}")
    private String eventImgLocation;


    public Long saveEvent(EventFormDto eventFormDto, MultipartFile eventImgFile1, MultipartFile eventImgFile2) throws Exception {
        // 상품 등록
        EventEntity event = eventFormDto.createEvent();

        // 폴더 없을시 폴더 생성
        Path directoryPath = Paths.get("/SkyCodeProject/img/event");

        try {
            // 디렉토리 생성
            Files.createDirectories(directoryPath);
            System.out.println(directoryPath + " 디렉토리가 생성되었습니다.");

        } catch (IOException e) {
            System.out.println(directoryPath + " 디렉토리 생성이 실패하였습니다.");
            e.printStackTrace();
        }

        // 파일 경로 설정
        String basePath = "/event";

        // 이미지 업로드 처리 및 파일명 생성
        String miniImgName = "";
        String miniOriImgName = eventImgFile1.getOriginalFilename();
        String miniImgUrl = "";

        if (miniOriImgName != null && !miniOriImgName.isEmpty()) {
            // 파일명 생성
            miniImgName = fileService.uploadFile(eventImgLocation + basePath, miniOriImgName,
                    eventImgFile1.getBytes());

            // 파일 경로 생성
            miniImgUrl = "/img/event/" + miniImgName;
        }

        String bigImgName = "";
        String bigOriImgName = eventImgFile2.getOriginalFilename();
        String bigImgUrl = "";

        if (bigOriImgName != null && !bigOriImgName.isEmpty()) {
            // 파일명 생성
            bigImgName = fileService.uploadFile(eventImgLocation + basePath, bigOriImgName,
                    eventImgFile2.getBytes());

            // 파일 경로 생성
            bigImgUrl = "/img/event/" + bigImgName;
        }

        // 상품 이미지 정보 저장
        event.updateEventImg(miniOriImgName, miniImgName, miniImgUrl,
                bigOriImgName, bigImgName, bigImgUrl);

        // DB 시간 저장
        LocalDateTime now = LocalDateTime.now();
        event.setRegTime(now);
        event.setUpdateTime(now);

        // 게시글 시간 저장 - 날짜까지만
        String formattedDate = now.toLocalDate().toString(); // "yyyy-MM-dd"
        event.setEventTime(formattedDate);

        // 이벤트 저장
        eventRepository.save(event);
        return event.getId();
    }

    @Transactional(readOnly = true)
    public EventFormDto getEventDtl(Long eventId) {
        EventEntity event = eventRepository.findById(eventId)
                .orElseThrow(EntityNotFoundException::new);
        // 해당 id의 상품 정보를 데이터 베이스에서 가져옴, 없으면 예외처리
        EventFormDto eventFormDto = EventFormDto.of(event);
        // 상품 정보를 eventFormDto 로 변환합니다
        return eventFormDto;
    }

    public Long updateEvent(EventFormDto eventFormDto) throws Exception {
        // 상품 수정
        EventEntity event = eventRepository.findById(eventFormDto.getId())
                .orElseThrow(EntityNotFoundException::new);
        event.updateEvent(eventFormDto);

        return event.getId();
    }


}