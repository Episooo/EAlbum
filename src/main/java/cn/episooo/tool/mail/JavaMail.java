package cn.episooo.tool.mail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * @Author ：Ep
 * @Date ：Created in 2019/9/24 20:24
 * @Description：
 */
@Component
public class JavaMail {
    private static Properties props;
    private static Authenticator auth;

    private static String host;

    private static String hostPassword;
    private static Session session ;
    private static String type = "text/html;charset=utf-8";

    private String organization;
    @Value("${mail.host}")
    public void setHost(String host) {
        JavaMail.host = host;
    }
    @Value("${mail.password}")
    public void setHostPassword(String hostPassword) {
        JavaMail.hostPassword = hostPassword;
    }
    @Value("${mail.organization}")
    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public static void setProps(Properties props) {
        JavaMail.props = props;
    }

    public static void setAuth(Authenticator auth) {
        JavaMail.auth = auth;
    }

    public static void setSession(Session session) {
        JavaMail.session = session;
    }

    public static void setType(String type) {
        JavaMail.type = type;
    }

    public static Properties getProps() {
        return props;
    }

    public static Authenticator getAuth() {
        return auth;
    }

    public static String getHost() {
        return host;
    }

    public static String getHostPassword() {
        return hostPassword;
    }

    public static Session getSession() {
        return session;
    }

    public static String getType() {
        return type;
    }

    public String getOrganization() {
        return organization;
    }

    public JavaMail(){}
    public JavaMail(String organization){
        this.organization = organization;
    }
    static {
        props = new Properties();
        props.setProperty("mail.host","smtp.qq.com");
        props.setProperty("mail.smtp.auth","true");
        auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(host, hostPassword);
            }
        };
        session = Session.getInstance(props,auth);
    }
    public void sendMail(String to,String title,String content) throws Exception {

        MimeMessage msg = new MimeMessage(session);
        try {
            msg.setFrom(new InternetAddress(host, organization,"UTF-8"));//发件人
            msg.setRecipients(Message.RecipientType.TO,to);//收件人
            msg.setSubject(title);
            msg.setContent(content,type);
            Transport.send(msg);
        } catch (MessagingException|UnsupportedEncodingException e) {
            throw e;
        }
    }
    public String getVerifyTemplate(String username, String project, String code){
        StringBuffer sb = new StringBuffer();
        sb.append("<!DOCTYPE html><html lang=\"en\"><head><meta charset=\"UTF-8\"><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"><title>EAlbum</title></head><body><h1>尊敬的\n");
        sb.append(username);
        sb.append(": </h1><p>感谢您使用");
        sb.append(project);
        sb.append("，您的激活码为：");
        sb.append(code);
        sb.append("</p></body></html>");
        return sb.toString();
    }


}
