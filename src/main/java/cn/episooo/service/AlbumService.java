package cn.episooo.service;

import cn.episooo.po.Album;

import java.util.ArrayList;

/**
 * @Author ：Ep
 * @Date ：Created in 2020/2/18 23:07
 * @Description：
 */
public interface AlbumService {

    public ArrayList<Album> getAlbums(int uid);

    
    public boolean addAlbum(int uid,String name);


    public boolean deleteAlbum(int uid,int id);


    public boolean updateAlbum(String name,int uid,int id);
}
