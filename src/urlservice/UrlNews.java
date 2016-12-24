package urlservice;

import com.alibaba.fastjson.JSON;
import severside.ServerBuffer;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

/**
 * Created by keben on 2016/12/23.
 */
public class UrlNews {


    /**
     *
     * @param hashMap 空
     * @param op
     */
    public void getNews(HashMap<String,String> hashMap , OutputStream op){

        int random = (int)(1+Math.random()*(36-1+1));
        HashMap<String, String> returnha = new HashMap<>();
        returnha.put("news_goods_id",String.valueOf(ServerBuffer.hmnews.get(random).getNews_goods_id()));
        returnha.put("news_content",ServerBuffer.hmnews.get(random).getNews_content());

        try {
            op.write(("{\"c2dictionary\":true,\"data\":" + JSON.toJSONString(returnha) + "}").getBytes());
            op.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
