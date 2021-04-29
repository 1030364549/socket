package com.umf.utils;

import lombok.SneakyThrows;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * 生成图片下载或流的形式传回前台
 *
 * @date 2020/11/17 16:41
 * @return
 */
public class FontImage {

//    private static String url = "src/main/webapp/images/";
    private static String url = "E:\\";

    public static void main(String[] args) throws Exception {
//        createImage("德玛西亚", new Font("宋体", Font.BOLD, 30), new File(
//                "src/main/webapp/images/3.jpg"), 130, 60);
//        createImage("诺克萨斯", new Font("黑体", Font.BOLD, 35), new File(
//                "src/main/webapp/images/4.jpg"), 130, 60);
//        createImage("艾欧尼亚", new Font("黑体", Font.PLAIN, 40), new File(
//                "src/main/webapp/images/5.jpg"), 130, 60);

        getImage("哈哈哈");
    }

    @SneakyThrows
    public static String getImage(String string){
        String imgUrl = new StringBuffer(url).append(DateUtils.getDateTime()).append(string.hashCode()).append(".jpg").toString();
        createImage(string, new Font("宋体", Font.PLAIN, 30), new File(imgUrl), 130, 60);
        return imgUrl;
    }

    /**
     * 根据str,font的样式以及输出文件目录
     * @param str	    字符串
     * @param font	    字体
     * @param outFile	输出文件位置
     * @param width	    宽度
     * @param height    高度
     * @throws Exception
     */
    public static void createImage(String str, Font font, File outFile,
                                   Integer width, Integer height) throws Exception {
        // 创建图片
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_BGR);
        Graphics g = image.getGraphics();
        g.setClip(0, 0, width, height);
        // 背景颜色
        g.setColor(Color.WHITE);
        // 填充整张图片,也就是背景
        g.fillRect(0, 0, width, height);
        // 文字颜色
        g.setColor(Color.BLACK);
        // 设置画笔字体
        g.setFont(font);
        /** 用于获得垂直居中y */
        Rectangle clip = g.getClipBounds();
        FontMetrics fm = g.getFontMetrics(font);
        int ascent = fm.getAscent();
        int descent = fm.getDescent();
        int y = (clip.height - (ascent + descent)) / 2 + ascent;
        // 256 340 0 680
        for (int i = 0; i < 6; i++) {
            // 画出字符串
            g.drawString(str, i * 680, y);
        }
        g.dispose();
        // 输出png图片
        ImageIO.write(image, "jpg", outFile);
    }

    public static BufferedImage createImage(String str, Font font,
                                   Integer width, Integer height) throws Exception {
        // 创建图片
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_BGR);
        Graphics g = image.getGraphics();
        g.setClip(0, 0, width, height);
        // 背景颜色
        g.setColor(Color.WHITE);
        // 填充整张图片,也就是背景
        g.fillRect(0, 0, width, height);
        // 文字颜色
        g.setColor(Color.BLACK);
        // 设置画笔字体
        g.setFont(font);
        /** 用于获得垂直居中y */
        Rectangle clip = g.getClipBounds();
        FontMetrics fm = g.getFontMetrics(font);
        int ascent = fm.getAscent();
        int descent = fm.getDescent();
        int y = (clip.height - (ascent + descent)) / 2 + ascent;
        // 256 340 0 680
        for (int i = 0; i < 6; i++) {
            // 画出字符串
            g.drawString(str, i * 680, y);
        }

        g.dispose();
        // 输出png图片
        return image;
    }

    public static BufferedImage get1() throws IOException
    {
        Random random=new Random();
//	            默认背景为黑色
        BufferedImage image=new BufferedImage(500,200,BufferedImage.TYPE_INT_RGB);
//		获取画笔
        Graphics graphics = image.getGraphics();
//		默认填充为白色
        graphics.fillRect(0,0, 500, 200);
//	            验证码是由	数字 字母 干扰线 干扰点组成
//		文字素材
        String words = WordUtil.getWord(new int[]{1,2,3});
        System.out.println(words);
//        String words="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        char[] cs = words.toCharArray();
//		一般验证码为4位数
//      字母+数字
        for(int i=0;i<cs.length;i++) {
//			设置随机的颜色
            graphics.setColor(new Color(random.nextInt(256),random.nextInt(256),random.nextInt(256)));
            graphics.setFont(new Font("微软雅黑",Font.BOLD,30));
            char c=cs[i];
            System.out.println(c);
            graphics.drawString(c+"", i*30, 20);
        }
//		画干扰线
        int max=random.nextInt(100);
        for(int i=0;i<max;i++) {
            graphics.setColor(new Color(random.nextInt(256),random.nextInt(256),random.nextInt(256)));
            graphics.drawLine(random.nextInt(100),random.nextInt(50), random.nextInt(100), random.nextInt(50));
        }
//		画干扰点
        int max2=random.nextInt(100);
        for(int i=0;i<max2;i++) {
            graphics.setColor(new Color(random.nextInt(256),random.nextInt(256),random.nextInt(256)));
            graphics.drawOval(random.nextInt(80), random.nextInt(40), random.nextInt(5), random.nextInt(10));
        }
//        ImageIO.write(image, "jpg", new File("D:\\验证码.jpg"));
        return image;
    }
}