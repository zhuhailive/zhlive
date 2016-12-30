package urlservice;

import com.alibaba.fastjson.JSON;
import serviceimpl.News_result;
import severside.ServerBuffer;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

/**请求新闻url所对应的方法 controllor
 * Created by keben on 2016/12/23.
 */
public class UrlNews {


    /**
     *
     * @param hashMap admin_id
     * @param op
     */
    public void getNews(HashMap<String,String> hashMap , OutputStream op){

        int admin_id = Integer.parseInt(hashMap.get("admin_id"));
        int random = (int)(1+Math.random()*(36-1+1));
        HashMap<String, String> returnha = new HashMap<>();
//        returnha.put("news_goods_id",String.valueOf(ServerBuffer.hmnews.get(random).getNews_goods_id()));

//        returnha.put("news_content",ServerBuffer.hmnews.get(random).getNews_content());

        returnha.put("news_id",String.valueOf(ServerBuffer.hmnews.get(random).getNews_id()));

        //如果新闻关系到物品

        if(random> 0 && random < 17){
            News_result.getNews_result().setGoodsEffect(random);
        }
        //新闻关系到health
        else if(random> 16 && random < 30){
            News_result.getNews_result().setHealthEffect(random,admin_id);
        }
        //新闻关系到cash
        else if(random> 29 && random< 37){
            News_result.getNews_result().setCashEffect(random,admin_id);
        }


        try {
            op.write(("{\"c2dictionary\":true,\"data\":" + JSON.toJSONString(returnha) + "}").getBytes());
            op.flush();
            op.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
