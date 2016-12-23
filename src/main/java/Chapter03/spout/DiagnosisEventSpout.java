package Chapter03.spout;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.tuple.Fields;
import storm.trident.spout.ITridentSpout;

import java.util.Map;

/**
 * Created by UrbanSy on 2016/12/21 9:43.
 */
public class DiagnosisEventSpout implements ITridentSpout<Long> {

    private static final long serialVersionUID = 1L;
    SpoutOutputCollector collector;
    BatchCoordinator<Long> coordinator = new DefaultCoordinator();
    Emitter<Long> emitter = new DiagnosisEventEmitter();

    public BatchCoordinator getCoordinator(String s, Map map, TopologyContext topologyContext) {
        return coordinator;
    }

    public Emitter getEmitter(String s, Map map, TopologyContext topologyContext) {
        return emitter;
    }

    public Map getComponentConfiguration() {
        return null;
    }

    public Fields getOutputFields() {
        return new Fields("event");
    }
}
