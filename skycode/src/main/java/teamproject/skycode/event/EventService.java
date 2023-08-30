package teamproject.skycode.event;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final EventImgService eventImgService;
    private final EventImgRepository eventImgRepository;

    public Long saveEvent(EventFormDto eventFormDto, List<MultipartFile> eventImgFileList) throws Exception {
        // 상품 등록
        Event event = eventFormDto.createEvent();
        eventRepository.save(event);

        // 이미지 등록
        for (int i = 0; i < eventImgFileList.size(); i++) {
            EventImg eventImg = new EventImg();
            eventImg.setEvent(event); // 해당 이미지 객체에 성품 정보를 연결
            if (i == 0)
                eventImg.setRepImgYn("Y"); // 이미지 넘버가 0이면 대표이미지
            else
                eventImg.setRepImgYn("N");
            eventImgService.saveEventImg(eventImg, eventImgFileList.get(i));
        }
        return event.getId();
    }

    public List<Event> findEvent(){
        return eventRepository.findAll();
    }


}
