package backend.drivor.base.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KafkaMessage<T> {
    private String topicName;
    private Integer numPartitions;
    private Integer partition;
    private String key;
    private T message;
}
