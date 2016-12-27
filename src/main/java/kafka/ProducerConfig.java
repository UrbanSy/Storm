package kafka;

/**
 * Created by UrbanSy on 2016/12/27 19:34.
 */
public interface ProducerConfig {
    String brokerList();

    boolean isSync();

    AcksLevel acks();
}
