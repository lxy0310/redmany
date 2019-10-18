package servlet;

import model.VerifyCode;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

public class ImageServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {

            int width=200;

            int height=69;

            BufferedImage verifyImg=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);

//生成对应宽高的初始图片

            String randomText = VerifyCode.drawRandomText(width,height,verifyImg);

//单独的一个类方法，出于代码复用考虑，进行了封装。

//功能是生成验证码字符并加上噪点，干扰线，返回值为验证码字符

            request.getSession().setAttribute("verifyCode", randomText);

            response.setContentType("image/png");//必须设置响应内容类型为图片，否则前台不识别

            OutputStream os = response.getOutputStream(); //获取文件输出流

            ImageIO.write(verifyImg,"png",os);//输出图片流

            os.flush();

            os.close();//关闭流

        } catch (IOException e) {

         //   this.logger.error(e.getMessage());

            e.printStackTrace();

        }

       /*  //这个方法实现验证码的生成
        BufferedImage bi=new BufferedImage(68, 22,BufferedImage.TYPE_INT_RGB);//创建图像缓冲区
        Graphics g=bi.getGraphics(); //通过缓冲区创建一个画布
        Color c=new Color(200,150,255); //创建颜色
        *//*根据背景画了一个矩形框
       *//*
        g.setColor(c);//为画布创建背景颜色
        g.fillRect(0, 0, 68,22); //fillRect:填充指定的矩形

        char[] ch="ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();//转化为字符型的数组
        Random r=new Random();
        int len=ch.length;
        int index; //index用于存放随机数字
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<4;i++)
            {
            index=r.nextInt(len);//产生随机数字
            g.setColor(new Color(r.nextInt(88),r.nextInt(188),r.nextInt(255)));  //设置颜色
            g.drawString(ch[index]+"",(i*15)+3, 18);//画数字以及数字的位置
            sb.append(ch[index]);
            }
        request.getSession().setAttribute("piccode",sb.toString()); //将数字保留在session中，便于后续的使用
        ImageIO.write(bi, "JPG", response.getOutputStream());*/
    }



}
