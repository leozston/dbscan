package fileoperate;

import algorithm.BaseBean;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by leoz on 2017/1/11.
 */
public class FileOperateUtilTemp {
    private static SimpleDateFormat sdf  =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
    /**
     * 读预处理之后的股票文件，并将读到的信息写入stockClusterBeanList中
     * */
    public static void getStockClusterBeanList(String path) {
        List<BaseBean> stockClusterBeanList = Lists.newArrayList();
        File file=new File(path);
        File[] tempList = file.listFiles();
        System.out.println("预处理之后目录下文件个数："+tempList.length);
        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
                stockClusterBeanList.addAll(getStockClusterBean(tempList[i].toString()));
                System.out.println("读" + i + "个文件完毕");
            } else {
                getStockClusterBeanList(tempList[i].getPath());
            }
        }

        String outPath = "C:\\Users\\leoz\\Desktop\\data\\";
        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
        System.out.println(stockClusterBeanList.size());

        for (BaseBean baseBean : stockClusterBeanList) {
            myLogger(outPath + sdf.format(baseBean.getCarDate()) + ".txt", baseBean.toString());
        }

//        Map<String, String> dataInfoMap = Maps.newHashMap();
//        //写文件
//        int outTimes = 0;
//        int index = 0;
//        for (BaseBean baseBean : stockClusterBeanList) {
//            if (index % 1000 == 0) {
//                System.out.println("index:" + index);
//            }
//            String date = sdf.format(baseBean.getCarDate());
//            if (dataInfoMap.containsKey(date)) {
//                dataInfoMap.put(date, dataInfoMap.get(date) + "\n" + baseBean.toString());
//            } else {
//                dataInfoMap.put(date, baseBean.toString());
//            }
//
//            if (index == 10000) {
//                for (Map.Entry<String, String> entry : dataInfoMap.entrySet()) {
//                    myLogger(outPath + entry.getKey() + ".txt", entry.getValue());
//                }
//                System.out.println("输出次数：" + outTimes);
//                dataInfoMap.clear();
//                index = 0;
//            } else {
//                index++;
//            }
//
//        }
//        for (Map.Entry<String, String> entry : dataInfoMap.entrySet()) {
//            myLogger(outPath + entry.getKey() + ".txt", entry.getValue());
//        }
        System.out.println("目录完成");
    }

    /**
     * 将某一股票文件整理成聚类文件
     * */
    public static List<BaseBean> getStockClusterBean(String path) {
        List<BaseBean> baseBeanList = Lists.newArrayList();
        FileReader reader = null;
        try {
            reader = new FileReader(path);
        } catch (FileNotFoundException e) {
            System.out.println("***********文件路径错误*********");
            e.printStackTrace();
        }
        BufferedReader br = new BufferedReader(reader);

//        int lineNumber = 1;
        String line = null;
        try {
            while ((line = br.readLine()) != null) {
                if (line.trim().length() == 0) {
                    break;
                }
//                if (lineNumber % 100 != 1) {
//                    lineNumber++;
//                    continue;
//                }
                BaseBean baseBean = new BaseBean();
                String[] properties = line.split(",");
                for (int i = 0; i < properties.length; i++) {
                    switch (i) {
                        case 0:
                            baseBean.setCarNumber(properties[i]);
                            break;
                        case 1:
                            baseBean.setCarDate(stringToDate(properties[i]));
                            break;
                        case 2:
                            baseBean.setLongitude(properties[i]);
                            break;
                        case 3:
                            baseBean.setLatitude(properties[i]);
                            break;
                        default:
                            break;
                    }
                }
//                lineNumber++;
                baseBeanList.add(baseBean);
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
        return baseBeanList;
    }

    private static Date stringToDate(String dateString) {
        try {
            return sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    public static void myLogger(String path, String content) {
        //写文件
        FileWriter writer = null;
        try {
            writer = new FileWriter(path, true);
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