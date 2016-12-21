package Chapter03;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import storm.trident.spout.ITridentSpout;

import java.io.Serializable;

/**
 * Created by UrbanSy on 2016/12/21 10:15.
 */
public class DefaultCoordinator implements ITridentSpout.BatchCoordinator<Long>,Serializable{

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory.getLogger(DefaultCoordinator.class);

    public Long initializeTransaction(long l, Long aLong, Long x1) {
        LOG.info("Initializing Transaction ["+l+"]");
        return null;
    }

    public void success(long l) {
        LOG.info("Successful Transaction ["+l+"]");
    }

    public boolean isReady(long l) {
        return true;
    }

    public void close() {

    }
}
