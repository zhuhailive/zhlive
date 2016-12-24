package entity;

/**
 * Created by keben on 2016/12/18.
 */
public class Player {
    private int id;
    private int admin_id;
    private int player_cash;
    private int player_debt;
    private int player_bank;
    private int player_health;
    private int player_reputation;
    private String goods_contain;
    private int remain_days;
    private int remain_tradetime;
    private String player_status;
    private String admin_name;

    public String getAdmin_name() {
        return admin_name;
    }

    public void setAdmin_name(String admin_name) {
        this.admin_name = admin_name;
    }

    public String getPlayer_status() {
        return player_status;
    }

    public void setPlayer_status(String palyer_status) {
        this.player_status = palyer_status;
    }

    public int getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(int admin_id) {
        this.admin_id = admin_id;
    }
    public int getId() {
        return id;
    }

    public String getGoods_contain() {
        return goods_contain;
    }

    public void setGoods_contain(String goods_contain) {
        this.goods_contain = goods_contain;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPlayer_cash() {
        return player_cash;
    }

    public void setPlayer_cash(int player_cash) {
        this.player_cash = player_cash;
    }

    public int getPlayer_debt() {
        return player_debt;
    }

    public void setPlayer_debt(int player_debt) {
        this.player_debt = player_debt;
    }

    public int getPlayer_bank() {
        return player_bank;
    }

    public void setPlayer_bank(int player_bank) {
        this.player_bank = player_bank;
    }

    public int getPlayer_health() {
        return player_health;
    }

    public void setPlayer_health(int player_health) {
        this.player_health = player_health;
    }

    public int getPlayer_reputation() {
        return player_reputation;
    }

    public void setPlayer_reputation(int player_reputation) {
        this.player_reputation = player_reputation;
    }



    public int getRemain_days() {
        return remain_days;
    }

    public void setRemain_days(int remain_days) {
        this.remain_days = remain_days;
    }

    public int getRemain_tradetime() {
        return remain_tradetime;
    }

    public void setRemain_tradetime(int remain_tradetime) {
        this.remain_tradetime = remain_tradetime;
    }
}
