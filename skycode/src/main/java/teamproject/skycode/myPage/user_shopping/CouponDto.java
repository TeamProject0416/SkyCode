package teamproject.skycode.myPage.user_shopping;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class CouponDto {

    @Column
    @NotBlank(message = "쿠폰을 입력해주세요")
    private String couponCode;

}
