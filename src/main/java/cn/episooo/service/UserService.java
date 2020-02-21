package cn.episooo.service;

import cn.episooo.po.User;

/**
 * @Author ：Ep
 * @Date ：Created in 2020/2/11 0:57
 * @Description：
 */

public interface UserService {
    public User getUser(User user);
    boolean signUp(User user);
}
