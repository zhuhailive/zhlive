package severside;

import entity.*;
import serviceimpl.GoodsService;
import serviceimpl.NewService;
import serviceimpl.PlaceService;
import serviceimpl.PlayerService;

import java.util.HashMap;

/**
 * Created by keben on 2016/12/20.
 */
public class ServerBuffer {
    //缓存player
    public static HashMap<Integer, Player> hmplayer = new HashMap(); //made
    //缓存物品
    public static HashMap<Integer, Goods> hmpgoods = new HashMap();  //made
    //缓存背包
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
//



    }

}
