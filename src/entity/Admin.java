package entity;

/**
 * Created by keben on 2016/12/20.
 */
public class Admin {
    private int id;

    private String sign_status;

    public String getSign_status() {
        return sign_status;
    }

    public void setSign_status(String sign_status) {
        this.sign_status = sign_status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String admin_name;
    private String admin_password;
    private String admin_lasttime;

    public String getAdmin_name() {
        return admin_name;
    }

    public void setAdmin_name(String admin_name) {
        this.admin_name = admin_name;
    }

    public String getAdmin_password() {
        return admin_password;
    }

    public void setAdmin_password(String admin_password) {
        this.admin_password = admin_password;
    }

    public String getAdmin_lasttime() {
        return admin_lasttime;
    }

    public void setAdmin_lasttime(String admin_lasttime) {
        this.admin_lasttime = admin_lasttime;
    }
}
