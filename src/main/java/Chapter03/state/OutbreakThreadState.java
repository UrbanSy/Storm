package Chapter03.state;

import storm.trident.state.map.IBackingMap;
import storm.trident.state.map.NonTransactionalMap;

/**
 * Created by UrbanSy on 2016/12/22 14:46.
 */
public class OutbreakThreadState extends NonTransactionalMap<Long> {

    protected OutbreakThreadState(OutbreakBackingMap outbreakBackingMap) {
        super(outbreakBackingMap);
    }
}
