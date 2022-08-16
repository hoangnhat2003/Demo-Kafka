package backend.drivor.base.domain.components;

import backend.drivor.base.domain.utils.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
public class KafkaSender {

    private static final String TAG = KafkaSender.class.getSimpleName();

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void sendMessage(String message, String topicName) {
        kafkaTemplate.send(topicName, message);
    }

    @Async
    public void sendMessageWithCallback(String message,String topicName) {
        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topicName, message);
        future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
            @Override
            public void onSuccess(SendResult<String, Object> result) {
                LoggerUtil.i(TAG,String.format("Message {} delivered", message));

            }
            @Override
            public void onFailure(Throwable ex) {
                LoggerUtil.e(TAG,String.format("Unable to deliver message {}. {}", message,ex.getMessage()));
            }
        });
    }
}
