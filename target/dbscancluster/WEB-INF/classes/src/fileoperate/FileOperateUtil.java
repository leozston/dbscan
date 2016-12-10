package src.fileoperate;

import src.algorithm.StockSimilarPropertyBean;

import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by leoz on 2016/11/22.
 */
public class FileOperateUtil {
    private static String stockDataPathPre = "F://dbscan//stock_data_pre//";   //数据预处理之前路径
    private static String stockDataPathAfter = "F://dbscan//stock_data_after//";   //数据预处理之后路径

//    private static final Logger logger = Logger.getLogger(this.getClass());
    /**
     * 数据预处理函数，将一些数据剔除掉
     * */
    public void dataPreProcessing(String fromPath, String toPath) {
        //TODO
    }


    /**
     * 读预处理之后的股票文件，并将读到的信息写入StockSimilarPropertyBean中
     * */
    public Map<String, StockSimilarPropertyBean> readStockData(String path) {
        //TODO
        return null;
    }
}
