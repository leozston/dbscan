package algorithm;

import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by leoz on 2016/11/22.
 * function:工具类
 */
public class DBSCANUtils {
    public static float getDistance(TaxiClusterBean one, TaxiClusterBean two) {
        float distance = (float) Math.sqrt((one.getLongitude() - two.getLongitude()) * (one.getLongitude() - two.getLongitude())
                + (one.getLatitude() - two.getLatitude()) * (one.getLatitude() - two.getLatitude()));
        return distance;
    }

    public static List<TaxiClusterBean> getDomainBean(List<TaxiClusterBean> clusterBeanList, TaxiClusterBean current, float distanceLimit, int minPts){
        int count = 0;
        List<TaxiClusterBean> resultBeanList = Lists.newArrayList();
        for(TaxiClusterBean tmp: clusterBeanList){
            float distance = getDistance(current, tmp);
            if (distance <= distanceLimit) {
                ++count;
                resultBeanList.add(tmp);
            }
        }
        if (count >= minPts) {
            current.setKey(true);
            return resultBeanList;
        }
        return null;
    }
}
