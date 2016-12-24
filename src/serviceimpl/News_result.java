package serviceimpl;

/**新闻事件的结果，物品升降价格，被人偷钱，健康值下降等
 * Created by keben on 2016/12/24.
 */
public class News_result {

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

}
