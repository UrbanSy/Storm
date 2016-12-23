package Chapter03.state;

import backtype.storm.task.IMetricsContext;
import storm.trident.state.State;
import storm.trident.state.StateFactory;

import java.util.Map;

/**
 * Created by UrbanSy on 2016/12/22 14:34.
 */
public class OutbreakThreadFactory implements StateFactory{

    private static final Long serialVersionUID = 1L;

    public State makeState(Map conf, IMetricsContext metrics, int i, int i1) {
        return new OutbreakThreadState(new OutbreakBackingMap());
    }
}
