package Chapter03.operator;

import clojure.lang.Obj;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import storm.trident.operation.BaseFunction;
import storm.trident.operation.TridentCollector;
import storm.trident.tuple.TridentTuple;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by UrbanSy on 2016/12/22 11:28.
 */
public class OutbreakDetector extends BaseFunction {

    private static final Long seriavalVersionUID = 1L;
    //    private static final Logger LOG = LoggerFactory.getLogger(OutbreakDetector.class);
    private static final int THRESHOLD = 10000;//设置阀值

    public void execute(TridentTuple tridentTuple, TridentCollector tridentCollector) {
        String key = (String) tridentTuple.getValue(0);
        Long count = (Long) tridentTuple.getValue(1);

        if (count > THRESHOLD) {
            List<Object> values = new ArrayList<Object>();
            values.add("Outbreak detected for [" + key + "]!");
            tridentCollector.emit(values);
        }
    }
}
