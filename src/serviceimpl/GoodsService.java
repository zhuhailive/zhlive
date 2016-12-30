package serviceimpl;

import entity.Goods;
import entity.Goods_house;
import entity.Player;
import severside.ServerBuffer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**连接数据库进行操作
 * Created by keben on 2016/12/23.
 */
public class GoodsService extends ServiceBase{


    private static GoodsService ghs = null;

    public static GoodsService getGoods_houseService(){
        if(ghs==null){
            ghs = new GoodsService();
        }
        return ghs;
    }

    public void getGoods(){

        //获取链接
        Connection conn = getConnection();

        //mysql语句对象
        PreparedStatement findUS = null;


        //编译语句
        try {
            findUS = conn.prepareStatement("SELECT * FROM goods");


            ResultSet rs = findUS.executeQuery();


            while (rs.next()){

                Goods goods = new Goods();
                goodsDataSet(goods,rs);
                ServerBuffer.hmpgoods.put(rs.getInt("goods_id"),goods);


            }
            System.out.println(ServerBuffer.hmpgoods.get(1).getGoods_name()+"----------"+ServerBuffer.hmpgoods.get(3).getGoods_name());
            findUS.close();
            rs.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }



    private void goodsDataSet(Goods goods,ResultSet rs) throws SQLException {
        goods.setGoods_id(rs.getInt("goods_id"));
        goods.setGoods_name(rs.getString("goods_name"));
        goods.setGoods_sum(rs.getInt("goods_sum"));
        goods.setMultiple(rs.getInt("multiple"));
        goods.setIsnews(rs.getInt("isnews"));
        goods.setOriginal_price(rs.getInt("original_price"));
    }

}
