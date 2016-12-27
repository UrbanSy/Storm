package kafka;

import java.util.Map;
import java.util.Properties;

/**
 * Created by UrbanSy on 2016/12/27 19:40.
 */
public class ProducerFactory {
    public static Producer build(ProducerConfig config) {
        return build(config, null);
    }

    public static Producer build(ProducerConfig config, Map<String, String> extraSetting) {
        if (config.brokerList() == null || "".equals(config.brokerList()))
            throw new RuntimeException("broker list can not be null .");
        if (config.acks() == null)
            throw new RuntimeException("acks option can not be null .");

        Properties props = new Properties();
        props.put("producer.type", config.isSync() ? "sync" : "async");
        props.put("metadata.broker.list", config.brokerList());
        props.put("request.required.acks", config.acks().value());
        props.put("compression.codec", "0");
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        props.put("request.required.acks", "1");
        if (extraSetting != null) {
            for (String key : extraSetting.keySet()) {
                props.put(key, extraSetting.get(key));
            }
        }
        kafka.producer.ProducerConfig producerConfig = new kafka.producer.ProducerConfig(props);
        return new Producer(producerConfig);
    }
}
