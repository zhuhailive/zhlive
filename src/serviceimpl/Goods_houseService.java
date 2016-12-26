package serviceimpl;

import entity.Admin;
import entity.Goods_house;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by keben on 2016/12/21.
 */
public class Goods_houseService extends ServiceBase {

    private static Goods_houseService ghs = null;

    public static Goods_houseService getGoods_houseService(){
        if(ghs==null){
            ghs = new Goods_houseService();
        }
        return ghs;
    }


    /**
     * 取出goods
     * @param id
     * @return
     */
    public Goods_house findByAdminId(int id){

        //获取链接
        Connection conn = getConnection();

        //mysql语句对象
        PreparedStatement findUS = null;

        //编译语句
        try {
            findUS = conn.prepareStatement("SELECT * FROM goods_house WHERE admin_id=?");


            //对sql变量赋值
            findUS.setInt(1,id);

            //结果
            ResultSet rs = findUS.executeQuery();

            if(!rs.next()){
                return null;

            }
            Goods_house goods_house = new Goods_house();


            goods_house.setAdmin_id(rs.getInt("admin_id"));
            goods_house.setGoods_contains(rs.getInt("goods_contains"));
            goods_house.setGoods_item(rs.getString("goods_item"));
            goods_house.setBuyin_price(rs.getString("buyin_price"));
            goods_house.setGoods_amount(rs.getString("goods_amount"));
            goods_house.setGoods_max(rs.getInt("goods_max"));

            findUS.close();
            rs.close();

            return goods_house;




        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }


    }

    /**
     * 存goods
     * @param gh
     */
    public void upDataGoods_house(Goods_house gh){


    }

    public void addGoods_house(Admin admin){


        //获取链接
        Connection conn = getConnection();

        //mysql语句对象
        PreparedStatement findUS = null;

        try {

            findUS = conn.prepareStatement("INSERT INTO goods_house(admin_id) VALUES (?)");

            findUS.setInt(1,admin.getId());

            findUS.execute();

            findUS.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
