package algorithm;

/**
 * Created by leoz on 2016/11/25.
 */
public class BaseBean {
    private String date;
    private float change;   // 这里暂时仅考虑股票涨幅，后期可以将股票的其他属性写进来作为计算股票相似度的依据

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getChange() {
        return change;
    }

    public void setChange(float change) {
        this.change = change;
    }

    @Override
    public String toString() {
        return  "date='" + date + '\'' +
                ", change=" + change;
    }
}
