package cn.episooo.dao;

import cn.episooo.po.Photo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;

/**
 * @Author ：Ep
 * @Date ：Created in 2020/2/17 0:11
 * @Description：
 */
@Repository
public interface PhotoDao {

    @Insert("insert into photo (uid,aid,date,prepath,path) VALUES(#{uid},#{aid},#{date},#{prepath},#{path})")
    public int addPhoto(Photo photo);

    @Select({"<script>Select * from photo where uid = #{uid}" ,
            "<when test='aid!=-1'> ",
            " and aid=#{aid}",
            "</when>",
            "<when test='id!=-1'> ",
            " and id=#{id}",
            "</when>",
            " and deleted= #{deleted} order by id desc</script>"})
    public ArrayList<Photo> getPhotos(@Param("uid")int uid,@Param("id") int id,@Param("aid")int aid,@Param("deleted") int deleted);


    @Update("update photo set deleted = #{deleted} , deletedday=#{da} where id=#{id} AND uid=#{uid}")
    public int updatePhoto( @Param("deleted") int deleted,@Param("uid")int uid,  @Param("id")int id, @Param("da") Date da);

    @Update("update album set deleted = 0 where id = #{aid}")
    public int recoverAlbum(@Param("aid") int aid);

    @Delete("delete from photo where uid=#{uid} and id=#{id} and deleted= 1")
    public int deletePhoto(@Param("uid") int uid, @Param("id") int id);
}
