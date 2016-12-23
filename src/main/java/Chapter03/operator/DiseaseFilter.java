package Chapter03.operator;

import Chapter03.model.DiagnosisEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import storm.trident.operation.BaseFilter;
import storm.trident.tuple.TridentTuple;

/**
 * Created by UrbanSy on 2016/12/22 9:32.
 */
public class DiseaseFilter extends BaseFilter {

    private static final long serialVersionUID =1L;
    private static final Logger LOG = LoggerFactory.getLogger(DiseaseFilter.class);

    public boolean isKeep(TridentTuple tridentTuple) {
        DiagnosisEvent diagnosis = (DiagnosisEvent) tridentTuple.getValue(0);
        Integer code = Integer.parseInt(diagnosis.diagnosisCode);
        if (code.intValue() <= 322) {
            LOG.error("Emitting disease [" + diagnosis.diagnosisCode + "]");
            return true;
        } else {
            LOG.error("Emitting disease [" + diagnosis.diagnosisCode + "]");
            return false;
        }

    }
}
