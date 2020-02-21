package cn.episooo.tool.image;

import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @Author ：Ep
 * @Date ：Created in 2020/2/16 14:16
 * @Description：
 */
public class ImageUtils {
    private static double MAX_WIDTH = 800;
    private static double MAX_HEIGHT = 600;
    private static double MIN_WIDTH = 200;
    private static double MIN_HEIGHT = 400;


    public final static void drawPreviewImage(String imageFilePath){

        BufferedImage img = null;
        File file = new File(imageFilePath);
        String previewPath = file.getParent()+"/pre/";
        File previewfilepath = new File(previewPath);
        String outputPath = previewPath + file.getName();
        File newfile =new File(outputPath);
        System.out.println(outputPath);
        if ( !previewfilepath.exists()){//若此目录不存在，则创建之
            previewfilepath.mkdir();
        }
        try {
            System.out.println("------------------");
            System.out.println(imageFilePath);
            img = ImageIO.read(file);
            int width = img.getWidth();
            int height = img.getHeight();
            double scale = 1f ;
            //调整大小
            if(height<MAX_HEIGHT){

            }
            else {
                scale = MAX_HEIGHT/height;
                System.out.println(scale);
                width = (int)(width*scale);
                System.out.println(width*scale);
                height = (int)MAX_HEIGHT;
            }
            //绘制
            Image image = img.getScaledInstance(width,height,Image.SCALE_DEFAULT);
            BufferedImage bi = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
            Graphics g = bi.getGraphics();
            g.drawImage(image,0,0,null);
            g.dispose();
            ImageIO.write(bi,"JPEG",newfile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
//    public final static void drawPreviewImage1(String imageFilePath){
//
//        BufferedImage img = null;
//        File file = new File(imageFilePath);
//        String previewPath = file.getParent()+File.separator+"pre"+File.separator;
//        File previewfilepath = new File(previewPath);
//        String outputPath = previewPath + file.getName();
//        File newfile =new File(outputPath);
//        System.out.println(outputPath);
//        if ( !previewfilepath.exists()){//若此目录不存在，则创建之
//            previewfilepath.mkdir();
//        }
//        try {
//            img = ImageIO.read(file);
//        int width = img.getWidth();
//        int height = img.getHeight();
//        double scale = 1f ;
//        //调整大小
//         if(width<200 && height<400){
//
//         }
//         else if(width<height){
//            scale = MAX_HEIGHT/height;
//            width = (int)(width*scale)>MIN_WIDTH?(int)(width*scale):MIN_WIDTH;
//            height = MAX_HEIGHT;
//        }else {
//            scale = MAX_WIDTH/width;
//            height = (int)(height*scale)>MIN_HEIGHT?(int)(height*scale):MIN_HEIGHT;
//            width = MAX_WIDTH;
//        }
//        //绘制
//        Image image = img.getScaledInstance(width,height,Image.SCALE_DEFAULT);
//        BufferedImage bi = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
//        Graphics g = bi.getGraphics();
//        g.drawImage(image,0,0,null);
//        g.dispose();
//        ImageIO.write(bi,"JPEG",newfile);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
    @Test
    public void testOutput(){
        ImageUtils.drawPreviewImage("D:/ELSE/PHOTOS/timgUNQRWI0K.jpg");
    }
}
