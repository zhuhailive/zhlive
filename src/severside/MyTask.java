package severside;

import entity.Goods_house;
import entity.Player;
import serviceimpl.Goods_houseService;
import serviceimpl.PlayerService;

import java.util.TimerTask;

/**
 * Created by keben on 2016/12/28.
 */
public class MyTask extends TimerTask{

    @Override
    public void run() {
        System.out.println("更新数据库数据");
        for(int i = 1 ; i <= ServerBuffer.hmplayer.size() ; i++ ){
            System.out.println("第"+i+"个player上传");
            PlayerService.getPlayerService().updateplayer(ServerBuffer.hmplayer.get(i));
            Goods_houseService.getGoods_houseService().updataGoods_house(ServerBuffer.hmpgoods_house.get(i));
        }
    }
}
