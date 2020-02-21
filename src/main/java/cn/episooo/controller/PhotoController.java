package cn.episooo.controller;

import cn.episooo.po.User;
import cn.episooo.service.PhotoService;
import cn.episooo.vo.PhotoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author ：Ep
 * @Date ：Created in 2020/2/16 20:40
 * @Description：
 */
@Controller
@RequestMapping("/photo")
public class PhotoController {

    @Autowired
    PhotoService photoService;

    @RequestMapping("deletePhoto")
    @ResponseBody
    public boolean deletePhoto(int id,HttpSession session){
        User user = (User) session.getAttribute("user");

        return photoService.deletePhoto(user.getId(),id);
    }

    @RequestMapping("/upload")
    @ResponseBody
    public Map<String, Object> upload(int aid, MultipartFile upload,HttpServletRequest request, HttpSession session) throws IOException {
        //使用fileupload组件完成上传
        Map<String, Object> map = new HashMap<>();

        User user = (User) session.getAttribute("user");
        String projetPath = request.getSession().getServletContext().getRealPath("/");
        System.out.println(projetPath);

        String filename = upload.getOriginalFilename();
        if(photoService.savePhoto(projetPath, filename, user.getId(),aid, upload)) {
            map.put("code", 1);
            map.put("msg", "上传成功！");
        }

        return map;
    }
    /*
     * @Description :
     * @param ： id 
     * @Return : java.util.List<cn.episooo.vo.PhotoVO>
     */
    @RequestMapping("/getPhotos")
    @ResponseBody
    public List<PhotoVO> getPhotos(@RequestParam int id){
        return photoService.getPhoto(id,-1);
    }

    @RequestMapping("/getPhotosByAid")
    @ResponseBody
    public List<PhotoVO> getPhotosByAid(@RequestParam int id,@RequestParam int aid){
        return photoService.getPhoto(id,aid);
    }
}
