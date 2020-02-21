package cn.episooo.controller;

import cn.episooo.po.User;
import cn.episooo.service.UserService;
import cn.episooo.tool.image.VerifyCode;
import cn.episooo.tool.mail.JavaMail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @Author ：Ep
 * @Date ：Created in 2020/2/11 1:01
 * @Description：
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JavaMail mail;

    private Random random = new Random();

    @RequestMapping("/signin")
    @ResponseBody
    public Map<String,Object> signin(@RequestParam String email, @RequestParam String password, HttpSession session, HttpServletResponse response) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user = userService.getUser(user);
        session.setAttribute("user", user);

        Map<String,Object> map = new HashMap<>();
        map.put("user",user);
        map.put("code",user!=null?1:-1);


        return map;

    }

    @RequestMapping("/isLogin")
    @ResponseBody
    public boolean isLogin(HttpSession session){
        return session.getAttribute("user")!=null;
    }

    @RequestMapping("/logOut")
    @ResponseBody
    public void logOut(HttpSession session){
        session.setAttribute("user",null);
    }

    @PostMapping("/signup")
    @ResponseBody
    public int signup(@Validated User user, @RequestParam String verifycode, HttpSession session, HttpServletResponse response) {
        //-1:验证码不正确  -2:邮箱已存在 -3:邮箱不存在 1：注册成功，等待激活
        int result = -1;
        user.setEmail(user.getEmail().toLowerCase());
        if (session.getAttribute("verifycode").equals(verifycode.toLowerCase())) {
            if (userService.getUser(user) == null) {
                result = 1;//1：注册成功,等待激活

                session.setAttribute("ruser", user);
                //发送邮件
                session.setAttribute("emailcode", String.valueOf(random.nextInt(9000) + 1000));
                try {
                    mail.sendMail(user.getEmail(), "EAlbum激活码",
                            mail.getVerifyTemplate(user.getUsername(), "EAlbum", (String) session.getAttribute("emailcode")));
                }catch (Exception e){
                    e.printStackTrace();
                    result = -3;
                }
                            } else {
                result = -2;//-2:邮箱已存在
            }
        } else {
            result = -1;//-1:验证码不正确
        }

        session.setAttribute("verifycode", null);
        return result;
    }

    @RequestMapping("/activate")
    @ResponseBody
    public int acticate(@RequestParam String code, HttpSession session, HttpServletResponse response) {
        //1激活成功 -1激活码错误
        int result = -1;
        User user = (User) session.getAttribute("ruser");
        if (session.getAttribute("emailcode").equals(code)) {
            if (user != null) {
                user.setActive(1);
                userService.signUp(user);
                result = 1;
            }
        }
        return result;

    }

    @RequestMapping("verifycode")
    public void verifycode(HttpSession session, HttpServletResponse response) {
        session.getId();
        VerifyCode vc = new VerifyCode();
        BufferedImage bi = vc.getImage();
        try {
            VerifyCode.output(bi, response.getOutputStream());
            session.setAttribute("verifycode", vc.getCode());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
