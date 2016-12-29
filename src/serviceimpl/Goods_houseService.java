package serviceimpl;

import entity.Admin;
import entity.Goods_house;
import entity.Player;
import severside.ServerBuffer;

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
     * @param
     */
    public void updataGoods_house(Goods_house goods_house){

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


            findUS.execute();

            //关闭结果集和连接
            findUS.close();



        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            returnConnection(conn);
        }
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


    public void getGoods_houseBuffer(){
        //获取链接
        Connection conn = getConnection();

        //mysql语句对象
        PreparedStatement findUS = null;


        //编译语句
        try {
            findUS = conn.prepareStatement("SELECT * FROM goods_house");


            ResultSet rs = findUS.executeQuery();


            while (rs.next()) {
                Goods_house goods_house = new Goods_house();
                goods_house.setAdmin_id(rs.getInt("admin_id"));
                goods_house.setGoods_contains(rs.getInt("goods_contains"));
                goods_house.setGoods_item(rs.getString("goods_item"));
                goods_house.setBuyin_price(rs.getString("buyin_price"));
                goods_house.setGoods_amount(rs.getString("goods_amount"));
                goods_house.setGoods_max(rs.getInt("goods_max"));

//                System.out.println(player.getAdmin_name());
                //切割进一个二维数组
                String s1[] = goods_house.getGoods_item().split(",");
                String s2[] = goods_house.getBuyin_price().split(",");
                String s3[] = goods_house.getGoods_amount().split(",");



                String opstr[][][] = new String[goods_house.getGoods_contains()][4][2];



                for (int i = 0 ; i < goods_house.getGoods_contains() ; i++ ){

                    opstr[i][0][0] = "goods_id";
                    opstr[i][1][0] = "goods_price";
                    opstr[i][2][0] = "goods_amount";
                    opstr[i][3][0] = "goods_name";

                    opstr[i][0][1] = s1[i];
                    opstr[i][1][1] = s2[i];
                    opstr[i][2][1] = s3[i];
                    opstr[i][3][1] = ServerBuffer.hmpgoods.get(Integer.parseInt(s1[i])).getGoods_name();
                }
                //切割好的数组存进缓存
                goods_house.setGoods_stringarray(opstr);
                ServerBuffer.hmpgoods_house.put(goods_house.getAdmin_id(), goods_house);
            }
//            System.out.println(ServerBuffer.hmplayer.size());
//            for(int i = 1 ; i <= ServerBuffer.hmplayer.size() ; i++ ){
//                System.out.println(ServerBuffer.hmplayer.get(i).getAdmin_name());
//            }

            findUS.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
