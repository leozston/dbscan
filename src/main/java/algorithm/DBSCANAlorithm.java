package algorithm;

import com.google.common.collect.Lists;
import fileoperate.FileOperateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by leoz on 2016/11/22.
 */
public class DBSCANAlorithm {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private static String stockDataPathPre = "F://dbscan//stock_data_pre//";   //数据预处理之前路径

    public static List<TaxiClusterBean> stockClusterBeanList = Lists.newArrayList();
    private List<List<TaxiClusterBean>> clusterResults = Lists.newArrayList();

    private static int MinPts = 500;
    private static float radius = 0.01f;

    public void getStockClusterBean(){
        System.out.println("开始获得stockClusterBeanList取值" + new Date());
        FileOperateUtil.myLogger("开始获得stockClusterBeanList取值" + new Date());
        stockClusterBeanList = FileOperateUtil.getStockClusterBeanList(stockDataPathPre);
        System.out.println("获得stockClusterBeanList取值完毕" + new Date());
        FileOperateUtil.myLogger("获得stockClusterBeanList取值完毕" + new Date());
    }

    public void DBSCANCluster() {
        System.out.println("开始聚类时间：" + new Date());
        FileOperateUtil.myLogger("开始聚类时间：" + new Date());
        //设初始值
        for (int i = 0; i < stockClusterBeanList.size(); i++) {
            stockClusterBeanList.get(i).setVisited(false);
            stockClusterBeanList.get(i).setClassed(false);
        }

        //聚类实现
        int selectRange = stockClusterBeanList.size();
        do {
            System.out.println("第" + (stockClusterBeanList.size() + 1 - selectRange) + "次随机选择");
            //随机选择对象
            Random random = new Random();
            int randomNumber = random.nextInt(selectRange);
            TaxiClusterBean tmpNode = new TaxiClusterBean();
            tmpNode = stockClusterBeanList.get(randomNumber);
            stockClusterBeanList.set(randomNumber, stockClusterBeanList.get(selectRange - 1));
            stockClusterBeanList.set(selectRange - 1, tmpNode);
            selectRange--;

            //对当前对象进行处理
            int currentNodeNumber = selectRange - 1;
            if (tmpNode.isVisited()) {
                continue;
            }
            stockClusterBeanList.get(currentNodeNumber).setVisited(true);

            List<TaxiClusterBean> domainBean = DBSCANUtils.getDomainBean(stockClusterBeanList, stockClusterBeanList.get(currentNodeNumber), radius, MinPts);
            if (CollectionUtils.isEmpty(domainBean)) {
                stockClusterBeanList.get(currentNodeNumber).setKey(false);
                continue;
            }

            //DBSCAN核心
            List<TaxiClusterBean> currentCluster = Lists.newArrayList();
            for (int i = 0; i < domainBean.size(); i++) {
                if (!domainBean.get(i).isVisited()) {
                    domainBean.get(i).setVisited(true);
                    List<TaxiClusterBean> currentDomainBean = Lists.newArrayList();
                    currentDomainBean = DBSCANUtils.getDomainBean(stockClusterBeanList, domainBean.get(i), radius, MinPts);

                    if (!CollectionUtils.isEmpty(currentDomainBean)) {
                        for (TaxiClusterBean tmp : currentDomainBean) {
                            if (!domainBean.contains(tmp)) {
                                domainBean.add(tmp);
                            }
                        }
                    }
                }

                if (!domainBean.get(i).isClassed()) {
                    currentCluster.add(domainBean.get(i));
                    domainBean.get(i).setClassed(true);
                }
            }
            if (!CollectionUtils.isEmpty(currentCluster)) {
                clusterResults.add(currentCluster);
            }
        } while(selectRange > 0);

        System.out.println(new Date());
        //聚类结束
        System.out.println("聚类结束时间：" + new Date());
        FileOperateUtil.myLogger("聚类结束时间：" + new Date());
        //进行展示
        System.out.println("开始展示结果。。。");
        FileOperateUtil.myLogger("开始展示结果。。。" + new Date());
        display();
        System.out.println("展示结果完成");
        FileOperateUtil.myLogger("展示结果完成" + new Date());
    }

    public void display(){
        for (int i = 0; i < clusterResults.size(); i++) {
            String clusterInfo = "第" + i + "个簇，size：" + clusterResults.get(i).size();
            System.out.println(clusterInfo);
            FileOperateUtil.myLogger(clusterInfo);
            for (int j = 0; j < clusterResults.get(i).size(); j++) {
                String stockInfo = "出租车地理位置:" + clusterResults.get(i).get(j).toString();
                FileOperateUtil.myLogger(stockInfo);
            }
        }
    }

    public static void main(String[] args) {
        DBSCANAlorithm dbscanAlorithm = new DBSCANAlorithm();
        dbscanAlorithm.getStockClusterBean();
        //验证MinPts要小于stockClusterBeanList.size-1;
        if (!(MinPts < stockClusterBeanList.size() - 1)) {
            System.out.println("MinPts取值不符合规范，重新取值");
            System.exit(0);
        }

        dbscanAlorithm.DBSCANCluster();
    }
}
