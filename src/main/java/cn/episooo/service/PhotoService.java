package cn.episooo.service;

import cn.episooo.vo.PhotoVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author ：Ep
 * @Date ：Created in 2020/2/17 0:05
 * @Description：
 */
public interface PhotoService {
    public boolean savePhoto(String path, String filename, int uid,int aid, MultipartFile upload);
    public List<PhotoVO> getPhoto(int id, int aid);
    public boolean deletePhoto(int uid ,int id);


}
