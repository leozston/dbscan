package fileoperate;

import algorithm.TaxiClusterBean;
import com.google.common.collect.Lists;

import java.io.*;
import java.util.List;

/**
 * Created by leoz on 2016/11/22.
 */
public class FileOperateUtil {

    private static String logPath = "F://dbscan//log//log.txt";

    /**
     * 读预处理之后的股票文件，并将读到的信息写入stockClusterBeanList中
     * */
    public static List<TaxiClusterBean> getStockClusterBeanList(String path) {
        List<TaxiClusterBean> stockClusterBeanList = Lists.newArrayList();
        File file=new File(path);
        File[] tempList = file.listFiles();
        System.out.println("预处理之后目录下文件个数："+tempList.length);
        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
                stockClusterBeanList.addAll(getStockClusterBean(tempList[i].toString()));
                System.out.println("读" + i + "个文件完毕");
            } else {
                stockClusterBeanList.addAll(getStockClusterBeanList(tempList[i].getPath()));
            }
        }
        return stockClusterBeanList;
    }

    /**
     * 将某一股票文件整理成聚类文件
     * */
    public static List<TaxiClusterBean> getStockClusterBean(String path) {
        List<TaxiClusterBean>  clusterBeanCurrentFileList = Lists.newArrayList();

        FileReader reader = null;
        try {
            reader = new FileReader(path);
        } catch (FileNotFoundException e) {
            System.out.println("***********文件路径错误*********");
            e.printStackTrace();
        }
        BufferedReader br = new BufferedReader(reader);

        int lineNumber = 1;
        String line = null;
        try {
            while ((line = br.readLine()) != null) {
                if (line.trim().length() == 0) {
                    break;
                }
                if (lineNumber % 100 != 1) {
                    lineNumber++;
                    continue;
                }
                TaxiClusterBean baseBean = new TaxiClusterBean();
                String[] properties = line.split(",");
                for (int i = 0; i < properties.length; i++) {
                    switch (i) {
                        case 2:
                            baseBean.setLongitude(Float.parseFloat(properties[i]));
                            break;
                        case 3:
                            baseBean.setLatitude(Float.parseFloat(properties[i]));
                            break;
                        default:
                            break;
                    }
                }
                lineNumber++;
                clusterBeanCurrentFileList.add(baseBean);
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
        return clusterBeanCurrentFileList;
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
}
