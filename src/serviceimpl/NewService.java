package serviceimpl;

import entity.Goods;
import entity.News;
import severside.ServerBuffer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by keben on 2016/12/23.
 */
public class NewService extends ServiceBase{

    private static NewService ghs = null;

    public static NewService getNewService(){

        if(ghs==null){
            ghs = new NewService();
        }
        return ghs;
    }
    public void getNews(){

        //获取链接
        Connection conn = getConnection();

        //mysql语句对象
        PreparedStatement findUS = null;


        //编译语句
        try {
            findUS = conn.prepareStatement("SELECT * FROM news");


            ResultSet rs = findUS.executeQuery();


            while (rs.next()){

                News news = new News();
                newsDataSet(news,rs);
                ServerBuffer.hmnews.put(news.getNews_id(),news);



            }
            System.out.println(ServerBuffer.hmnews.get(1).getNews_content()+"----------"+ServerBuffer.hmnews.get(36).getNews_content());
            findUS.close();
            rs.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void newsDataSet(News news,ResultSet rs) throws SQLException {
        news.setNews_id(rs.getInt("news_id"));
        news.setNews_goods_id(rs.getInt("news_goods_id"));
        news.setNews_content(rs.getString("news_content"));
        news.setEffect_goods_multiple(rs.getInt("effect_goods_multiple"));
        news.setEffect_health_point(rs.getInt("effect_health_point"));
        news.setEffect_cash_point(rs.getInt("effect_cash_point"));
    }


}
