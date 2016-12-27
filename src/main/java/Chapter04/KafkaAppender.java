package Chapter04;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import org.apache.kafka.clients.producer.Producer;

import java.util.Formatter;

/**
 * Created by UrbanSy on 2016/12/23 14:47.
 */
public class KafkaAppender extends AppenderBase<ILoggingEvent> {

    private String topic;
    private String zookeeperHost;
    private Producer<String,String> producer;
    private Formatter formatter;
    protected void append(ILoggingEvent iLoggingEvent) {

    }
}
