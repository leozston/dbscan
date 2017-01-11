package algorithm;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by leoz on 2017/1/11.
 */
public class BaseBean {
    private static SimpleDateFormat sdf  =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );

    private String carNumber;
    private Date carDate;
    private String longitude;
    private String latitude;

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public Date getCarDate() {
        return carDate;
    }

    public void setCarDate(Date carDate) {
        this.carDate = carDate;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
//        return "BaseBean{" +
//                "carNumber='" + carNumber + '\'' +
//                ", carDate=" + carDate +
//                ", longitude=" + longitude +
//                ", latitude=" + latitude +
//                '}';
        return carNumber + "," + sdf.format(carDate) + "," + longitude + "," + latitude;
    }
}
