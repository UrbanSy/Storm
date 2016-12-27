package kafka;

/**
 * Created by UrbanSy on 2016/12/27 19:36.
 */
public enum  AcksLevel {
    NEVER(0), LEADER(1), ISR(-1);

    private int value = 0;

    private AcksLevel(int value) {
        this.value = value;
    }

    public int value(){
        return this.value;
    }
}
