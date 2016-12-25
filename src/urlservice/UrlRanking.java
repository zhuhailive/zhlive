package urlservice;

import com.alibaba.fastjson.JSON;
import entity.Player;
import severside.ServerBuffer;

import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

/**
 * Created by Jim on 2016/12/25.
 */
public class UrlRanking {

    private HashMap<String,Integer> AllPlayer = new HashMap<>();//该哈希表存缓存中所有的用户及他们的财产

    public void getRanking(HashMap<String, String> hm, OutputStream op){

        //取出缓存中的用户信息，进行财产排序，并存入Allplayer表中
        Collection<Player> values = ServerBuffer.hmplayer.values();
        Iterator<Player> iter = values.iterator();
        while(iter.hasNext()){
            Player player = iter.next();
            int player_wealth = player.getPlayer_bank() + player.getPlayer_cash() - player.getPlayer_debt();
            AllPlayer.put(player.getAdmin_name(),player_wealth);
        }


        //财产排序
        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(AllPlayer.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            //降序排序
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                //return o1.getValue().compareTo(o2.getValue());
                return o2.getValue().compareTo(o1.getValue());
            }
        });



        //将前十名存入哈希表中
        HashMap<String,Integer> Rankhm = new HashMap<String,Integer>();
        for (int i = 0;i < 10;i++){
            Rankhm.put(list.get(i).getKey(),list.get(i).getValue());
        }
        System.out.print(JSON.toJSONString(Rankhm));

        //输出到前端
        try {
            op.write(("{\"c2dictionary\":true,\"data\":" + JSON.toJSONString(Rankhm) + "}").getBytes());
            op.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
