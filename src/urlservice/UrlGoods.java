package urlservice;

import com.alibaba.fastjson.JSON;
import entity.Goods_house;
import entity.Player;
import serviceimpl.Goods_houseService;
import severside.ServerBuffer;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by keben on 2016/12/22.
 */
public class UrlGoods {

    /**
     * 取出背包所包含的物品列表
     * @param hm 参数包含player的id值
     * @param op 输出流
     */
    public void getBackPackControllor(HashMap<String,String> hm, OutputStream op){


        Goods_house goods_house;
        String data;

        if(ServerBuffer.hmpgoods_house.get(Integer.parseInt(hm.get("admin_id")))==null) {

            goods_house = Goods_houseService.getGoods_houseService().findByAdminId(Integer.parseInt(hm.get("admin_id")));

            //切割进一个二维数组
            String s1[] = goods_house.getGoods_item().split(",");
            String s2[] = goods_house.getBuyin_price().split(",");
            String s3[] = goods_house.getGoods_amount().split(",");

            String opstr[][][] = new String[Integer.parseInt(goods_house.getGoods_contains())][4][2];



            for (int i = 0 ; i < Integer.parseInt(goods_house.getGoods_contains()) ; i++ ){

                opstr[i][0][0] = "goods_id";
                opstr[i][1][0] = "goods_price";
                opstr[i][2][0] = "goods_amount";
                opstr[i][3][0] = "goods_name";

                opstr[i][0][1] = s1[i];
                opstr[i][1][1] = s2[i];
                opstr[i][2][1] = s3[i];
                opstr[i][3][1] = ServerBuffer.hmpgoods.get(Integer.parseInt(s1[i])).getGoods_name();
            }

            //切割好的数组存进缓存
            goods_house.setGoods_stringarray(opstr);

            //缓存流
            ServerBuffer.hmpgoods_house.put(Integer.parseInt(hm.get("admin_id")),goods_house);

            data = "{\"c2array\":true,\"size\":["+ goods_house.getGoods_contains() +","+ 4 +","+ 2 +"],\"data\":"+ JSON.toJSONString(opstr) +"}";

        }else {

            goods_house = ServerBuffer.hmpgoods_house.get(Integer.parseInt(hm.get("admin_id")));
            System.out.println("已经有goods缓存");
            data = "{\"c2array\":true,\"size\":["+ goods_house.getGoods_contains() +","+ 4 +","+ 2 +"],\"data\":"+ JSON.toJSONString(goods_house.getGoods_stringarray()) +"}";

        }

        try {
            op.write(data.getBytes());
            op.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    /**
     * 背包列表传回服务器，并没传进数据库
     * @param hm admin_id,     goods_id,    goods_price,    goods_amount
     * @param op
     */
    public void updataBackPackControllor(HashMap<String,String> hm, OutputStream op){

        Goods_house goods_house;
        String data;
        System.out.println(hm.get("admin_id"));

        goods_house = ServerBuffer.hmpgoods_house.get(Integer.parseInt(hm.get("admin_id")));

        //人物物品+1
        ServerBuffer.hmplayer.get(Integer.parseInt(hm.get("admin_id"))).setGoods_contain(ServerBuffer.hmplayer.get(Integer.parseInt(hm.get("admin_id"))).getGoods_contain()+1);



            if(goods_house.getGoods_item().contains(hm.get("goods_id")+",")){

                //切割进一个二维数组
                String s1[] = goods_house.getGoods_item().split(",");
                String s2[] = goods_house.getBuyin_price().split(",");
                String s3[] = goods_house.getGoods_amount().split(",");

                String opstr[][][] = new String[Integer.parseInt(goods_house.getGoods_contains())][4][2];
                opstr = ServerBuffer.hmpgoods_house.get(Integer.parseInt(hm.get("admin_id"))).getGoods_stringarray();

                int i;
                for(i = 0 ; i <s1.length ; i++){
                    if(s1[i].equals(hm.get("goods_id")))
                        break;
                }

                //计算价格
                int oldamount = Integer.parseInt(s3[i]);
                int newamount = Integer.parseInt(hm.get("goods_amount"));
                int oldprice = Integer.parseInt(s2[i]);
                int newprice = Integer.parseInt(hm.get("goods_price"));

                s3[i] = String.valueOf(newamount+oldamount);
                s2[i] = String.valueOf ((oldamount*oldprice+newamount*newprice )/(newamount+oldamount));

                opstr[i][0][1] = s1[i];
                opstr[i][1][1] = s2[i];
                opstr[i][2][1] = s3[i];
                opstr[i][3][1] = ServerBuffer.hmpgoods.get(Integer.parseInt(s1[i])).getGoods_name();

                String newgoods_item = "";
                String newgoods_price= "";
                String newgoods_amount= "";

                for( i = 0 ; i <Integer.parseInt(goods_house.getGoods_contains());i++){


                    //更新模型数据
                    if(i==s1.length-1) {
                        newgoods_item = newgoods_item + s1[i];
                        newgoods_amount = newgoods_amount + s3[i];
                        newgoods_price = newgoods_price +s2[i];
                    }else {
                        newgoods_item = newgoods_item + s1[i]+",";
                        newgoods_amount = newgoods_amount + s3[i]+",";
                        newgoods_price = newgoods_price +s2[i]+",";
                    }
                }

                //更新模型数据
                goods_house.setBuyin_price(newgoods_price);
                goods_house.setGoods_amount(newgoods_amount);
                //更新字符串
                goods_house.setGoods_stringarray(opstr);

                System.out.println(newgoods_amount);
                System.out.println(newgoods_price);

                data = "{\"c2array\":true,\"size\":["+ goods_house.getGoods_contains() +","+ 4 +","+ 2 +"],\"data\":"+ JSON.toJSONString(opstr) +"}";

            }
            else {

                goods_house.setGoods_item(goods_house.getGoods_item()+","+hm.get("goods_id"));
                goods_house.setBuyin_price(goods_house.getBuyin_price()+","+hm.get("goods_price"));
                goods_house.setGoods_amount(goods_house.getGoods_amount()+","+hm.get("goods_amount"));

                ArrayList list = new ArrayList();
                for(int i = 0 ; i <Integer.parseInt(goods_house.getGoods_contains());i++){
                    list.add(goods_house.getGoods_stringarray()[i]);
                }

                goods_house.setGoods_contains(goods_house.getGoods_contains()+1);

                String news[][] = new String[4][2];
                news[0][0]="goods_id";
                news[1][0] = "goods_price";
                news[2][0] = "goods_amount";
                news[3][0] = "goods_name";

                //更新字符串
                news[0][1] =hm.get("goods_id");
                news[1][1] = hm.get("goods_price");
                news[2][1] = hm.get("goods_amount");
                news[3][1] = ServerBuffer.hmpgoods.get(Integer.parseInt(hm.get("goods_id"))).getGoods_name();


                list.add(news);
                data = "{\"c2array\":true,\"size\":["+ goods_house.getGoods_contains() +","+ 4 +","+ 2 +"],\"data\":"+ JSON.toJSONString(list) +"}";

                int size=list.size();
                String[][][] array = (String[][][])list.toArray(new String[size][][]);
//                System.out.println(JSON.toJSON(array));


            }

        //更新自己的钱数
        Player player = ServerBuffer.hmplayer.get(Integer.parseInt(hm.get("admin_id")));
        player.setPlayer_cash(player.getPlayer_cash()-        ( Integer.parseInt(hm.get("goods_price")) *   Integer.parseInt(hm.get("goods_amount")) )   );

        try {
            op.write(data.getBytes());
            op.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }


    /**
     *
     * @param hm admin_id,goods_id,goods_price,goods_amount
     * @param op
     */
    public void saleBackPackControllor(HashMap<String,String> hm, OutputStream op){
        String data="";

        Goods_house goods_house = ServerBuffer.hmpgoods_house.get(Integer.parseInt(hm.get("admin_id")));
        goods_house.setGoods_contains(String .valueOf(Integer.parseInt(goods_house.getGoods_contains())-1));

        //人物物品-1
        ServerBuffer.hmplayer.get(Integer.parseInt(hm.get("admin_id"))).setGoods_contain(ServerBuffer.hmplayer.get(Integer.parseInt(hm.get("admin_id"))).getGoods_contain()-1);
        String opstr[][][] = new String[Integer.parseInt(goods_house.getGoods_contains())][4][2];
        opstr = ServerBuffer.hmpgoods_house.get(Integer.parseInt(hm.get("admin_id"))).getGoods_stringarray();

        //切割进一个二维数组
        String s1[] = goods_house.getGoods_item().split(",");
        String s2[] = goods_house.getBuyin_price().split(",");
        String s3[] = goods_house.getGoods_amount().split(",");

        int i;
        for(i = 0 ; i <s1.length ; i++){
            if(s1[i].equals(hm.get("goods_id")))
                break;
        }

        for( int j = i ; j < s1.length-1 ; j++ ){
            s1[j] = s1[j+1];
            s2[j] = s2[j+1];
            s3[j] = s3[j+1];
            System.out.println("并没有跳出");
        }


        for ( i = 0 ; i < Integer.parseInt(goods_house.getGoods_contains()) ; i++ ){

            opstr[i][0][0] = "goods_id";
            opstr[i][1][0] = "goods_price";
            opstr[i][2][0] = "goods_amount";
            opstr[i][3][0] = "goods_name";

            opstr[i][0][1] = s1[i];
            opstr[i][1][1] = s2[i];
            opstr[i][2][1] = s3[i];
            opstr[i][3][1] = ServerBuffer.hmpgoods.get(Integer.parseInt(s1[i])).getGoods_name();

        }

        data = "{\"c2array\":true,\"size\":["+ goods_house.getGoods_contains() +","+ 4 +","+ 2 +"],\"data\":"+ JSON.toJSONString(opstr) +"}";

        //更新自己的钱数
        Player player = ServerBuffer.hmplayer.get(Integer.parseInt(hm.get("admin_id")));

        player.setPlayer_cash(player.getPlayer_cash()+Integer.parseInt(hm.get("goods_price"))*Integer.parseInt(hm.get("goods_amount")));

        try {
            op.write(data.getBytes());
            op.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
