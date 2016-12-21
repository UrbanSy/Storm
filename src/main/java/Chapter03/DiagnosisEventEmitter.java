package Chapter03;

import storm.trident.operation.TridentCollector;
import storm.trident.spout.ITridentSpout;
import storm.trident.topology.TransactionAttempt;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by UrbanSy on 2016/12/21 10:27.
 */
public class DiagnosisEventEmitter implements ITridentSpout.Emitter<Long>,Serializable {

    private static final long serialVersionUID = 1l;
    AtomicInteger successfulTransactions = new AtomicInteger(0);

    public void emitBatch(TransactionAttempt tx, Long coordinatorMeta, TridentCollector tridentCollector) {
            for (int i=0;i<10000;i++){
                List<Object>  events = new ArrayList<Object>();
                double lat = new Double(-30 + (int)(Math.random()*75));
                double lng = new Double(-20 + (int)(Math.random()*70));
                long time = System.currentTimeMillis();
                String diag = new Integer((320 + (int)(Math.random()*7))).toString();
                DiagnosisEvent event = new DiagnosisEvent(lat,lng,time,diag);
                events.add(event);
                tridentCollector.equals(events);
            }
    }

    public void success(TransactionAttempt transactionAttempt) {
        successfulTransactions.incrementAndGet();
    }

    public void close() {

    }
}
