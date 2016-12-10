package fileoperate;

import algorithm.BaseBean;
import algorithm.ClusterBean;
import algorithm.StockSimilarPropertyBean;
import com.google.common.collect.Lists;

import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * Created by leoz on 2016/11/22.
 */
public class FileOperateUtil {
    private static String stockDataPathPre = "F://dbscan//stock_data_pre//";   //数据预处理之前路径
    private static String stockDataPathAfter = "F://dbscan//stock_data_after//";   //数据预处理之后路径
    private static String logPath = "F://dbscan//log//log.txt";

    private static int stockDays = 482;  //粗略的处理，只要从2013/1/4日以来的股票
    private static int stockDaysLimit = 400;
    /**
     * 数据预处理函数，将一些数据剔除掉
     * */
    public void dataPreProcess() {
        File file=new File(stockDataPathPre);
        File[] tempList = file.listFiles();
        System.out.println("预处理之前目录下文件个数："+tempList.length);
        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
                dataPreProcessing(tempList[i].toString(), i);
                System.out.println("第" + i + "个已处理完成");
            }
        }
    }

    public void  dataPreProcessing(String fromPath, int index) {
        FileReader reader = null;
        try {
            reader = new FileReader(fromPath);
        } catch (FileNotFoundException e) {
            System.out.println("***********文件路径错误*********");
            e.printStackTrace();
        }
        BufferedReader br = new BufferedReader(reader);
        String line = null;
        List<StockSimilarPropertyBean> stockSimilarPropertyBeanList = Lists.newArrayList();
        int lineNumber = 0;
        try {
            while ((line = br.readLine()) != null) {
                if (lineNumber == 0) {
                    lineNumber ++;
                    continue;
                }
                if (line.trim().length() == 0) {
                    return;
                }
                StockSimilarPropertyBean stockSimilarPropertyBean = new StockSimilarPropertyBean();
//                System.out.println(line);
                String[] properties = line.split(",");
                for (int i = 0; i < properties.length; i++) {
                    switch (i) {
                        case 0:
                            stockSimilarPropertyBean.setCode(properties[i]);
                            break;
                        case 1:
                            stockSimilarPropertyBean.setDate(properties[i]);
//                            if (index == 1) {
//                                stockAllDayInfo.add(properties[i]);
//                            }
                            break;
                        case 6:
                            stockSimilarPropertyBean.setChange(Float.parseFloat(properties[i]));
                            break;
                        default:
                            break;
                    }
                }
                stockSimilarPropertyBeanList.add(stockSimilarPropertyBean);
                lineNumber++;
            }
        } catch (IOException e) {
            System.out.println("***********读取文件失败************");
            e.printStackTrace();
        }
        try {
            br.close();
            reader.close();
        } catch (IOException e) {
            System.out.println("***********关闭读文件流失败************");
            e.printStackTrace();
        }

        //只要从2013/1/4日以来的股票，方便进行比较
//        if (stockSimilarPropertyBeanList.size() != stockDays) {
//            return;
//        }
        if (stockSimilarPropertyBeanList.size() < stockDaysLimit) {
            return;
        }

        String fileName = fromPath.substring(fromPath.lastIndexOf("\\"), fromPath.length());
//        String stockName = fromPath.substring(fromPath.lastIndexOf("\\", fromPath.lastIndexOf(".")));
        //写文件
        FileWriter writer = null;
        try {
            writer = new FileWriter(stockDataPathAfter + fileName, true);
        } catch (IOException e) {
            System.out.println("写文件目录出错");
            e.printStackTrace();
        }
        BufferedWriter bw = new BufferedWriter(writer);
        int stockBeanIndex = 0;
        for (; stockBeanIndex < stockSimilarPropertyBeanList.size(); stockBeanIndex++) {
            try {
                bw.write(stockSimilarPropertyBeanList.get(stockBeanIndex).toString() + "\n");
            } catch (IOException e) {
                System.out.println("写文件出错");
                e.printStackTrace();
            }
        }
        try {
            bw.close();
            writer.close();
        } catch (IOException e) {
            System.out.println("关闭写文件流出错");
            e.printStackTrace();
        }
    }

    /**
     * 读预处理之后的股票文件，并将读到的信息写入StockSimilarPropertyBean中
     * */
    public Map<String, StockSimilarPropertyBean> readStockData(String path) {
        //TODO
        return null;
    }

    /**
     * 读预处理之后的股票文件，并将读到的信息写入stockClusterBeanList中
     * */
    public static List<ClusterBean> getStockClusterBeanList() {
        List<ClusterBean> stockClusterBeanList = Lists.newArrayList();
        File file=new File(stockDataPathAfter);
        File[] tempList = file.listFiles();
        System.out.println("预处理之后目录下文件个数："+tempList.length);
        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
                stockClusterBeanList.add(getStockClusterBean(tempList[i].toString()));
                System.out.println("读" + i + "个文件完毕");
            }
        }
        return stockClusterBeanList;
    }

    /**
     * 将某一股票文件整理成聚类文件
     * */
    public static ClusterBean getStockClusterBean(String path) {
        ClusterBean clusterBean = new ClusterBean();
        String stockName = path.substring(path.lastIndexOf("\\") + 1, path.lastIndexOf("."));
        clusterBean.setName(stockName);

        FileReader reader = null;
        try {
            reader = new FileReader(path);
        } catch (FileNotFoundException e) {
            System.out.println("***********文件路径错误*********");
            e.printStackTrace();
        }
        BufferedReader br = new BufferedReader(reader);

        List<BaseBean> baseBeanList = Lists.newArrayList();

        String line = null;
        int lineNumber = 0;
        try {
            while ((line = br.readLine()) != null) {
                if (lineNumber == 0) {
                    lineNumber ++;
                    continue;
                }
                if (line.trim().length() == 0) {
                    break;
                }
                BaseBean baseBean = new BaseBean();
//                System.out.println(line);
                String[] properties = line.split(",");
                for (int i = 0; i < properties.length; i++) {
                    switch (i) {
                        case 1:
                            baseBean.setDate(properties[i]);
                            break;
                        case 2:
                            baseBean.setChange(Float.parseFloat(properties[i]));
                            break;
                        default:
                            break;
                    }
                }
                baseBeanList.add(baseBean);
                lineNumber++;
            }
        } catch (IOException e) {
            System.out.println("***********读取文件失败************");
            e.printStackTrace();
        }
        try {
            br.close();
            reader.close();
        } catch (IOException e) {
            System.out.println("***********关闭读预处理后文件流失败************");
            e.printStackTrace();
        }
        clusterBean.setWeight(baseBeanList);
        return clusterBean;
    }


    private void preProcess(){
        this.dataPreProcess();
        System.out.printf("预处理完成");
    }

    public static void myLogger(String content) {
        //写文件
        FileWriter writer = null;
        try {
            writer = new FileWriter(logPath, true);
        } catch (IOException e) {
            System.out.println("写文件目录出错");
            e.printStackTrace();
        }
        BufferedWriter bw = new BufferedWriter(writer);
        try {
            bw.write(content + "\n");
        } catch (IOException e) {
            System.out.println("日志写文件出错");
            e.printStackTrace();
        }
        try {
            bw.close();
            writer.close();
        } catch (IOException e) {
            System.out.println("关闭日志写文件流出错");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        FileOperateUtil fileOperateUtil = new FileOperateUtil();
        fileOperateUtil.preProcess();
    }
}
