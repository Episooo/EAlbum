package cn.episooo.tool.image;

import org.junit.Test;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

/**
 * @ Author ：Ep
 * @ Date ：Created in 2019/9/10 16:11
 * @ Description：
 */
@Component
public class VerifyCode {
    private int width = 100;
    private int height = 46;
    private Random random = new Random();
    private String[] fontNames = {"宋体","华文楷体","黑体","微软雅黑","幼圆"} ;
    private String textArea = "23456789qwertyupasdfghjkzxcvbnmQWERTYUPASDFGHJKLZXCVBNM";
    //去除 0 o O 1 i l I
    private Color bgColor ;
    private int fontColorBound = 220;
    private  String code = "";


    private Color getRandomColor(int bound){
        int red = random.nextInt(bound);
        int green = random.nextInt(bound);
        int blue = random.nextInt(bound);
        return new Color(red,green,blue);
    }

    private Color getRandomColor(int min,int max){
        /**
         * create by: Ep
         * description:
         * create time: 2019/9/10 16:21
         *
         * @Param: min
         * @Param: max
         * @return java.awt.Color
         */
        int red = random.nextInt(max-min)+min;
        int green = random.nextInt(max-min)+min;
        int blue = random.nextInt(max-min)+min;
        return new Color(red,green,blue);
    }

    private Font getRandomFont(){
        int index = random.nextInt(fontNames.length);
        String fontName = fontNames[index];
        int style = random.nextInt(3)+1;
        int size = random.nextInt(5)+30;
        return new Font(fontName,style,size);
    }
    private char getRandomChar(){
        int index = random.nextInt(textArea.length());
        return textArea.charAt(index);
    }
    private void drawLine(BufferedImage image){
        int num = 5;
        Graphics2D g2= (Graphics2D)image.getGraphics();
        for(int i =0; i<num; i++){
            int x1 = random.nextInt(width);
            int y1 = random.nextInt(height);
            int x2 = random.nextInt(width);
            int y2 = random.nextInt(height);
            g2.setStroke(new BasicStroke(0.5F));
            g2.setColor(getRandomColor(100,180));
            g2.drawLine(x1,y1,x2,y2);
        }

    }
    private void drawDot(BufferedImage image){
        int num = 40;
        Graphics2D g2= (Graphics2D)image.getGraphics();
        for(int i =0; i<num; i++){
            int x = random.nextInt(width-1);
            int y = random.nextInt(height-1);
            int x1 = random.nextInt(3);
            int y1 = random.nextInt(3);
            g2.setStroke(new BasicStroke(0.5F));
            g2.setColor(getRandomColor(90,160));
            g2.fillRect(x,y,1,1);
        }

    }
    private void drawString(BufferedImage image){
        Graphics2D g = (Graphics2D) image.getGraphics();//得到绘制环境
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <4 ; i++) {
            String s = String.valueOf(getRandomChar());
            sb.append(s);
            g.setFont(getRandomFont());
            g.setColor(getRandomColor(fontColorBound));

            int x = 3+(width/4)*i+random.nextInt(2);
            int y = height - random.nextInt(5) -15;
            double degree = random.nextInt() % 30*Math.PI / 180;
            g.rotate(degree,x,y);
            g.drawString(s,x,y);
            g.rotate(-degree,x,y);
        }
        this.code = sb.toString().toLowerCase();
    }
    private  BufferedImage createImage(){
        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) image.getGraphics();//得到绘制环境
        bgColor = new Color(255,255,255);
        g.setColor(bgColor);
        g.fillRect(0,0,width,height);
        return image;
    }

    public BufferedImage getImage(){
        BufferedImage image = createImage();
        drawLine(image);
        drawDot(image);
        drawString(image);
        return image;
    }
    public String getCode(){
        return this.code;
    }
    public static void output(BufferedImage image, OutputStream out) throws IOException {
        ImageIO.write(image,"JPEG",out);
    }
    @Test
    public void fun1() throws IOException {
//        BufferedImage bi = new BufferedImage(70,35,BufferedImage.TYPE_INT_RGB);
//        Graphics2D g = (Graphics2D) bi.getGraphics();//得到绘制环境
//        g.setColor(bgColor);
//        g.fillRect(0,0,width,height);
//        g.setColor(Color.RED);
//        g.drawString("",2,35-2);

//        ImageIO.write(bi,"JPEG",new FileOutputStream(""));
        VerifyCode vc = new VerifyCode();
        BufferedImage bi = vc.getImage();
        VerifyCode.output(bi,new FileOutputStream("D:/vc.jpg"));
        System.out.println(vc.getCode());
    }

}
