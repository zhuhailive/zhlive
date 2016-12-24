package entity;

/**
 * Created by keben on 2016/12/23.
 */
public class News {

    private int news_id;
    private int news_goods_id;
    private String news_content;
    private int effect_goods_multiple;
    private int effect_health_point;
    private int effect_cash_point;

    public int getEffect_goods_multiple() {
        return effect_goods_multiple;
    }

    public void setEffect_goods_multiple(int effect_goods_multiple) {
        this.effect_goods_multiple = effect_goods_multiple;
    }

    public int getEffect_health_point() {
        return effect_health_point;
    }

    public void setEffect_health_point(int effect_health_point) {
        this.effect_health_point = effect_health_point;
    }

    public int getEffect_cash_point() {
        return effect_cash_point;
    }

    public void setEffect_cash_point(int effect_cash_point) {
        this.effect_cash_point = effect_cash_point;
    }

    public int getNews_id() {
        return news_id;
    }

    public void setNews_id(int news_id) {
        this.news_id = news_id;
    }

    public int getNews_goods_id() {
        return news_goods_id;
    }

    public void setNews_goods_id(int news_goods_id) {
        this.news_goods_id = news_goods_id;
    }

    public String getNews_content() {
        return news_content;
    }

    public void setNews_content(String news_content) {
        this.news_content = news_content;
    }
}
