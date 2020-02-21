package cn.episooo.dao;

import cn.episooo.po.Photo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
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
            " and deleted= 0 order by id desc</script>"})
    public ArrayList<Photo> getPhotos(@Param("uid")int uid,@Param("aid")int aid);

    @Update("update photo set deleted = 1 , deletedday=#{da} where id=#{id} AND uid=#{uid}")
    public int deletePhoto( @Param("uid")int uid,  @Param("id")int id, @Param("da") Date da);

    }
