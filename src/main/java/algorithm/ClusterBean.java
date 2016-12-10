package algorithm;

import java.util.List;

/**
 * Created by leoz on 2016/11/25.
 */
public class ClusterBean {
    private String name;
    private List<BaseBean> weight;

    private boolean isKey;
    private boolean isClassed;
    private boolean isVisited;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BaseBean> getWeight() {
        return weight;
    }

    public void setWeight(List<BaseBean> weight) {
        this.weight = weight;
    }

    public boolean isKey() {
        return isKey;
    }

    public void setKey(boolean key) {
        isKey = key;
    }

    public boolean isClassed() {
        return isClassed;
    }

    public void setClassed(boolean classed) {
        isClassed = classed;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }
}
