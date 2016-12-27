package kafka;

import kafka.producer.KeyedMessage;

public class Producer {

    private kafka.javaapi.producer.Producer<String, String> producer = null;

    public Producer(kafka.producer.ProducerConfig config) {
        producer = new kafka.javaapi.producer.Producer<String, String>(config);
    }

    public void send(String topic, String message) {
        KeyedMessage<String, String> data = new KeyedMessage<String, String>(topic, message);
        try {
            producer.send(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        if (producer != null) {
            try {
                producer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public TopicSender bind(String topic) {
        return new TopicSender(topic, this);
    }

    public class TopicSender {
        private String topic;
        private Producer outer;

        TopicSender(String topic, Producer outer) {
            this.topic = topic;
            this.outer = outer;
        }

        public void send(String message) {
            outer.send(topic, message);
        }

        public void close() {
            outer.close();
        }
    }
}

