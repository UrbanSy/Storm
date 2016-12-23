package Chapter03.topology;

import Chapter03.operator.*;
import Chapter03.spout.DiagnosisEventSpout;
import Chapter03.state.OutbreakThreadFactory;
import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.generated.StormTopology;
import backtype.storm.tuple.Fields;
import storm.trident.Stream;
import storm.trident.TridentTopology;
import storm.trident.operation.builtin.Count;

/**
 * Created by UrbanSy on 2016/12/19 19:49.
 */
public class OutbreakDetectionTopology {
    public static StormTopology buidTopology(){
        TridentTopology topology = new TridentTopology();
        DiagnosisEventSpout spout = new DiagnosisEventSpout();
        Stream inputStream = topology.newStream("event1",spout);
        inputStream
                //过滤
                .each(new Fields("event"),new DiseaseFilter())
                //计算离事件最近的城市
                .each(new Fields("event"),new CityAssignment(),new Fields("city"))
                //转换时间
                .each(new Fields("event","city"),new HourAssignment(),new Fields("hour","cityDiseaseHour"))
                //分组计算
                .groupBy(new Fields("cityDiseaseHour"))
                //统计并持久化存储
                .persistentAggregate(new OutbreakThreadFactory(),new Count(),new Fields("count")).newValuesStream()
                //
                .each(new Fields("cityDiseaseHour","count"),new OutbreakDetector(),new Fields("alert"))
                //
                .each(new Fields("alert"),new DispatchAlert(),new Fields());
        return topology.build();
    }

    public static void main(String[] args) throws InterruptedException {
        Config conf = new Config();
        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("cdc",conf,buidTopology());
        Thread.sleep(200000);
        cluster.shutdown();
    }

}
