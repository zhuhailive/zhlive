package urlservice;

import com.alibaba.fastjson.JSON;
import entity.News;
import serviceimpl.NewService;
import serviceimpl.ServiceBase;
import severside.ServerBuffer;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

/**处理place的url
 * Created by keben on 2016/12/21.
 */


public class UrlPlace {


    /**
     *
     * @param hashMap admin_id ,news_id,
     * @param op
     */
    public void getMarket_goods (HashMap<String,String> hashMap ,OutputStream op){

        //清空使用
        for(int i = 1 ; i <= ServerBuffer.hmpgoods.get(1).getGoods_sum() ; i++ ){
            ServerBuffer.hmpgoods.get(i).setSold(false);
        }


        //读取新闻
        News news = new News();
        news = ServerBuffer.hmnews.get(Integer.parseInt(hashMap.get("news_id")));

        //随机物品数量
        int i=0;
        int random_number = (int) (6 + Math.random() * (13 - 1 + 1));
        System.out.println(random_number);
        String opstr[][][] = new String[random_number][4][2];

        //是否加入新闻物品
        if(news.getNews_id()>0&&news.getNews_id()<17) {
            i=1;
            System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
            //新闻物品被使用
            ServerBuffer.hmpgoods.get(news.getNews_goods_id()).setSold(true);
            add(ServerBuffer.hmnews.get(Integer.parseInt(hashMap.get("news_id"))).getNews_goods_id(), 0, opstr, news.getEffect_goods_multiple());

        }


        //随机取物品
        for(; i < random_number ; i++){
            int random_goods = choice();
            int multiple = (int)((-2)+Math.random()*(4-1+1));
            add(random_goods,i,opstr,multiple);
        }

        try {

            op.write(("{\"c2array\":true,\"size\":["+ random_number +","+ 4 +","+ 2 +"],\"data\":"+ JSON.toJSONString(opstr) +"}").getBytes());
            op.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    //配置商品数组
    public void add(int random_goods,int i,String opstr[][][],int multiple){


        int random_amount = (int)(1+Math.random()*(9-1+1))*10;
        opstr[i][0][0] = "goods_id";
        opstr[i][1][0] = "goods_price";
        opstr[i][2][0] = "goods_amount";
        opstr[i][3][0] = "goods_name";

        opstr[i][0][1] = String .valueOf(ServerBuffer.hmpgoods.get(random_goods).getGoods_id());
        opstr[i][1][1] = String .valueOf(ServerBuffer.hmpgoods.get(random_goods).getOriginal_price()+ServerBuffer.hmpgoods.get(random_goods).getMultiple()*multiple);
        opstr[i][2][1] = String .valueOf(random_amount);
        opstr[i][3][1] = String .valueOf(ServerBuffer.hmpgoods.get(random_goods).getGoods_name());

    }

    //随机选择商品
    public int choice(){
        int id;
        while(true){
            id =  (int)(1+Math.random()*(13-1+1));

            //如果已经被使用了
            if(!ServerBuffer.hmpgoods.get(id).isSold()){
                ServerBuffer.hmpgoods.get(id).setSold(true);
                break;
            }
        }

        return id;

    }


}
