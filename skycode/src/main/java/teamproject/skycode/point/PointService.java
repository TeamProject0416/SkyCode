package teamproject.skycode.point;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Random;

@Service
@Transactional
@RequiredArgsConstructor
public class PointService {
    private final PointRepository pointRepository;

    public Long savePoint(PointFormDto pointFormDto) throws Exception {
        // 포인트 등록
        PointEntity point = pointFormDto.createPoint();

        // DB 시간 저장
        LocalDateTime now = LocalDateTime.now();
        point.setRegTime(now);
        point.setUpdateTime(now);

        // 포인트 정보 저장
        pointRepository.save(point);
        return point.getId();
    }

    @Transactional(readOnly = true)
    public PointFormDto getPointDtl(Long pointId) {
        // 해당 id의 상품 정보를 데이터 베이스에서 가져옴, 없으면 예외처리
        PointEntity point = pointRepository.findById(pointId)
                .orElseThrow(EntityNotFoundException::new);
        // 상품 정보를 pointFormDto 로 변환합니다
        PointFormDto pointFormDto = PointFormDto.of(point);
        return pointFormDto;
    }

    public Long updatePoint(PointFormDto pointFormDto) throws Exception {
        // 상품 수정
        PointEntity point = pointRepository.findById(pointFormDto.getId())
                .orElseThrow(EntityNotFoundException::new);

        // DB 시간 저장
        LocalDateTime now = LocalDateTime.now();
        point.setUpdateTime(now);

        point.updateEvent(pointFormDto);

        return point.getId();
    }

    public void deletePoint(Long pointId) {
        PointEntity point = pointRepository.findById(pointId)
                .orElseThrow(EntityNotFoundException::new);

        // 데이터 삭제
        pointRepository.delete(point);
    }
}
