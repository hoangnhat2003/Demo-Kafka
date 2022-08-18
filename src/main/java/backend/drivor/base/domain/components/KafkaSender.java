package backend.drivor.base.domain.components;

import backend.drivor.base.config.kafka.KafkaTopicConfig;
import backend.drivor.base.domain.model.KafkaMessage;
import backend.drivor.base.domain.utils.GsonSingleton;
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

    @Autowired
    private KafkaTopicConfig kafkaTopicConfig;

    private static final String TAG = KafkaSender.class.getSimpleName();

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Async
    public void sendMessage(KafkaMessage message) throws Exception {

        LoggerUtil.i(TAG, String.format("Sending message {} to topic: {} , partition: {}", GsonSingleton.getInstance().toJson(message.getMessage()),message.getTopicName(), message.getPartition()));
        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(message.getTopicName(), message.getPartition(), null ,message.getMessage());
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
