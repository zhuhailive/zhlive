package severside;

import entity.*;
import serviceimpl.*;

import java.util.HashMap;
import java.util.Timer;

/**
 * Created by keben on 2016/12/20.
 */
public class ServerBuffer {
    //缓存player，需要更新
    public static HashMap<Integer, Player> hmplayer = new HashMap(); //made
    //缓存物品
    public static HashMap<Integer, Goods> hmpgoods = new HashMap();  //made
    //缓存背包，需要更新
    public static HashMap<Integer, Goods_house> hmpgoods_house = new HashMap(); //made
    //缓存place
    public static HashMap<Integer, Place> hmplace = new HashMap<>(); //made
    //缓存news
    public static HashMap<Integer, News> hmnews = new HashMap<>(); //made




    public ServerBuffer(){


        //将资料导入缓存区
        GoodsService.getGoods_houseService().getGoods();
        NewService.getNewService().getNews();
        PlaceService.getPlaceService().getPlace();
        PlayerService.getPlayerService().getPlayerBuffer();
        Goods_houseService.getGoods_houseService().getGoods_houseBuffer();


        //定时上次缓存
        Timer timer = new Timer();
        timer.schedule(new MyTask(),1000,10000);



    }

}
