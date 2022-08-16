package backend.drivor.base.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KafkaMessage<T> {
    private String topic;
    private Long partition;
    private Long offSet;
    private T message;
}
