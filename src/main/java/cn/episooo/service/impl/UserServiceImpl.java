package cn.episooo.service.impl;

import cn.episooo.dao.UserDao;
import cn.episooo.po.User;
import cn.episooo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author ：Ep
 * @Date ：Created in 2020/2/11 0:59
 * @Description：
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao ;



    @Override
    public User getUser(User user) {
        return userDao.find(user);
    }

    @Override
    public boolean signUp(User user) {
        return userDao.insert(user)==1;
    }
}
