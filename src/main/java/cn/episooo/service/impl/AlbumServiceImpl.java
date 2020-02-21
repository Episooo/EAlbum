package cn.episooo.service.impl;

import cn.episooo.dao.AlbumDao;
import cn.episooo.po.Album;
import cn.episooo.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;

/**
 * @Author ：Ep
 * @Date ：Created in 2020/2/18 23:08
 * @Description：
 */
@Service
public class AlbumServiceImpl implements AlbumService {

    @Autowired
    AlbumDao albumDao;

    @Override
    public ArrayList<Album> getAlbums(int uid) {
        return albumDao.getAlbums(uid);
    }

    @Override
    public boolean addAlbum(int uid, String name) {
        return albumDao.addAlbum(uid,name)==1;
    }

    @Override
    @Transactional
    public boolean deleteAlbum(int uid, int id)  {
        if(albumDao.isExist(uid,id)==1){
            albumDao.deletePhotoByAid(id,new Date());
            albumDao.deleteAlbum(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateAlbum(String name, int uid, int id) {
        return albumDao.updateAlbum(name,uid,id)==1;
    }
}
