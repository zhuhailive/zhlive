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
        URLs.put("/login","UrlPlayer#adminLoginControllor");//made
        URLs.put("/sign","UrlPlayer#adminsignControllor");//made
        URLs.put("/updatacash","UrlPlayer#updataCash");
        URLs.put("/updatadebt","UrlPlayer#updataDebt");//made
        URLs.put("/updatabank","UrlPlayer#updataBank");
        URLs.put("/updatahealth","UrlPlayer#updataHealth");
        URLs.put("/updatareputation","UrlPlayer#updataReputation");
        URLs.put("/updatagoods_contain","UrlPlayer#updataGoods_contain");
        URLs.put("/updataremain_days","UrlPlayer#updataRemain_days");
        URLs.put("/updataremain_tradetime","UrlPlayer#updataTradetime");

        //Goods_houseUrl
        URLs.put("/getgoods_house","UrlGoods#getBackPackControllor");//made
        URLs.put("/buygoods_house","UrlGoods#updataBackPackControllor");//made
        URLs.put("/salegoods_house","UrlGoods#saleBackPackControllor");//made


        //PlaceUrl
        URLs.put("/getmarket_goods","UrlPlace#getMarket_goods");//made

        //news
        URLs.put("/getnews","UrlNews#getNews");//made

        //









    }
}
