package algorithm;

import com.google.common.collect.Lists;
import fileoperate.FileOperateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by leoz on 2016/11/22.
 */
public class DBSCANAlorithm {
    private Logger logger = LoggerFactory.getLogger(getClass());
    public static List<ClusterBean> stockClusterBeanList = Lists.newArrayList();
    private List<List<ClusterBean>> clusterResults = Lists.newArrayList();

    private static int MinPts = 4;
    private static float radius = 0.65f;

    private List<Float> K_DISTANCES = Lists.newArrayList();
    private List<Float> K_SIMILARS = Lists.newArrayList();

    public void getStockClusterBean(){
        System.out.println("开始获得stockClusterBeanList取值" + new Date());
        FileOperateUtil.myLogger("开始获得stockClusterBeanList取值" + new Date());
        stockClusterBeanList = FileOperateUtil.getStockClusterBeanList();
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
            ClusterBean tmpNode = new ClusterBean();
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

            List<ClusterBean> domainBean = DBSCANUtils.getDomainBean(stockClusterBeanList, stockClusterBeanList.get(currentNodeNumber), radius, MinPts);
            if (CollectionUtils.isEmpty(domainBean)) {
                stockClusterBeanList.get(currentNodeNumber).setKey(false);
                continue;
            }

            //DBSCAN核心
            List<ClusterBean> currentCluster = Lists.newArrayList();
            for (int i = 0; i < domainBean.size(); i++) {
                if (!domainBean.get(i).isVisited()) {
                    domainBean.get(i).setVisited(true);
                    List<ClusterBean> currentDomainBean = Lists.newArrayList();
                    currentDomainBean = DBSCANUtils.getDomainBean(stockClusterBeanList, domainBean.get(i), radius, MinPts);

                    if (!CollectionUtils.isEmpty(currentDomainBean)) {
                        for (ClusterBean tmp : currentDomainBean) {
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
                String stockInfo = "股票名称:" + clusterResults.get(i).get(j).getName() + "\n";
                stockInfo += clusterResults.get(i).get(j).getWeight().toString() + "  ";
//                System.out.println(stockInfo);
                FileOperateUtil.myLogger(stockInfo);
            }
        }
    }

    public void setRadius() {
        System.out.println("开始设置radius取值" + new Date());
        FileOperateUtil.myLogger("开始设置radius取值" + new Date());
        for (int i = 0; i < stockClusterBeanList.size(); i++) {
            //计算每一个节点据其他节点的相似度
            List<Float> currentNodeSimilar = Lists.newArrayList();
            for (int j = 0; j < stockClusterBeanList.size(); j++) {
                if (i != j) {
                    float similar = DBSCANUtils.getSimilarity(stockClusterBeanList.get(i), stockClusterBeanList.get(j));
                    if (similar == DBSCANUtils.SIMILAR_ERROR_DATA) {
                        System.out.println("数据有问题，请做检查再计算");
                        System.exit(0);
                    }
                    currentNodeSimilar.add(similar);
                }
            }
            //对相似度按从大到小距离排序
            Collections.sort(currentNodeSimilar);
            Collections.reverse(currentNodeSimilar);
            FileOperateUtil.myLogger("计算相似度：" + currentNodeSimilar.toString());
            //取第K大的添加到K_SIMILAR中
            if (MinPts < currentNodeSimilar.size()) {
                K_SIMILARS.add(currentNodeSimilar.get(MinPts));
            }
            System.out.println("第" + i + "个相似度计算完毕");
        }
        //对K_SIMILAR从大到小排序，对获得到的值放在excel中（这里可以用代码实现）取得变化最快的
        Collections.sort(K_SIMILARS);
        Collections.reverse(K_SIMILARS);
        for (int i = 0; i < K_SIMILARS.size(); i++) {
            if (K_SIMILARS.get(i) <= 0) {
                System.out.println("相似度数据有误，为了方便计算，本实验仅考虑相似度大于0的，小于0的可以额外考虑");
                System.exit(0);
            }
            K_DISTANCES.add((float)1.0/K_SIMILARS.get(i));
        }
        FileOperateUtil.myLogger("K_SIMILAR:" + K_SIMILARS.toString());
        //设置半径
        float currentRadius = getRadiusList();
        radius = (float)1.0/currentRadius;
        System.out.println("radius 设值结束:" + radius + "    "  + new Date());
        FileOperateUtil.myLogger("radius 设值结束:" + radius + "    "  + new Date());
    }

    /**
     *博客中说变化最快的位置，该如何取点，是取前一个还是取后一个，后期通过实验验证
     * */
    private float getRadiusList() {
        List<Float> radiusList = Lists.newArrayList();
        int offset = 0;
        float currentMaxRadius = (float) 1.0/radius;
        float maxFall = Float.MIN_VALUE;
        while (offset < K_DISTANCES.size() - 1) {
            float currentFall = K_DISTANCES.get(offset + 1) - K_DISTANCES.get(offset);
            if (currentFall > maxFall) {
                maxFall = currentFall;
                currentMaxRadius = K_DISTANCES.get(offset);
            }
            offset++;
        }
        //这里取index还是取index+1 后期通过实验验证，论文中并没有给具体说法
        return currentMaxRadius;
    }

    public static void main(String[] args) {
        DBSCANAlorithm dbscanAlorithm = new DBSCANAlorithm();
        dbscanAlorithm.getStockClusterBean();
        //验证MinPts要小于stockClusterBeanList.size-1;
        if (!(MinPts < stockClusterBeanList.size() - 1)) {
            System.out.println("MinPts取值不符合规范，重新取值");
            System.exit(0);
        }
        dbscanAlorithm.setRadius();
        FileOperateUtil.myLogger("radius 取值:" + radius + new Date());

        dbscanAlorithm.DBSCANCluster();
    }
}
