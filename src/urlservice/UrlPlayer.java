package urlservice;

import com.alibaba.fastjson.JSON;
import entity.Admin;
import entity.Player;
import serviceimpl.AdminService;
import serviceimpl.Goods_houseService;
import serviceimpl.PlayerService;
import severside.ServerBuffer;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

/**
 * 处理player 的url
 * Created by keben on 2016/12/20.
 */
public class UrlPlayer {


    /**
     * 登陆player
     *
     * @param hm construct2 回传的参数: admin_name = ; admin_password=
     * @param op 输出流
     */
    public void adminLoginControllor(HashMap<String, String> hm, OutputStream op) {

        //data载入
        String data;


        //失败所用hashmap
        HashMap<String, String> returnhm = new HashMap<>();


        //从construct2得到的数据找出对应的admin
        Admin admin = AdminService.getAdminService().findByAdminAccount(hm.get("admin_name"));


        //并没有注册过
        if (admin == null) {
            returnhm.put("player_status", "用户不存在");
            data = ("{\"c2dictionary\":true,\"data\":" + JSON.toJSONString(returnhm) + "}");
        }


        //注册过了
        else {

            if (hm.get("admin_password").equals(admin.getAdmin_password())) {

                //缓存player
                Player player = ServerBuffer.hmplayer.get(admin.getId());

//                ServerBuffer.hmplayer.put(admin.getId(), player);

                //回传player的表数据、
                System.out.println("登陆成功");
                player.setPlayer_status("登陆成功");

                data = ("{\"c2dictionary\":true,\"data\":" + JSON.toJSONString(player) + "}");


            } else {


                //密码不匹配
                returnhm.put("player_status", "密码不正确");
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
     * 注册admin
     *
     * @param hm admin_name= admin_password =
     * @param op
     */
    public void adminsignControllor(HashMap<String, String> hm, OutputStream op) {


        HashMap<String, String> returnhm = new HashMap<>();
        String data;

        if (AdminService.getAdminService().findByAdminAccount(hm.get("admin_name")) == null) {
            System.out.println("成功注册");

            Admin admin = new Admin();
            admin.setAdmin_name(hm.get("admin_name"));
            admin.setAdmin_password(hm.get("admin_password"));

            //insert admin 数据库
            AdminService.getAdminService().addAdmin(admin);

            //重新拿出 有id
            admin = AdminService.getAdminService().findByAdminAccount(hm.get("admin_name"));

            //insert player 数据库
            PlayerService.getPlayerService().addPlayer(admin);

            //insert goods 数据库
            Goods_houseService.getGoods_houseService().addGoods_house(admin);
            //添加player缓存
            Player player = PlayerService.getPlayerService().findById(admin.getId());
            ServerBuffer.hmplayer.put(admin.getId(),player);

            admin.setSign_status("成功注册");
            data = ("{\"c2dictionary\":true,\"data\":" + JSON.toJSONString(admin) + "}");

        } else {
            returnhm.put("admin_name", "");
            returnhm.put("sign_status", "用户已存在");
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
     * 更新还钱
     *
     * @param hm admin_id , player_cash (还钱钱数),
     * @param op
     */
    public void updataDebt(HashMap<String, String> hm, OutputStream op) {

        Player player = ServerBuffer.hmplayer.get(Integer.parseInt(hm.get("admin_id")));


        //计算
        System.out.println("-----------------------------0-" + player.getPlayer_debt());
        System.out.println("-----------------------------1-" + Integer.parseInt(hm.get("player_debt")));
        int newdebt = player.getPlayer_debt() + Integer.parseInt(hm.get("player_debt"));
        System.out.println(newdebt);
        ServerBuffer.hmplayer.get(Integer.parseInt(hm.get("admin_id"))).setPlayer_debt(newdebt);


        HashMap<String, String> returnhm = new HashMap<>();
        returnhm.put("player_debt", String.valueOf(newdebt));


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
     * @param hm 这个方法需要construct2 往服务器传一个hm，hm包含的key值为“admin_id","player_cash” 这个方法里面可以用hm.get("admin_id")取到数据。
     * @param op 输出流，
     *           JSON.toJSONString(HashMap)用的是 op.write(("{\"c2dictionary\":true,\"data\":" + JSON.toJSONString(returnhm) + "}").getBytes());
     *           JSON.toJSONString(模型类：player)op.write(("{\"c2dictionary\":true,\"data\":" + JSON.toJSONString(player) + "}").getBytes());
     */
    public void updataCash(HashMap<String, String> hm, OutputStream op) {
        /**
         * 读取前端发送来的加减现金值，与缓存中的现金值进行加减运算，更新缓存中人物属性的现金值
         */

        Player player = ServerBuffer.hmplayer.get(Integer.parseInt(hm.get("admin_id")));

        int current_cash = player.getPlayer_cash() + Integer.parseInt(hm.get("player_cash"));
        ServerBuffer.hmplayer.get(Integer.parseInt(hm.get("admin_id"))).setPlayer_cash(current_cash);

        HashMap<String, String> returnhm = new HashMap<>();
        returnhm.put("player_cash", String.valueOf(current_cash));


        try {
            op.write(("{\"c2dictionary\":true,\"data\":" + JSON.toJSONString(returnhm) + "}").getBytes());
            op.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 更新存款
     *
     * @param hm key值为"admin_id","player_bank"
     * @param op
     */
    public void updataBank(HashMap<String, String> hm, OutputStream op) {
        /**
         * 读取前端发送来的存款操作，正数为存，负数为取，与原存款相加获得现存款数
         *
         */
        Player player = ServerBuffer.hmplayer.get(Integer.parseInt(hm.get("admin_id")));

        int current_bank = player.getPlayer_bank() + Integer.parseInt(hm.get("player_bank"));
        ServerBuffer.hmplayer.get(Integer.parseInt(hm.get("admin_id"))).setPlayer_bank(current_bank);

        HashMap<String, String> returnhm = new HashMap<>();
        returnhm.put("player_bank", String.valueOf(current_bank));

        try {
            op.write(("{\"c2dictionary\":true,\"data\":" + JSON.toJSONString(returnhm) + "}").getBytes());
            op.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * key值为"admin_id","player_health"
     */
    public void updataHealth(HashMap<String, String> hm, OutputStream op){
        /**
         * 读取前端发送来的扣除健康值信息，正数加健康值，负数减健康值，返回现健康值
         *
         */
        Player player = ServerBuffer.hmplayer.get(Integer.parseInt(hm.get("admin_id")));

        int current_health = player.getPlayer_health() + Integer.parseInt(hm.get("player_health"));
        ServerBuffer.hmplayer.get(Integer.parseInt(hm.get("admin_id"))).setPlayer_health(current_health);

        HashMap<String, String> returnhm = new HashMap<>();
        returnhm.put("player_health", String.valueOf(current_health));

        try {
            op.write(("{\"c2dictionary\":true,\"data\":" + JSON.toJSONString(returnhm) + "}").getBytes());
            op.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * key值为"admin_id","player_reputation"
     */
    public void updataReputation(HashMap<String,String> hm,OutputStream op){
        /**
         * 读取前端发送来的名声信息，正数为涨声望，负数为跌声望，返回现名声值
         */
        Player player = ServerBuffer.hmplayer.get(Integer.parseInt(hm.get("admin_id")));

        int current_Reputation = player.getPlayer_reputation() + Integer.parseInt(hm.get("player_reputation"));
        if (current_Reputation < 0) current_Reputation = 0;
        ServerBuffer.hmplayer.get(Integer.parseInt(hm.get("admin_id"))).setPlayer_reputation(current_Reputation);

        HashMap<String, String> returnhm = new HashMap<>();
        returnhm.put("player_reputation", String.valueOf(current_Reputation));

        try {
            op.write(("{\"c2dictionary\":true,\"data\":" + JSON.toJSONString(returnhm) + "}").getBytes());
            op.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param hm key值为"admin_id","goods_contain"
     * @param op
     */
    public void updataGoods_contain(HashMap<String,String> hm,OutputStream op){
        /**
         * 读取前端的出租房储物大小,直接设置为现出租房储物大小
         */
        ServerBuffer.hmplayer.get(Integer.parseInt(hm.get("admin_id"))).setGoods_contain(Integer.parseInt(hm.get("goods_contain")));
        HashMap<String, String> returnhm = new HashMap<>();
        returnhm.put("goods_contain", String.valueOf(hm.get("goods_contain")));

        try {
            op.write(("{\"c2dictionary\":true,\"data\":" + JSON.toJSONString(returnhm) + "}").getBytes());
            op.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param hm key值为"admin_id"
     * @param op
     */
    public void updataRemain_days(HashMap<String,String> hm,OutputStream op){
        /**
         * 每次请求则该id对应的用户还债期限减一天
         */
        Player player = ServerBuffer.hmplayer.get(Integer.parseInt(hm.get("admin_id")));

        int current_remain_days = player.getRemain_days() - 1;
        ServerBuffer.hmplayer.get(Integer.parseInt(hm.get("admin_id"))).setRemain_days(current_remain_days);

        HashMap<String, String> returnhm = new HashMap<>();
        returnhm.put("remain_days", String.valueOf(current_remain_days));


        try {
            op.write(("{\"c2dictionary\":true,\"data\":" + JSON.toJSONString(returnhm) + "}").getBytes());
            op.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     *@param hm key值为"admin_id"
     */
    public void updataTradetime(HashMap<String,String> hm,OutputStream op){
        /**
         * 每次请求则id对应的用户交易剩余次数减去一次
         */
        Player player = ServerBuffer.hmplayer.get(Integer.parseInt(hm.get("admin_id")));

        int current_tradetime = player.getRemain_tradetime() - 1;
        ServerBuffer.hmplayer.get(Integer.parseInt(hm.get("admin_id"))).setRemain_tradetime(current_tradetime);

        HashMap<String, String> returnhm = new HashMap<>();
        returnhm.put("remain_tradetime",String.valueOf(current_tradetime));

        System.out.print(ServerBuffer.hmplayer.get(Integer.parseInt(hm.get("admin_id"))).getRemain_tradetime());
        try {
            op.write(("{\"c2dictionary\":true,\"data\":" + JSON.toJSONString(returnhm) + "}").getBytes());
            op.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
