package cn.episooo.po;


import cn.episooo.annotation.Email;
import com.sun.istack.NotNull;

import java.io.Serializable;

/**
 * @Author ：Ep
 * @Date ：Created in 2020/2/11 1:04
 * @Description：
 */
public class User implements Serializable {

    private Integer id;
    @NotNull
    private String username;
    @NotNull
    private String password;
    @Email
    private String email;
    private int active = 0;


    public User(){}

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
