package socar;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;


@Entity
@Table(name="Message_table")
public class Message  {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long msgId;

    private Long carId;

    private String content;



    public Long getMsgId() {
        return msgId;
    }

    public void setMsgId(Long msgId) {
        this.msgId = msgId;
    }
    
    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }
    
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    



}
