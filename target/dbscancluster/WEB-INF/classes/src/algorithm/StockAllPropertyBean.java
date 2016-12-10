package src.algorithm;

/**
 * Created by leoz on 2016/11/22.
 * function：封装股票全部属性Bean
 */
public class StockAllPropertyBean {
    private String code;
    private String date;
    private float open;
    private float high;
    private float low;
    private float close;
    private float change;
    private long volume;
    private long money;
    private long traded_market_value;
    private long market_value;
    private float turnover;
    private float adjust_price;
    private String report_type;
    private String report_date;
    private float proPE_TTM;
    private float proPS_TTM;
    private float proPC_TTM;
    private float proPB;

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

    public float getOpen() {
        return open;
    }

    public void setOpen(float open) {
        this.open = open;
    }

    public float getHigh() {
        return high;
    }

    public void setHigh(float high) {
        this.high = high;
    }

    public float getLow() {
        return low;
    }

    public void setLow(float low) {
        this.low = low;
    }

    public float getClose() {
        return close;
    }

    public void setClose(float close) {
        this.close = close;
    }

    public float getChange() {
        return change;
    }

    public void setChange(float change) {
        this.change = change;
    }

    public long getVolume() {
        return volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    public long getTraded_market_value() {
        return traded_market_value;
    }

    public void setTraded_market_value(long traded_market_value) {
        this.traded_market_value = traded_market_value;
    }

    public long getMarket_value() {
        return market_value;
    }

    public void setMarket_value(long market_value) {
        this.market_value = market_value;
    }

    public float getTurnover() {
        return turnover;
    }

    public void setTurnover(float turnover) {
        this.turnover = turnover;
    }

    public float getAdjust_price() {
        return adjust_price;
    }

    public void setAdjust_price(float adjust_price) {
        this.adjust_price = adjust_price;
    }

    public String getReport_type() {
        return report_type;
    }

    public void setReport_type(String report_type) {
        this.report_type = report_type;
    }

    public String getReport_date() {
        return report_date;
    }

    public void setReport_date(String report_date) {
        this.report_date = report_date;
    }

    public float getProPE_TTM() {
        return proPE_TTM;
    }

    public void setProPE_TTM(float proPE_TTM) {
        this.proPE_TTM = proPE_TTM;
    }

    public float getProPS_TTM() {
        return proPS_TTM;
    }

    public void setProPS_TTM(float proPS_TTM) {
        this.proPS_TTM = proPS_TTM;
    }

    public float getProPC_TTM() {
        return proPC_TTM;
    }

    public void setProPC_TTM(float proPC_TTM) {
        this.proPC_TTM = proPC_TTM;
    }

    public float getProPB() {
        return proPB;
    }

    public void setProPB(float proPB) {
        this.proPB = proPB;
    }
}
