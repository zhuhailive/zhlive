package serviceimpl;

import entity.Goods;
import entity.News;
import entity.Player;
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
        Player player = ServerBuffer.hmplayer.get(admin_id);

        int current_health = player.getPlayer_health() + news.getEffect_health_point();
        ServerBuffer.hmplayer.get(admin_id).setPlayer_health(current_health);

    }

    public void setCashEffect(int news_id,int admin_id){

        News news = ServerBuffer.hmnews.get(news_id);
        Player player = ServerBuffer.hmplayer.get(admin_id);
        int current_cash = (int)(player.getPlayer_cash() - player.getPlayer_cash()*(news.getEffect_cash_point()*0.01));
        ServerBuffer.hmplayer.get(admin_id).setPlayer_cash(current_cash);

    }

}
