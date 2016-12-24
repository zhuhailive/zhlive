package entity;

/**
 * Created by keben on 2016/12/21.
 */
public class Goods_house {

    private int admin_id;
    private String goods_contains;
    private String goods_item;
    private String buyin_price;
    private String goods_amount;
    private String[][][] goods_stringarray = new String[100][4][2];
    private int goods_max;

    public int getGoods_max() {
        return goods_max;
    }

    public void setGoods_max(int goods_max) {
        this.goods_max = goods_max;
    }

    public String[][][] getGoods_stringarray() {
        return goods_stringarray;
    }

    public void setGoods_stringarray(String[][][] goods_string) {
        this.goods_stringarray = goods_string;
    }

    public int getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(int admin_id) {
        this.admin_id = admin_id;
    }

    public String getGoods_contains() {
        return goods_contains;
    }

    public void setGoods_contains(String goods_contains) {
        this.goods_contains = goods_contains;
    }

    public String getGoods_item() {
        return goods_item;
    }

    public void setGoods_item(String goods_item) {
        this.goods_item = goods_item;
    }

    public String getBuyin_price() {
        return buyin_price;
    }

    public void setBuyin_price(String buyin_price) {
        this.buyin_price = buyin_price;
    }

    public String getGoods_amount() {
        return goods_amount;
    }

    public void setGoods_amount(String goods_amount) {
        this.goods_amount = goods_amount;
    }
}
