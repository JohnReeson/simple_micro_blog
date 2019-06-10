package cn.cslg.microblog.Util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javax.imageio.ImageIO;

public class VerifyCode extends HttpServlet {

	private static final long serialVersionUID = -775699744475915178L;
	private Font mFont = new Font("Times New Roman", Font.PLAIN, 18); // 设置字体

	// 处理post
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		session.removeAttribute("realVerifyCode");

		response.setContentType("image/jpeg");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);

		String code = getCode();
		session.setAttribute("realVerifyCode", code);

		ServletOutputStream out = response.getOutputStream();//指向客户端的输出流对象
		ImageIO.write(getImage(code), "jpg", out);
		out.close();
	}

	private BufferedImage getImage(String code){
		int width = 4 * 15, height = 20;//size
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB); // 设置图片大小的
		Graphics graphics = image.getGraphics();
		
		graphics.setColor(getRandColor(200, 250)); // 设置背景色
		graphics.fillRect(0, 0, width, height);

		graphics.setColor(Color.black); // 设置字体色
		graphics.setFont(mFont);

		graphics.setColor(new Color(0));
		graphics.drawRect(0, 0, width - 1, height - 1);

		Random random = new Random();
		random.setSeed(System.currentTimeMillis());
		// 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
		graphics.setColor(getRandColor(120, 200));
		for (int i = 0; i < 155; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(25);
			int yl = random.nextInt(25);
			graphics.drawLine(x, y, x + xl, y + yl);
		}

		for (int i = 0; i < 4; i++) {
			String rand = "" + code.charAt(i);
			// 将认证码显示到图象中
			graphics.setColor(new Color(20 + random.nextInt(110), 20 + random
					.nextInt(110), 20 + random.nextInt(110)));
			// 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
			graphics.drawString(rand, 13 * i + 6, 16);
		}
		return image;
	}

	private static Color getRandColor(int fc, int bc) { // 给定范围获得随机颜色
		Random random = new Random();
		random.setSeed(System.currentTimeMillis());
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

	private static String getCode(){
		StringBuffer s = new StringBuffer("");
		Random rand=new Random();
		rand.setSeed(System.currentTimeMillis());
		int x = 0;

		for(int i=0; i<4; i++){
			int flag = rand.nextInt(2);
			// 数字
			if(flag == 0){
				x = rand.nextInt(10) + 48;
			}
			// 大写字母
			else{
                x = rand.nextInt(26) + 65;
			}
			s.append((char)x);
		}
		return s.toString();
	}

}
