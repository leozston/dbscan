package src.algorithm;

/**
 * Created by leoz on 2016/11/22.
 * function:用于计算股票与股票之间相似度Bean
 */
public class StockSimilarPropertyBean {
    private String code;
    private String date;
    private float change;   // 这里暂时仅考虑股票涨幅，后期可以将股票的其他属性写进来作为计算股票相似度的依据

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

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
}
