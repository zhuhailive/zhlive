package serviceimpl;

import entity.Admin;
import entity.News;
import entity.Player;
import severside.ServerBuffer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by keben on 2016/12/18.
 */
public class PlayerService extends ServiceBase {

    private static PlayerService us = null;

    /**
     * 单例的服务获取方法，
     * @return UserServive对象
     */

    public static PlayerService getPlayerService(){
        if(us == null){
            us = new PlayerService();
        }
        return us;
    }


    /**
     * 按id查找User
     * @param id User表的id
     * @return User对象
     */

    public Player findById(int id){

        //获取链接
        Connection conn = getConnection();

        //mysql语句对象
        PreparedStatement findUS = null;

        if(id == 0){
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            return null;
        }

        try {

            //编译语句
            findUS = conn.prepareStatement("SELECT * FROM player WHERE admin_id=?");

            //对sql变量赋值
            findUS.setInt(1,id);

            //没有结果
            ResultSet rs = findUS.executeQuery();
            if(!rs.next()){
                return null;

            }

            //新建实体结果
            Player player = new Player();

            //使用结果集填充实体结果
            userDataSet(player,rs);


            //关闭结果集和连接
            findUS.close();
            rs.close();


            //返回结果
            return player;

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            returnConnection(conn);
        }
        return null;
    }


    /**
     * 新注册用户
     * @param admin
     */
    public void addPlayer(Admin admin) {


        //获取链接
        Connection conn = getConnection();

        //mysql语句对象
        PreparedStatement findUS = null;


        try {

            //编译语句
            findUS = conn.prepareStatement("INSERT INTO player(admin_id,admin_name) VALUES (?,?)");

            //对sql变量赋值
            findUS.setInt(1,admin.getId() );
            findUS.setString(2,admin.getAdmin_name());

            //没有结果
           findUS.execute();

            //关闭结果集和连接
            findUS.close();



        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            returnConnection(conn);
        }
    }

    /**
     * 删除用户
     * @param admin
     */
    public void delectplayer(Admin admin){

        //获取链接
        Connection conn = getConnection();

        //mysql语句对象
        PreparedStatement findUS = null;

        try {

            //编译语句
            findUS = conn.prepareStatement("DELETE FROM player WHERE id=?");

            //对sql变量赋值
            findUS.setInt(1,admin.getId());



            //没有结果
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

    public void updateplayer(Player player){


        //获取链接
        Connection conn = getConnection();

        //mysql语句对象
        PreparedStatement findUS = null;


        try {

            //编译语句
            findUS = conn.prepareStatement("UPDATE player SET player_cash=?, player_debt=?, player_bank=?, player_heralth=?, player_reputation=?, goods_contain=?, remain_days=?, ramain_tradetime=? ,admin_name=? WHERE admin_id=?");
//            findUS = conn.prepareStatement("DELETE FROM player WHERE id=?");
            //对sql变量赋值
            findUS.setInt(1,player.getPlayer_cash());
            findUS.setInt(2,player.getPlayer_debt());
            findUS.setInt(3,player.getPlayer_bank());
            findUS.setInt(4,player.getPlayer_health());
            findUS.setInt(5,player.getPlayer_reputation());
            findUS.setInt(7,player.getRemain_days());
            findUS.setInt(6,player.getGoods_contain());
            findUS.setInt(8,player.getRemain_tradetime());
            findUS.setString(9,player.getAdmin_name());
            findUS.setInt(10,player.getAdmin_id());


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

    public void getPlayerBuffer(){
        //获取链接
        Connection conn = getConnection();

        //mysql语句对象
        PreparedStatement findUS = null;


        //编译语句
        try {
            findUS = conn.prepareStatement("SELECT * FROM player");


            ResultSet rs = findUS.executeQuery();


            while (rs.next()) {
                Player player= new Player();

                userDataSet(player, rs);
                ServerBuffer.hmplayer.put(player.getAdmin_id(), player);
//                System.out.println(player.getAdmin_name());

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


    /**
     * 通过结果集将信息映射到实体
     * @param player 需要填充的实体对象
     * @param rs 结果集
     * @throws SQLException
     */
    private void userDataSet(Player player, ResultSet rs) throws SQLException{
        player.setId(rs.getInt("id"));
        player.setAdmin_id(rs.getInt("admin_id"));
        player.setPlayer_bank(rs.getInt("player_bank"));
        player.setRemain_days(rs.getInt("remain_days"));
        player.setPlayer_cash(rs.getInt("player_cash"));
        player.setPlayer_health(rs.getInt("player_health"));
        player.setPlayer_debt(rs.getInt("player_debt"));
        player.setGoods_contain(rs.getInt("goods_contain"));
        player.setPlayer_reputation(rs.getInt("player_reputation"));
        player.setAdmin_name(rs.getString("admin_name"));
    }

}
