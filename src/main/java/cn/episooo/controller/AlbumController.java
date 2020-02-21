package cn.episooo.controller;

import cn.episooo.po.Album;
import cn.episooo.po.User;
import cn.episooo.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 * @Author ：Ep
 * @Date ：Created in 2020/2/18 23:11
 * @Description：
 */
@Controller
@RequestMapping("/album")
public class AlbumController  {

    @Autowired
    AlbumService albumService;

    @RequestMapping("/getAlbums")
    @ResponseBody
    public ArrayList<Album> getAlbum(HttpSession session){
        User user = (User) session.getAttribute("user");

        return albumService.getAlbums(user.getId());
    }

    @RequestMapping("/addAlbum")
    @ResponseBody
    public boolean addAlbum( String name,HttpSession session) {
        User user = (User) session.getAttribute("user");
        return albumService.addAlbum(user.getId(),name);
    }

    @RequestMapping("/deleteAlbum")
    @ResponseBody
    public boolean deleteAlbum(int id,HttpSession session) {
        User user = (User) session.getAttribute("user");
        return albumService.deleteAlbum(user.getId(),id);
    }

    @RequestMapping("/updateAlbum")
    @ResponseBody
    public boolean updateAlbum(String name, int id,HttpSession session) {
        User user = (User) session.getAttribute("user");
        return albumService.updateAlbum(name,user.getId(),id);
    }

}
