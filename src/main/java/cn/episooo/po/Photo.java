package cn.episooo.po;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author ：Ep
 * @Date ：Created in 2020/2/17 0:13
 * @Description：
 */
public class Photo implements Serializable {
    private int id;
    private int uid;
    private int aid;
    private Date date;
    private String prepath;
    private String path;
    private int deleted;
    private Date deletedday;

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPrepath() {
        return prepath;
    }

    public void setPrepath(String prepath) {
        this.prepath = prepath;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public Date getDeletedday() {
        return deletedday;
    }

    public void setDeletedday(Date deletedday) {
        this.deletedday = deletedday;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "id=" + id +
                ", uid=" + uid +
                ", date=" + date +
                ", prepath='" + prepath + '\'' +
                ", path='" + path + '\'' +
                ", deleted=" + deleted +
                ", deletedday=" + deletedday +
                '}';
    }
}
