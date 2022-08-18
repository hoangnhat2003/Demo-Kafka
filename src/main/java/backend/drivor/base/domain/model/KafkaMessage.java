package backend.drivor.base.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KafkaMessage {
    private String topicName;
    private Integer partition;
    private Object message;
}
