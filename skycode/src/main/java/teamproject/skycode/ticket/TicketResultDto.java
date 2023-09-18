package teamproject.skycode.ticket;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import teamproject.skycode.constant.TicketCountry;

@Getter
@Setter
public class TicketResultDto {
    private String start;
    private String arrive;
    private String goingTime;
    private String userGrade;
    private String comingTime;
    private int goingPrice;
    private int comingPrice;

    public void setStart(String start) {
        this.start = start;
    }
    public void setArrive(String arrive) {
        this.arrive = arrive;
    }
    public void setGoingTime(String goingTime) {
        this.goingTime = goingTime;
    }
    public void setUserGrade(String userGrade) {
        this.userGrade = userGrade;
    }

    public void setComingTime(String comingTime) {
        this.comingTime = comingTime;
    }

    public void setGoingPrice(int goingPrice) {
        this.goingPrice = goingPrice;
    }

    public void setComingPrice(int comingPrice) {
        this.comingPrice = comingPrice;
    }
}
