package serviceimpl;

import com.alibaba.fastjson.support.odps.udf.CodecCheck;
import entity.Admin;
import entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by keben on 2016/12/20.
 */
public class AdminService extends ServiceBase{

    private static AdminService ad = null;


    /**
     * 单例
     * @return AdminService
     */
    public static AdminService getAdminService(){
        if(ad==null){
            ad = new AdminService();
        }
            return ad;
    }

    /**
     * 注册用户
     * @param admin
     */
    public void addAdmin(Admin admin){
        //获取链接
        Connection conn = getConnection();

        //mysql语句对象
        PreparedStatement findUS = null;


        try {

            //编译语句
            findUS = conn.prepareStatement("INSERT INTO admin(admin_name,admin_password) VALUES (?,?)");

            //对sql变量赋值
            findUS.setString(1, admin.getAdmin_name());
            findUS.setString(2, admin.getAdmin_password());

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
     * 查询account，缓存admin表，
     * @param account
     */
    public Admin findByAdminAccount(String account){


        Admin admin;
        //获取链接
        Connection conn = getConnection();

        //mysql语句对象
        PreparedStatement findUS = null;

        try {

            //编译语句
            findUS = conn.prepareStatement("SELECT * FROM admin WHERE admin_name = ?");

            //对sql变量赋值
            System.out.println("findByAdminAccount"+account);
            findUS.setString(1,account);

            //没有结果
            ResultSet rs = findUS.executeQuery();

            if(!rs.next()){
                admin = null;
            }

            else {
                //新建实体结果
                admin = new Admin();
                admin.setId(rs.getInt("id"));
                admin.setAdmin_name(rs.getString("admin_name"));
                admin.setAdmin_password(rs.getString("admin_password"));
            }
            //关闭结果集和连接
            findUS.close();
            rs.close();

            //返回结果
            return admin;

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            returnConnection(conn);
        }
        return null;
    }

    public void delect(String account){

    }



}
