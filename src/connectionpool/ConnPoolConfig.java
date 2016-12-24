package connectionpool;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
         lifo ：对象池存储空闲对象是使用的LinkedBlockingDeque，它本质上是一个支持FIFO和FILO的双向的队列，common-pool2中的LinkedBlockingDeque不是Java原生的队列，而有common-pool2重新写的一个双向队列。如果为false，表示使用FIFO获取对象。默认值是false.建议使用默认值。

         fairness：common-pool2实现的LinkedBlockingDeque双向阻塞队列使用的是Lock锁。这个参数就是表示在实例化一个LinkedBlockingDeque时，是否使用lock的公平锁。默认值是false，建议使用默认值。

         maxWaitMillis：当没有空闲连接时，获取一个对象的最大等待时间。如果这个值小于0，则永不超时，一直等待，直到有空闲对象到来。如果大于0，则等待maxWaitMillis长时间，如果没有空闲对象，将抛出NoSuchElementException异常。默认值是-1；可以根据需要自己调整，单位是毫秒。

         minEvictableIdleTimeMillis：对象最小的空闲时间。如果为小于等于0，最Long的最大值，如果大于0，当空闲的时间大于这个值时，执行移除这个对象操作。默认值是1000L * 60L * 30L;即30分钟。这个参数是强制性的，只要空闲时间超过这个值，就会移除。

         softMinEvictableIdleTimeMillis：对象最小的空间时间，如果小于等于0，取Long的最大值，如果大于0，当对象的空闲时间超过这个值，并且当前空闲对象的数量大于最小空闲数量(minIdle)时，执行移除操作。这个和上面的minEvictableIdleTimeMillis的区别是，它会保留最小的空闲对象数量。而上面的不会，是强制性移除的。默认值是-1；

         numTestsPerEvictionRun：检测空闲对象线程每次检测的空闲对象的数量。默认值是3；如果这个值小于0,则每次检测的空闲对象数量等于当前空闲对象数量除以这个值的绝对值，并对结果向上取整。

         testOnCreate：在创建对象时检测对象是否有效，false是，默认值是false。

         testOnBorrow：在从对象池获取对象时是否检测对象有效，false是；默认值是false。

         testOnReturn：在向对象池中归还对象时是否检测对象有效，false是，默认值是false。

         testWhileIdle：在检测空闲对象线程检测到对象不需要移除时，是否检测对象的有效性。false是，默认值是false。

         timeBetweenEvictionRunsMillis：空闲对象检测线程的执行周期，即多长时候执行一次空闲对象检测。单位是毫秒数。如果小于等于0，则不执行检测线程。默认值是-1;

         blockWhenExhausted：当对象池没有空闲对象时，新的获取对象的请求是否阻塞。false阻塞。默认值是false;

         maxTotal：对象池中管理的最多对象个数。默认值是8。

         maxIdle：对象池中最大的空闲对象个数。默认值是8。

         minIdle：对象池中最小的空闲对象个数。默认值是0。
 */
public class ConnPoolConfig extends GenericObjectPoolConfig {
    public ConnPoolConfig() {
        // defaults to make your life with connection pool easier :)
        setMinIdle(5);
        setMaxIdle(120);
        setMaxTotal(140);
        setTestOnBorrow(false);
        setBlockWhenExhausted(false);
        setTestOnCreate(false);
        setTestOnBorrow(false);
        setTestOnReturn(false);
        setTestWhileIdle(false);
    }
}
