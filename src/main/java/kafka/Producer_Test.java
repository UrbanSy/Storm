package kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * Created by UrbanSy on 2016/12/27 10:22.
 */
public class Producer_Test{
    /**
     * bin/kafka-topics.sh --create --zookeeper 172.10.1.230:2181,172.10.1.231:2181,172.10.1.232:2181/carpo/kafka --replication-factor 1 --partitions 1 --topic sy
     * @param args
     */
    public static void main(String[] args) {

        Properties properties = new Properties();
        properties.put("bootstrap.servers","172.10.1.230:9092");
        properties.put("acks","all");
        properties.put("retries","0");
        properties.put("linger.ms",1);
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String,String> producer = new KafkaProducer<String, String>(properties);
        producer.send(new ProducerRecord<String, String>("sy","test","symxl"));
        producer.close();
    }
}
