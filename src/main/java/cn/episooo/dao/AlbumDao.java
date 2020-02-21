package cn.episooo.dao;

import cn.episooo.po.Album;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;

/**
 * @Author ：Ep
 * @Date ：Created in 2020/2/18 23:04
 * @Description：
 */
@Repository
public interface AlbumDao {

    @Select("select * from album where uid = #{uid} and deleted=0")
    public ArrayList<Album> getAlbums(int uid);

    @Insert("insert into album(uid,name,size) values(#{uid},#{name},0)")
    public int addAlbum(@Param("uid")int uid,@Param("name")String name);

    @Update("update album set deleted = 1 where id=#{id}")
    public int deleteAlbum(@Param("id") int id);

    @Update("update album set name = #{name} where uid=#{uid} and id=#{id}")
    public int updateAlbum(@Param("name")String name,@Param("uid")int uid,@Param("id")int id);

    @Update("update photo set deleted = 1 , deletedday=#{date} where aid=#{aid}")
    public int deletePhotoByAid(@Param("aid")int aid, @Param("date") Date date);

    @Select("select count(*) from album where uid=#{uid} and id=#{id}")
    public int isExist(@Param("uid")int uid,@Param("id") int id);
}
