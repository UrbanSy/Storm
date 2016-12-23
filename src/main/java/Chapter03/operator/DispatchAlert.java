package Chapter03.operator;

import com.esotericsoftware.minlog.Log;
import storm.trident.operation.BaseFunction;
import storm.trident.operation.TridentCollector;
import storm.trident.tuple.TridentTuple;

/**
 * Created by UrbanSy on 2016/12/22 11:36.
 */
public class DispatchAlert extends BaseFunction {

    private static final Long seriaVersionUID = 1L;

    public void execute(TridentTuple tridentTuple, TridentCollector tridentCollector) {
        String alert = (String) tridentTuple.getValue(0);
        Log.error("ALERT RECEIVED [" + alert + "]");
        Log.error("Dispatch the national guard!");
        System.exit(0);
    }
}
