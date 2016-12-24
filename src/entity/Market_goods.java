package entity;

/**
 * Created by keben on 2016/12/22.
 */
public class Market_goods {
    private int goods_id;
    private int goods_price;
    private int current_price;
    private int is_news;
    private String goods_name;

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public int getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(int goods_price) {
        this.goods_price = goods_price;
    }

    public int getCurrent_price() {
        return current_price;
    }

    public void setCurrent_price(int current_price) {
        this.current_price = current_price;
    }

    public int getIs_news() {
        return is_news;
    }

    public void setIs_news(int is_news) {
        this.is_news = is_news;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }
}
