package connectionpool;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import java.sql.Connection;
import java.sql.DriverManager;




/**数据库连接池的数据库连接参数
 * Created by Admini on 2016/12/13.
 */
public class ConnFactory extends BasePooledObjectFactory<Connection> {

    @Override
    public Connection create() throws Exception {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("驱动加载出错!");
            e.printStackTrace();
        }
//        return DriverManager.getConnection(url,username,password);

        //加上中文编码
        return DriverManager.getConnection("jdbc:mysql://115.159.59.72/zhuhailive?"
                + "user=manage&password=abc012300&useUnicode=true&characterEncoding=UTF8");
    }

    @Override
    public PooledObject<Connection> wrap(Connection mysqlConnect) {
        return new DefaultPooledObject<Connection>(mysqlConnect);
    }


}
