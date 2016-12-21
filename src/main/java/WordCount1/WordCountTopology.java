package WordCount1;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import Utils.ThreadUtils;
/**
 * Created by UrbanSy on 19:06.
 *
 */
public class WordCountTopology{

    /**
    * 组件唯一标识符
    * */
    private static final String SENTENCE_SPOUT_ID = "sentence-spout";
    private static final String SPLIT_BOLT_ID = "split-bolt";
    private static final String COUNT_BOLT_ID = "count-bolt";
    private static final String REPORT_BOLT_ID = "report-bolt";
    private static final String TOPOLOGY_NAME = "word-count-topology";

    public static void main(String[] args) {
        SentenceSpout spout = new SentenceSpout();
        SplitSentenceBolt splitBolt = new SplitSentenceBolt();
        WordCountBolt countBolt = new WordCountBolt();
        ReportBolt reportBolt = new ReportBolt();

        TopologyBuilder builder = new TopologyBuilder();
        //builder.setSpout(SENTENCE_SPOUT_ID,spout);
        //storm允许设定每个task对应的executor个数和每个executor可执行的task的个数
        builder.setSpout(SENTENCE_SPOUT_ID,spout,2);

        //builder.setBolt(SPLIT_BOLT_ID,splitBolt).shuffleGrouping(SENTENCE_SPOUT_ID);
        builder.setBolt(SPLIT_BOLT_ID,splitBolt,2).setNumTasks(4).shuffleGrouping(SENTENCE_SPOUT_ID);

        //builder.setBolt(COUNT_BOLT_ID,countBolt).fieldsGrouping(SPLIT_BOLT_ID,new Fields("word"));
        builder.setBolt(COUNT_BOLT_ID,countBolt,4).fieldsGrouping(SPLIT_BOLT_ID,new Fields("word"));

        builder.setBolt(REPORT_BOLT_ID,reportBolt).globalGrouping(COUNT_BOLT_ID);

        Config config = new Config();
        //设置两个worker，默认是一个
        config.setNumWorkers(2);
        LocalCluster cluster = new LocalCluster();

        cluster.submitTopology(TOPOLOGY_NAME,config,builder.createTopology());
        ThreadUtils.waitForSeconds(10);
        cluster.killTopology(TOPOLOGY_NAME);
        cluster.shutdown();
    }
}
