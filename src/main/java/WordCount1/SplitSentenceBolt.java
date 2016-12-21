package WordCount1;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

import java.util.Map;

/**
 * Created by 53215 on 2016/12/18.
 */
public class SplitSentenceBolt extends BaseRichBolt {

    private OutputCollector collector;
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        this.collector = outputCollector;
    }

    public void execute(Tuple tuple) {
        String sentence = tuple.getStringByField("sentence");
        String[] words = sentence.split(" ");
        for (String word : words){
           // this.collector.emit(new Values(word));
            //这里将输出的tuple和输入的tuple锚定
            this.collector.emit(tuple,new Values(word));
        }
        //为了支持有保障的处理，需要修改bolt，将输出的tuple和输入的tuple锚定，并且应答确认输入的tuple
        this.collector.ack(tuple);
    }

    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("word"));
    }
}
