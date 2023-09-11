package teamproject.skycode.event;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import teamproject.skycode.common.FileService;

import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final FileService fileService;

    @Value("${imgLocation}")
    private String imgLocation;


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
            miniImgName = fileService.uploadFile(imgLocation + basePath, miniOriImgName,
                    eventImgFile1.getBytes());

            // 파일 경로 생성
            miniImgUrl = "/img/event/" + miniImgName;
        }

        String bigImgName = "";
        String bigOriImgName = eventImgFile2.getOriginalFilename();
        String bigImgUrl = "";

        if (bigOriImgName != null && !bigOriImgName.isEmpty()) {
            // 파일명 생성
            bigImgName = fileService.uploadFile(imgLocation + basePath, bigOriImgName,
                    eventImgFile2.getBytes());

            // 파일 경로 생성
            bigImgUrl = "/img/event/" + bigImgName;
        }

        // 상품 이미지 정보 저장
        event.updateEventImg(miniImgName, miniOriImgName, miniImgUrl,
                bigImgName, bigOriImgName, bigImgUrl);

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
        // 해당 id의 상품 정보를 데이터 베이스에서 가져옴, 없으면 예외처리
        EventEntity event = eventRepository.findById(eventId)
                .orElseThrow(EntityNotFoundException::new);
        // 상품 정보를 eventFormDto 로 변환합니다
        EventFormDto eventFormDto = EventFormDto.of(event);
        return eventFormDto;
    }

    public Long updateEvent(EventFormDto eventFormDto, MultipartFile eventImgFile1, MultipartFile eventImgFile2) throws Exception {
        // 상품 수정
        EventEntity event = eventRepository.findById(eventFormDto.getId())
                .orElseThrow(EntityNotFoundException::new);

        // 파일 경로 설정
        String basePath = "/event";

        // 이미지 업로드 처리 및 파일명 생성
        String miniImgName = event.getMiniImgName();
        String miniOriImgName = event.getMiniOriImgName();
        String miniImgUrl = event.getMiniImgUrl();

        if (eventImgFile1 != null && !eventImgFile1.isEmpty()) {

            // 수정 전 파일 삭제하기
            String filePath = imgLocation + basePath + "/" + miniImgName;
            File fileToDelete = new File(filePath);
            if (fileToDelete.delete()) {
                System.out.println("파일이 성공적으로 삭제되었습니다.");
            } else {
                System.err.println("파일 삭제 중 오류가 발생했습니다.");
            }

            // 파일명 가져오기
            miniOriImgName = eventImgFile1.getOriginalFilename();

            // 파일명 생성
            miniImgName = fileService.uploadFile(imgLocation + basePath, miniOriImgName,
                    eventImgFile1.getBytes());

            // 파일 경로 생성
            miniImgUrl = "/img/event/" + miniImgName;
        }

        // 이미지 업로드 처리 및 파일명 생성
        String bigImgName = event.getBigImgName();
        String bigOriImgName = event.getBigOriImgName();
        String bigImgUrl = event.getBigImgUrl();

        if (eventImgFile2 != null && !eventImgFile2.isEmpty()) {

            // 수정 전 파일 삭제하기
            String filePath = imgLocation + basePath + "/" + bigImgName;
            File fileToDelete = new File(filePath);
            if (fileToDelete.delete()) {
                System.out.println("파일이 성공적으로 삭제되었습니다.");
            } else {
                System.err.println("파일 삭제 중 오류가 발생했습니다.");
            }

            // 파일명 가져오기
            bigOriImgName = eventImgFile2.getOriginalFilename();

            // 파일명 생성
            bigImgName = fileService.uploadFile(imgLocation + basePath, bigOriImgName,
                    eventImgFile2.getBytes());

            // 파일 경로 생성
            bigImgUrl = "/img/event/" + bigImgName;

        }

        // DB 시간 저장
        LocalDateTime now = LocalDateTime.now();
        event.setUpdateTime(now);

        eventFormDto.setMiniImgName(miniImgName);
        eventFormDto.setMiniOriImgName(miniOriImgName);
        eventFormDto.setMiniImgUrl(miniImgUrl);

        eventFormDto.setBigImgName(bigImgName);
        eventFormDto.setBigOriImgName(bigOriImgName);
        eventFormDto.setBigImgUrl(bigImgUrl);

        event.updateEvent(eventFormDto);

        return event.getId();
    }

    // 이벤트 삭제
    public void deleteEvent(Long eventId) {
        EventEntity event = eventRepository.findById(eventId)
                .orElseThrow(EntityNotFoundException::new);

        // 파일 경로 설정
        String basePath = "/event";

        // 파일 삭제하기
        if (!event.getMiniImgName().isEmpty()) {
            String filePath = imgLocation + basePath + "/" + event.getMiniImgName();
            System.out.println("filePath : " + filePath);
            File fileToDelete = new File(filePath);
            if (fileToDelete.delete()) {
                System.out.println("파일이 성공적으로 삭제되었습니다.");
            } else {
                System.err.println("파일 삭제 중 오류가 발생했습니다.");
            }
        }

        if (!event.getBigImgName().isEmpty()) {
            String filePath = imgLocation + basePath + "/" + event.getBigImgName();
            File fileToDelete = new File(filePath);
            if (fileToDelete.delete()) {
                System.out.println("파일이 성공적으로 삭제되었습니다.");
            } else {
                System.err.println("파일 삭제 중 오류가 발생했습니다.");
            }
        }

        // 데이터 삭제
        eventRepository.delete(event);
    }

    @Transactional(readOnly = true)
    public Page<EventEntity> getEventPage(EventStatus eventStatus, Pageable pageable) {
        return eventRepository.findByEventStatusOrderByRegTimeDesc(eventStatus, pageable);
    }

}