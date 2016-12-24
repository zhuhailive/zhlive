package urlservice;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import entity.Admin;
import entity.Goods_house;
import entity.Player;
import serviceimpl.AdminService;
import serviceimpl.Goods_houseService;
import serviceimpl.PlayerService;
import severside.ServerBuffer;

/**
 * 处理player 的url
 * Created by keben on 2016/12/20.
 */
public class UrlPlayer {


    /**
     * 登陆player
     * @param hm construct2 回传的参数: admin_name = ; admin_password=
     * @param op 输出流 ,用户还没注册，0；登陆成功，1；密码错误，2；
     */
    public void adminLoginControllor(HashMap<String,String> hm, OutputStream op){

        //data载入
        String data;


        //失败所用hashmap
        HashMap<String ,String> returnhm = new HashMap<>();


        //从construct2得到的数据找出对应的admin
        Admin admin = AdminService.getAdminService().findByAdminAccount(hm.get("admin_name"));



        //并没有注册过
        if(admin==null) {
            returnhm.put("player_status", "0");
            data = ("{\"c2dictionary\":true,\"data\":" + JSON.toJSONString(returnhm) + "}");
        }


        //注册过了
        else {

            if (hm.get("admin_password").equals(admin.getAdmin_password())) {

                //缓存player
//                Player player1 = PlayerService.getPlayerService().findById(admin.getId());
                Player player = ServerBuffer.hmplayer.get(admin.getId());

//                Goods_house goods_house = Goods_houseService.getGoods_houseService().findByAdminId(admin.getId());
//                //缓存背包
//                ServerBuffer.hmpgoods_house.put(admin.getId(),goods_house);

//                ServerBuffer.hmplayer.put(admin.getId(), player);




                //回传player的表数据、
                System.out.println("登陆成功");

                player.setPlayer_status("1");
                data = ("{\"c2dictionary\":true,\"data\":" + JSON.toJSONString(player) + "}");


            } else {


                //密码不匹配
                returnhm.put("player_status", "2");
                data = ("{\"c2dictionary\":true,\"data\":" + JSON.toJSONString(returnhm) + "}");
            }
        }
        try {

            op.write(data.getBytes());
            op.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     *注册admin
     * @param hm admin_name= admin_password =
     * @param op 成功注册1；注册失败0；
     */
    public void adminsignControllor(HashMap<String,String> hm, OutputStream op){


        HashMap<String ,String> returnhm = new HashMap<>();
        String data;

        if(AdminService.getAdminService().findByAdminAccount(hm.get("admin_name"))==null){
            System.out.println("成功注册");

            Admin admin = new Admin();
            admin.setAdmin_name(hm.get("admin_name"));
            admin.setAdmin_password(hm.get("admin_password"));

            //insert admin 数据库
            AdminService.getAdminService().addAdmin(admin);



            //重新从数据库拿出admin，确保admin的id是数据库提供！！！

            admin = AdminService.getAdminService().findByAdminAccount(hm.get("admin_name"));
//            admin = AdminService.getAdminService().findByAdminAccount(hm.get("admin_name"));

            //insert player 数据库
            PlayerService.getPlayerService().addPlayer(admin);

            //insert goods 数据库
            Goods_houseService.getGoods_houseService().addGoods_house(admin);

            admin.setSign_status("1");
            data = ("{\"c2dictionary\":true,\"data\":" + JSON.toJSONString(admin) + "}");

        }else{
            returnhm.put("admin_name","");
            returnhm.put("sign_status","0");
            data = ("{\"c2dictionary\":true,\"data\":" + JSON.toJSONString(returnhm) + "}");
        }

        try {
            op.write(data.getBytes());
            op.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     *更新还钱
     * @param hm admin_id , player_cash (还钱钱数),
     * @param op
     */
    public void updataDebt(HashMap<String,String> hm, OutputStream op){

        Player player = ServerBuffer.hmplayer.get(Integer.parseInt(hm.get("admin_id")));


        //计算
        System.out.println("-----------------------------0-"+player.getPlayer_debt());
        System.out.println("-----------------------------1-"+Integer.parseInt(hm.get("player_debt")));
        int newdebt = player.getPlayer_debt()-Integer.parseInt(hm.get("player_debt"));
        System.out.println(newdebt);
        player.setPlayer_debt(newdebt);


        HashMap<String,String> returnhm = new HashMap<>();
        returnhm.put("player_debt",String.valueOf(newdebt));


        try {
            op.write(("{\"c2dictionary\":true,\"data\":" + JSON.toJSONString(returnhm) + "}").getBytes());
            op.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新现金
     *
     *
     * @param hm 这个方法需要construct2 往服务器传一个hm，hm包含的key值为“admin_id","player_cash” 这个方法里面可以用hm.get("admin_id")取到数据。
     * @param op 输出流，
     *           JSON.toJSONString(HashMap)用的是 op.write(("{\"c2dictionary\":true,\"data\":" + JSON.toJSONString(returnhm) + "}").getBytes()); Dictionary
     *           JSON.toJSONString(模型类：player)op.write("{\"c2array\":true,\"size\":["+ goods_house.getGoods_contains() +","+ 4 +","+ 2 +"],\"data\":"+ JSON.toJSONString(opstr) +"}").getbytes   Array
     */
    public void updataCash(HashMap<String,String> hm, OutputStream op){



    }









}
