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
        /**
         * ("login_status","0");
         ("player_status", "用户不存在");
         ("login_status","1");
         ("player_status", "成功登陆");
         ("login_status","2");
         ("player_status", "密码不正确");

         */
        URLs.put("/sign","UrlPlayer#adminsignControllor");//made                                                   参数：admin_name,admin_password
        /**
         * ("sign_status"，"成功注册");
         * ("sign_status", "用户已存在");
         */
        URLs.put("/updatacash","UrlPlayer#updataCash");//made                                                        参数：admin_id,admin_cash
        URLs.put("/updatadebt","UrlPlayer#updataDebt");//made                                                         参数：admin_id , player_debt  返回： player_debt ,player_cash query:现金不够 或者 还债成功
        URLs.put("/updatabank","UrlPlayer#updataBank");//made                                                        参数：admin_id ,player_bank  返回：player_bank ，,player_cash query：现金不够,存钱失败 或者 存款不足，取钱失败 或者 操作成功
        URLs.put("/updatahealth","UrlPlayer#updataHealth");//made                                                   参数：admin_id,player_health  返回：player_health ,player_cash query:操作失败，现金不足 或者 治疗成功


        URLs.put("/updatareputation","UrlPlayer#updataReputation");//made                                            参数：admin_id,player_reputation
        URLs.put("/updatagoods_contain","UrlPlayer#updataGoods_contain");//made                                   参数：admin_id,goods_contain
        URLs.put("/updataremain_days","UrlPlayer#updataRemain_days");//made                                        参数：admin_id 返回query 过了一天 或者 404
        URLs.put("/updataremain_tradetime","UrlPlayer#updataTradetime");//made                                       参数：admin_id 返回query 交易成功 或者 交易次数达到上限

        URLs.put("/getplayer","UrlPlayer#getPlayer");//made//                                                参数：admin_id 返回一个player整个类

        URLs.put("/updatagoods_max","UrlPlayer#updataGoods_max");

        //Goods_houseUrl
        URLs.put("/getgoods_house","UrlGoods#getBackPackControllor");//made                                         参数：admin_id
        URLs.put("/buygoods_house","UrlGoods#updataBackPackControllor");//made                                  参数：admin_id , goods_id,    goods_price,    goods_amount 返回背包数组
        URLs.put("/salegoods_house","UrlGoods#saleBackPackControllor");//made                                      参数：admin_id, goods_id,    goods_price,    goods_amount 返回背包数组


        //PlaceUrl
        URLs.put("/getmarket_goods","UrlPlace#getMarket_goods");//made                                                 参数：admin_id news_id 返回 物品数组

        //
        // news
        URLs.put("/getnews","UrlNews#getNews");//made            参数：admin_id 返回 news_id
        //1.获取news_id 2.获取player(使用/getplayer)  3获取place物品（/getmarket_goods）4.获取背包（/getgoods_house）
        //Rank
        URLs.put("/getRanking","UrlRanking#getRanking");









    }
}
