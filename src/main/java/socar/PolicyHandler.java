package socar;

import socar.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{
    @Autowired MessageRepository messageRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString){}

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverReservationConfirmed_SendConfirmMsg(@Payload ReservationConfirmed reservationConfirmed){

        if(!reservationConfirmed.validate()) return;

        System.out.println("\n\n##### 예약확인 메시지 전달 : " + reservationConfirmed.toJson() + "\n\n");


        // carId 추출
        long carId = reservationConfirmed.getCarId(); // 예약 확정된 carId
        String msgString = "예약이 완료 되었습니다. 차량번호 : [" + carId +"]";

        // 메시지 전송
        sendMsg(carId, msgString);

    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverReservationCancelled_SendCancelMsg(@Payload ReservationCancelled reservationCancelled){

        if(!reservationCancelled.validate()) return;

        System.out.println("\n\n##### 예약취소 메시지 전달 : " + reservationCancelled.toJson() + "\n\n");


        // carId 추출
        long carId = reservationCancelled.getCarId(); // 취소된 carId
        String msgString = "예약이 취소 되었습니다. 차량번호 : [" + carId +"]";

        // 메시지 전송
        sendMsg(carId, msgString);

    }

    private void sendMsg(long carId, String msgString)     {

        // carId에 대해 msgString으로 SMS를 쌓는다
        Message msg = new Message();
        msg.setCarId(carId);
        msg.setContent(msgString);

        // DB Insert
        messageRepository.save(msg);
    }

}


