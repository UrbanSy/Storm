package WordCount1;

import Utils.ThreadUtils;
import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by 53215 on 2016/12/18.
 */
public class SentenceSpout extends BaseRichSpout {

    private ConcurrentHashMap<UUID,Values> pending;
    private SpoutOutputCollector collector;
    private String[] sentence = {
            "my dog has fleas",
            "i like cold beverages",
            "the dog ate my homework",
            "don't have a cow man",
            "i don't think i like fleas"
    };
    private int index = 0;

    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        this.collector = spoutOutputCollector;
        this.pending = new ConcurrentHashMap<UUID, Values>();
    }

    public void nextTuple() {
        Values values = new Values(sentence[index]);
        UUID msgid = UUID.randomUUID();
        this.pending.put(msgid,values);
        this.collector.emit(new Values(sentence[index]));
        index++;
        if (index >= sentence.length) {
            index = 0;
        }
        ThreadUtils.waitForMillis(1);
    }

    @Override
    public void ack(Object msgId) {
        this.pending.remove(msgId);
    }

    @Override
    public void fail(Object msgId) {
        this.collector.emit(this.pending.get(msgId),msgId);
    }

    //    public void nextTuple() {
//        if (index < sentence.length) {
//            this.collector.emit(new Values(sentence[index]));
//            index++;
//        }
//        ThreadUtils.waitForMillis(1);
//    }

    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("sentence"));
    }
}
