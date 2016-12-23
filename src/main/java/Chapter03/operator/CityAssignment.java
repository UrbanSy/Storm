package Chapter03.operator;

import Chapter03.model.DiagnosisEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import storm.trident.operation.BaseFunction;
import storm.trident.operation.TridentCollector;
import storm.trident.tuple.TridentTuple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by UrbanSy on 2016/12/22 10:22.
 */
public class CityAssignment extends BaseFunction {

    private static final Long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory.getLogger(CityAssignment.class);

    public static Map<String, double[]> cities = new HashMap<String, double[]>();

    //初始化城市     phl费城   nyc纽约   sf旧金山   la洛杉矶  纬度/经度
    {
        double[] phl = {39.875365, -75.249524};
        double[] nyc = {40.71448, -74.00598};
        double[] sf = {-31.4250142, -62.0841809};
        double[] la = {-34.05374, -118.2430};
        cities.put("PHL", phl);
        cities.put("NYC", nyc);
        cities.put("SF", sf);
        cities.put("LA", la);
    }

    public void execute(TridentTuple tridentTuple, TridentCollector tridentCollector) {
        DiagnosisEvent diagnosis = (DiagnosisEvent) tridentTuple.get(0);
        double leastDistance = Double.MAX_VALUE;
        String closestCity = "NONE";

        //查找最近的城市
        for (Map.Entry<String, double[]> city : cities.entrySet()) {
            double R = 6371;//赤道半径
            double x = (city.getValue()[0] - diagnosis.lng) * Math.cos((city.getValue()[0] + diagnosis.lng) / 2);
            double y = (city.getValue()[1] - diagnosis.lat);
            double d = Math.sqrt(x * x + y * y) * R;
            if (d < leastDistance) {
                leastDistance = d;
                closestCity = city.getKey();
            }
        }

        //发射
        List<Object> values = new ArrayList<Object>();
        values.add(closestCity);
        LOG.error("Closest City to lat=["+diagnosis.lat+"],lng=["+diagnosis.lng+"} " +
                "== ["+closestCity+" ], d=["+leastDistance+"]");
        tridentCollector.emit(values);
    }



}
