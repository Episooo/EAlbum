package cn.episooo.po;

import java.io.Serializable;

/**
 * @Author ：Ep
 * @Date ：Created in 2020/2/18 23:05
 * @Description：
 */
public class Album implements Serializable {
    private int id;
    private int uid;
    private int size;
    private String name;
    private String previewPhotoPath;

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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPreviewPhotoPath() {
        return previewPhotoPath;
    }

    public void setPreviewPhotoPath(String previewPhotoPath) {
        this.previewPhotoPath = previewPhotoPath;
    }

    @Override
    public String toString() {
        return "Album{" +
                "id=" + id +
                ", uid=" + uid +
                ", size=" + size +
                ", name='" + name + '\'' +
                ", previewPhotoPath='" + previewPhotoPath + '\'' +
                '}';
    }
}
