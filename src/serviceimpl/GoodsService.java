package serviceimpl;

import entity.Goods;
import entity.Goods_house;
import entity.Player;
import severside.ServerBuffer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
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

    public void updataGoods(Goods_house goods_house){

        //获取链接
        Connection conn = getConnection();

        //mysql语句对象
        PreparedStatement findUS = null;


        try {

            //编译语句
            findUS = conn.prepareStatement("UPDATE goods_house SET goods_contains=?, goods_item=?, buyin_price=?, goods_amount=? WHERE admin_id=?");
//            findUS = conn.prepareStatement("DELETE FROM player WHERE id=?");
//            对sql变量赋值
            findUS.setInt(1,goods_house.getGoods_contains());
            findUS.setString(2,goods_house.getGoods_item());
            findUS.setString(3,goods_house.getBuyin_price());
            findUS.setString(4,goods_house.getGoods_amount());
            findUS.setInt(5,goods_house.getAdmin_id());


            ResultSet rs = findUS.executeQuery();

            //关闭结果集和连接
            findUS.close();
            rs.close();


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            returnConnection(conn);
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
