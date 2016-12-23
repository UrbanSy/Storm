package Chapter03.operator;

import Chapter03.model.DiagnosisEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import storm.trident.operation.BaseFunction;
import storm.trident.operation.TridentCollector;
import storm.trident.tuple.TridentTuple;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by UrbanSy on 2016/12/22 11:00.
 */
public class HourAssignment extends BaseFunction {

    private static final Long seriavalVersionUID = 1L;
    private static final Logger LOG = LoggerFactory.getLogger(HourAssignment.class);

    public void execute(TridentTuple tridentTuple, TridentCollector tridentCollector) {
        DiagnosisEvent diagnosis = (DiagnosisEvent) tridentTuple.getValue(0);
        String city = (String) tridentTuple.getValue(1);

        long timestamp = diagnosis.time;
        long hourSinceEpoch = timestamp/1000/60/60;

        LOG.error("Key = ["+city+" : "+hourSinceEpoch+"]");
        String Key = city + " : "+diagnosis.diagnosisCode+" : "+hourSinceEpoch;

        List<Object> values = new ArrayList<Object>();
        values.add(hourSinceEpoch);
        values.add(Key);
        tridentCollector.emit(values);
    }
}
