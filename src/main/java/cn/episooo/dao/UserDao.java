package cn.episooo.dao;

import cn.episooo.po.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * @Author ：Ep
 * @Date ：Created in 2020/2/11 1:04
 * @Description：
 */

@Repository
public interface UserDao {

    @Select({"<script>",
            "select * from user where email=#{email} ",
            "<when test='password!=null'>",
            " AND password=#{password} ",
            "</when>",
            "</script>"})
    User find(User user);

    @Insert("insert into user(email,username,password,active) values(#{email},#{username},#{password},#{active})")
    int insert(User user);

    @Update({"<script>",
            "update user set " ,
            "<when test='password!=null'>",
            "password=#{password}," ,
            "</when>",
            "<when test='active!=null'>",
            "active=#{active} " ,
            "</when>",
            "where email=#{email}",
            "</script>"})
    int update(User user);



}
