package algorithm;

/**
 * Created by leoz on 2016/12/10.
 */
public class TaxiClusterBean {
    private float longitude;
    private float latitude;

    private boolean isKey;
    private boolean isClassed;
    private boolean isVisited;

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
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

    @Override
    public String toString() {
        return "[" + this.longitude + "," + this.latitude + "]";
    }
}
