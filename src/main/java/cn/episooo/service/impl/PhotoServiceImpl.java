package cn.episooo.service.impl;

import cn.episooo.dao.PhotoDao;
import cn.episooo.po.Photo;
import cn.episooo.service.PhotoService;
import cn.episooo.tool.image.ImageUtils;
import cn.episooo.vo.PhotoVO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * @Author ：Ep
 * @Date ：Created in 2020/2/17 0:06
 * @Description：
 */
@Service
public class PhotoServiceImpl implements PhotoService {

    @Autowired
    PhotoDao photoDao;

    static Logger logger = Logger.getLogger ( PhotoServiceImpl.class.getName () ) ;

    /*
     * @Description : 这里将预览图存在projectPath/photos/{uid}/pre/
     *              原图存在projectPath/photos/{uid}/
     * @param ： projectPath 项目目录
     * @param ： filename 相片名称
     * @param ： uid 用户id
     * @param ： aid 相册id
     * @param ： upload 相片文件
     * @Return : boolean 是否成功
     */
    @Transactional
    public boolean savePhoto(String projectPath, String filename, int uid,int aid, MultipartFile upload){

        String relativePath = "/photos/" + uid +"/";
        String path = projectPath+relativePath;
        //判断该路径是否存在
        File file = new File(path);
        if(!file.exists()){
            //创建文件夹
            file.mkdirs();
        }
        String relativePrepath =relativePath+"pre/";
        String prepath = projectPath+relativePrepath;

        file = new File(prepath);
        if(!file.exists()){
            //创建文件夹
            file.mkdirs();
        }
        //把文件的名称设置唯一值：uuid
        String uuid = UUID.randomUUID().toString().replace("-", "");

        filename = uuid+"_"+filename;
        //完成文件上传
        Photo photo = new Photo();
        try {
            upload.transferTo(new File(path,filename));
            ImageUtils.drawPreviewImage(path+filename);

        } catch (IOException e) {
            e.printStackTrace();
        }

        photo.setUid(uid);
        photo.setAid(aid);
        photo.setPrepath(relativePrepath+filename);
        photo.setPath(relativePath+filename);
        photo.setDate(new Date());


        return photoDao.addPhoto(photo)==1;
    }
    /*
     * @Description :
     * @param ： uid 用户id
     * @param ： aid 如果aid=-1代表不进行相册筛选
     * @Return : java.util.ArrayList<cn.episooo.vo.PhotoVO>
     */
    @Override
    public ArrayList<PhotoVO> getPhoto(int uid,int aid,int deleted) {
        ArrayList<Photo> photos = photoDao.getPhotos(uid,-1,aid,deleted);
        if(photos.size()==0){
            return null;
        }
        String strDateFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);

        String day =sdf.format(photos.get(0).getDate()) ;
        ArrayList<PhotoVO> result = new ArrayList<>();

        PhotoVO itemVO = new PhotoVO();
        itemVO.setDay(day);
        itemVO.setPhotos(new ArrayList<>());

        Photo item ;
        String itemDay;


        for (int i = 0; i < photos.size(); i++) {
            item = photos.get(i);
            itemDay = sdf.format(item.getDate());
            if(!day.equals(itemDay)){
                //将上一次的photoVO添加到result中
                result.add(itemVO);
                //重新生成photoVO
                day = itemDay;
                itemVO = new PhotoVO();
                itemVO.setDay(day);
                itemVO.setPhotos(new ArrayList<>());

            }
            itemVO.getPhotos().add(item);
        }
        result.add(itemVO);

        return result;
    }


    /*
     * @Description :
     * @param ： uid 此处用户uid用于校验 该相册id是否属于用户
     * @param ： id  相册id
     * @Return : boolean
     */
    @Override
    public boolean deletePhoto(int uid ,int id) {
        Date date = new Date();
        System.out.println(date);
        int res = 0;
        res = photoDao.updatePhoto(1,uid,id,date);
        return res==1;
    }
    /*
     * @Description :
     * @param ： uid 此处用户uid用于校验 该相册id是否属于用户
     * @param ： id  相册id
     * @Return : boolean
     */
    @Override
    @Transactional
    public boolean recoverPhoto(int uid ,int id) {

        Photo photo = photoDao.getPhotos(uid,id,-1,1).get(0);
        int res = 0;
        if(photo!=null){
            res = photoDao.updatePhoto(0,uid,id,null);
            if(res==1){
                //如果对应相册没了，就恢复对应相册
                photoDao.recoverAlbum(photo.getAid());
            }
        }
        return res==1;
    }
    @Override
    @Transactional
    public boolean deletePhotoAbsolutly(int uid, int id, String path){

        Photo photo = photoDao.getPhotos(uid, id, -1, 1).get(0);

        if(photoDao.deletePhoto(uid,id)==1){
            File file = new File(path+photo.getPath());
            if(file.exists()){
               if(!file.delete()){
                   throw new RuntimeException("删除id为"+id+"的原图失败");
               }
            }else {
                logger.error("文件："+file.getAbsolutePath()+"不存在");
            }
            file = new File(path+photo.getPrepath());
            if(file.exists()){
                if(!file.delete()){
                    throw new RuntimeException("删除id为"+id+"的预览图失败");
                }
            }else {
                logger.error("文件："+file.getAbsolutePath()+"不存在");
            }
            return true;
        }
        return false;
    }

}
