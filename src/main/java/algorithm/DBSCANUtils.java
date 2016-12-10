package algorithm;

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
    public static float SIMILAR_ERROR_DATA = -2;  //用 -2 表示计算相似度出错
    private static SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
    /**
     * 计算两支股票之间的相似度:这里用余弦相似度计算（当然可以有其他计算相似度的方法）
     * 这里计算相似度仅将change这个因子拿进来作为参考，为了接下来试验的精确性，可以那更多参考因子
     * */
//    public static float getSimilarity(List<StockSimilarPropertyBean> x, List<StockSimilarPropertyBean> y) {
//        if (x.size() != y.size()) {
//            return SIMILAR_ERROR_DATA;
//        }
//        float xyValue = 0;
//        float xxValue = 0;
//        float yyValue = 0;
//
//        boolean flag = true;
//        int iIndex = 0;
//        int jIndex = 0;
//        if (x.size() == 0 || y.size() == 0) {
//            return SIMILAR_ERROR_DATA;
//        }
//        while(flag) {
//            String xStr = x.get(iIndex).getDate();
//            String yStr = y.get(jIndex).getDate();
//            long xTime = 0;
//            long yTime = 0;
//            try {
//                 xTime = sdf.parse(xStr).getTime();
//                 yTime = sdf.parse(yStr).getTime();
//            } catch (ParseException e) {
//                System.out.println("解析时间出错");
//                e.printStackTrace();
//            }
//
//            if (xTime > yTime) {
//                iIndex++;
//            } else if (xTime < yTime) {
//                jIndex++;
//            } else {
//                xyValue += x.get(iIndex).getChange() * y.get(jIndex).getChange();
//                xxValue += x.get(iIndex).getChange() * x.get(iIndex).getChange();
//                yyValue += y.get(jIndex).getChange() * y.get(jIndex).getChange();
//            }
//
//            if(x.size() <= iIndex) {
//                flag = false;
//            }
//            if(y.size() <= jIndex) {
//                flag = false;
//            }
//        }
//
//        if (xxValue == 0 || yyValue == 0) {
//            return SIMILAR_ERROR_DATA;
//        }
//        return (float)(xyValue/(Math.sqrt(xxValue) * Math.sqrt(yyValue)));
//    }

    public static float getSimilarity(ClusterBean one, ClusterBean two) {
        if(CollectionUtils.isEmpty(one.getWeight()) || CollectionUtils.isEmpty(two.getWeight())) {
            return SIMILAR_ERROR_DATA;
        }
        List<BaseBean> x = one.getWeight();
        List<BaseBean> y = two.getWeight();

        float xyValue = 0;
        float xxValue = 0;
        float yyValue = 0;

        boolean flag = true;
        int iIndex = 0;
        int jIndex = 0;

        while(flag) {
            String xStr = x.get(iIndex).getDate();
            String yStr = y.get(jIndex).getDate();
            long xTime = 0;
            long yTime = 0;
            try {
                xTime = sdf.parse(xStr).getTime();
                yTime = sdf.parse(yStr).getTime();
            } catch (ParseException e) {
                System.out.println("解析时间出错");
                e.printStackTrace();
            }

            if (xTime > yTime) {
                iIndex++;
            } else if (xTime < yTime) {
                jIndex++;
            } else {
                xyValue += x.get(iIndex).getChange() * y.get(jIndex).getChange();
                xxValue += x.get(iIndex).getChange() * x.get(iIndex).getChange();
                yyValue += y.get(jIndex).getChange() * y.get(jIndex).getChange();
                iIndex++;
                jIndex++;
            }

            if(x.size() <= iIndex) {
                flag = false;
            }
            if(y.size() <= jIndex) {
                flag = false;
            }
        }

        if (xxValue == 0 || yyValue == 0) {
            return SIMILAR_ERROR_DATA;
        }
        return (float)(xyValue/(Math.sqrt(xxValue) * Math.sqrt(yyValue)));
    }

    public static List<ClusterBean> getDomainBean(List<ClusterBean> clusterBeanList, ClusterBean current, float similarityLimit, int minPts){
        int count = 0;
        List<ClusterBean> resultBeanList = new ArrayList<ClusterBean>();
        for(ClusterBean tmp: clusterBeanList){
            float simil = getSimilarity(current, tmp);
            if (simil == SIMILAR_ERROR_DATA) {
                return null;
            }
            if (simil >= similarityLimit) {
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
