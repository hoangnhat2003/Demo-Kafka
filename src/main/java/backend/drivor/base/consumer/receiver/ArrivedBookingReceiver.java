package backend.drivor.base.consumer.receiver;

import backend.drivor.base.config.kafka.KafkaConfiguration;
import backend.drivor.base.consumer.event.BookingEvent;
import backend.drivor.base.domain.document.BookingHistory;
import backend.drivor.base.domain.utils.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class ArrivedBookingReceiver {

    private static final String TAG = ArrivedBookingReceiver.class.getSimpleName();

    @Autowired
    private BookingEvent event;

    @KafkaListener(topicPartitions = {@TopicPartition(topic = KafkaConfiguration.BOOKING_TOPIC, partitions = "0")
    })
    public void arrivedBookingRequest(@Payload BookingHistory dataFromTopic) {
        try {
            LoggerUtil.i(TAG, String.format("Booking from topic: {} " + dataFromTopic));
            event.arrivedBookingRequest(dataFromTopic);
            LoggerUtil.i(TAG, String.format("Process data from topic: {}", dataFromTopic));
        } catch (Exception e) {
            LoggerUtil.exception(TAG, e);
        }
    }
}
