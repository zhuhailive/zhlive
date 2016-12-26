package urlservice;

import java.net.URLStreamHandler;
import java.util.HashMap;
import java.util.Map;

/**所有url的映射
 *
 * Created by Admini on 2016/12/19.
 */
public class URLMap {

     public static Map URLs = new HashMap();
    static {

        //PlayerUrl
        URLs.put("/login","UrlPlayer#adminLoginControllor");//made                                                参数：admin_name,admin_password  返回：
        URLs.put("/sign","UrlPlayer#adminsignControllor");//made                                                   参数：admin_name,admin_password
        URLs.put("/updatacash","UrlPlayer#updataCash");//made                                                        参数：admin_id,admin_cash
        URLs.put("/updatadebt","UrlPlayer#updataDebt");//made                                                         参数：admin_id,admin_debt
        URLs.put("/updatabank","UrlPlayer#updataBank");//made                                                        参数：admin_id,admin_bank
        URLs.put("/updatahealth","UrlPlayer#updataHealth");//made                                                   参数：admin_id,admin_health
        URLs.put("/updatareputation","UrlPlayer#updataReputation");//made                                            参数：admin_id,admin_reputation
        URLs.put("/updatagoods_contain","UrlPlayer#updataGoods_contain");//made                                   参数：admin_id,goods_contain
        URLs.put("/updataremain_days","UrlPlayer#updataRemain_days");//made                                        参数：admin_id
        URLs.put("/updataremain_tradetime","UrlPlayer#updataTradetime");//made                                       参数：admin_id

        URLs.put("/getplayer","UrlPlayer#getPlayer");//made                                                              参数：admin_id

        //Goods_houseUrl
        URLs.put("/getgoods_house","UrlGoods#getBackPackControllor");//made                                         参数：admin_id
        URLs.put("/buygoods_house","UrlGoods#updataBackPackControllor");//made                                  参数：admin_id , goods_id,    goods_price,    goods_amount
        URLs.put("/salegoods_house","UrlGoods#saleBackPackControllor");//made                                      参数：admin_id, goods_id,    goods_price,    goods_amount


        //PlaceUrl
        URLs.put("/getmarket_goods","UrlPlace#getMarket_goods");//made                                                 参数：admin_id

        //
        // news
        URLs.put("/getnews","UrlNews#getNews");//made                                                                       参数：admin_id

        //Rank
        URLs.put("/getRanking","UrlRanking#getRanking");









    }
}
