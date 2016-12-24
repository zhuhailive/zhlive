package serviceimpl; /**
 * Created by keben on 2016/12/18.
 */
import connectionpool.ConnFactory;
import connectionpool.ConnPoolConfig;
import org.apache.commons.pool2.impl.GenericObjectPool;

import java.sql.Connection;

/**
 * Created by D on 2016/12/12.
 */
public abstract class ServiceBase {
    final static GenericObjectPool<Connection> genericObjectPool = new GenericObjectPool(new ConnFactory(),new ConnPoolConfig());
    public Connection getConnection(){
        try {
            return genericObjectPool.borrowObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public void returnConnection(Connection conn){
        genericObjectPool.returnObject(conn);
    }
}
