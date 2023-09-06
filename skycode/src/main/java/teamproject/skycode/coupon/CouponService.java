package teamproject.skycode.coupon;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@Transactional
@RequiredArgsConstructor
public class CouponService {
    private final CouponRepository couponRepository;

    public Long saveCoupon(CouponFormDto couponFormDto) throws Exception {
        // 상품 등록
        CouponEntity coupon = couponFormDto.createCoupon();

        // DB 시간 저장
        LocalDateTime now = LocalDateTime.now();
        coupon.setRegTime(now);
        coupon.setUpdateTime(now);

        // 쿠폰 로직
        coupon.setCode(couponCode());

        // 이벤트 저장
        couponRepository.save(coupon);
        return coupon.getId();
    }

    private String couponCode() {
        // 랜덤 코드 생성 로직을 구현
        int n = 16; // n자리 쿠폰
        char[] chs = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
                'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

        Random rd = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            char ch = chs[rd.nextInt(chs.length)];
            sb.append(ch);
        }
        return sb.toString();
    }
}
