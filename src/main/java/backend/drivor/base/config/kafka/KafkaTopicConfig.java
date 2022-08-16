package backend.drivor.base.config.kafka;


import backend.drivor.base.domain.utils.LoggerUtil;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Properties;


@Component
public class KafkaTopicConfig {

    private static final String TAG = KafkaTopicConfig.class.getSimpleName();

    @Value("${kafka.bootstrap-servers}")
    private String bootstrapServers;

    public void createTopic(String topicName, Integer numPartitions) throws Exception {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", bootstrapServers);
        AdminClient admin = AdminClient.create(properties);

        //checking if topic already exists
        boolean alreadyExists = admin.listTopics().names().get().stream()
                .anyMatch(existingTopicName -> existingTopicName.equals(topicName));
        if (!alreadyExists) {
            //creating new topic
            LoggerUtil.i(TAG,String.format("Creating topic: %s", topicName));
            NewTopic newTopic = new NewTopic(topicName, numPartitions, (short) 1);
            admin.createTopics(Collections.singleton(newTopic)).all().get();
        }
    }
}
