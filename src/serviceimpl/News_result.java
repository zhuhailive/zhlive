package serviceimpl;

import entity.Goods;
import entity.News;
import severside.ServerBuffer;

/**新闻事件的结果，物品升降价格，被人偷钱，健康值下降等
 * Created by keben on 2016/12/24.
 */
public class News_result {

    private static News_result ad = null;


    /**
     * 单例
     * @return AdminService
     */
    public static News_result getNews_result(){
        if(ad==null){
            ad = new News_result();
        }
        return ad;
    }

    public void setGoodsEffect(int news_id){

        News news = ServerBuffer.hmnews.get(news_id);
        ServerBuffer.hmpgoods.get(news.getNews_goods_id()).setIsnews(news.getEffect_goods_multiple());

    }

    public void setHealthEffect(int news_id,int admin_id){

        News news = ServerBuffer.hmnews.get(news_id);

    }

    public void setCashEffect(int news_id,int admin_id){
        News news = ServerBuffer.hmnews.get(news_id);

    }

}
