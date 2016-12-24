package test.connectionpool;

import connectionpool.ConnFactory;
import connectionpool.ConnPoolConfig;
import org.apache.commons.pool2.impl.GenericObjectPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admini on 2016/12/13.
 */
public class test {
    static Integer i=0;
    public static void main(String[] args) throws InterruptedException {

        final GenericObjectPool<Connection> genericObjectPool  = new GenericObjectPool(new ConnFactory(),new ConnPoolConfig());
        List<Thread> list = new ArrayList<>();
        for ( i = 0;i<40;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Connection conn = genericObjectPool.borrowObject();
                        System.out.println("####借用成功####\n"+conn.toString());

                        PreparedStatement st = null;
                        ResultSet rs = null;
                        try {

                            String sql = "select * from place"; // 这里用问号
                            st = conn.prepareStatement(sql);
                            rs = st.executeQuery();
                            while (rs.next()) {
                                System.out.println(rs.getInt(1) + "\t" + rs.getString(2) +"\t"+ rs.getInt(3));
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        } finally {
                            st.close();
                            rs.close();
                        }

                        genericObjectPool.returnObject(conn);
                        System.out.println("####归还成功####\n" + conn.toString());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }
        try {
            Thread.sleep(5000);

            for (int i =0;i<10000000;i++){}
            Thread.sleep(5000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
