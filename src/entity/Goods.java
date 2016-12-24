package entity;

/**
 * Created by keben on 2016/12/23.
 */
public class Goods {

    public int goods_id;
    public String goods_name;
    public int original_price;
    public int multiple;
    public int isnews;
    public int goods_sum;
    public boolean sold;

    public int getIsnews() {
        return isnews;
    }

    public boolean isSold() {
        return sold;
    }

    public void setSold(boolean sold) {
        this.sold = sold;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public int getOriginal_price() {
        return original_price;
    }

    public void setOriginal_price(int original_price) {
        this.original_price = original_price;
    }

    public int getMultiple() {
        return multiple;
    }

    public void setMultiple(int multiple) {
        this.multiple = multiple;
    }

    public int isnews() {
        return isnews;
    }

    public void setIsnews(int isnews) {
        this.isnews = isnews;
    }

    public int getGoods_sum() {
        return goods_sum;
    }

    public void setGoods_sum(int goods_sum) {
        this.goods_sum = goods_sum;
    }
}
