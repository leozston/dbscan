package src.algorithm;

import java.util.List;

/**
 * Created by leoz on 2016/11/22.
 * function:工具类
 */
public class Utils {
    private static float SIMILAR_ERROR_DATA = -2;  //用 -2 表示计算相似度出错

    /**
     * 计算两支股票之间的相似度:这里用余弦相似度计算（当然可以有其他计算相似度的方法）
     * 这里计算相似度仅将change这个因子拿进来作为参考，为了接下来试验的精确性，可以那更多参考因子
     * */
    public static float getSimilarity(List<StockSimilarPropertyBean> x, List<StockSimilarPropertyBean> y) {
        if (x.size() != y.size()) {
            return SIMILAR_ERROR_DATA;
        }
        float xyValue = 0;
        float xxValue = 0;
        float yyValue = 0;
        for (int i = 0; i < x.size(); i++) {
            xyValue += x.get(i).getChange() * y.get(i).getChange();
            xxValue += x.get(i).getChange() * x.get(i).getChange();
            yyValue += y.get(i).getChange() * y.get(i).getChange();
        }
        if (xxValue == 0 || yyValue == 0) {
            return SIMILAR_ERROR_DATA;
        }
        return (float)(xyValue/(Math.sqrt(xxValue) * Math.sqrt(yyValue)));
    }



}
