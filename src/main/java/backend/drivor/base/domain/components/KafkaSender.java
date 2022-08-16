package backend.drivor.base.domain.components;

import backend.drivor.base.config.kafka.KafkaTopicConfig;
import backend.drivor.base.domain.model.KafkaMessage;
import backend.drivor.base.domain.utils.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.support.MessageBuilder;
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

    public void sendMessage(String message, String topicName) {
        kafkaTemplate.send(topicName, message);
    }

    @Async
    public void sendMessageWithCallback(KafkaMessage message) {
        Message<Object> msg = MessageBuilder
                .withPayload(message.getMessage())
                .setHeader(KafkaHeaders.TOPIC,message.getTopic())
                .setHeader(KafkaHeaders.PARTITION_ID, 0)
                .build();

//        LOG.info("sending message='{}' to topic='{}'", data, topicFoo);
        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(msg);
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
