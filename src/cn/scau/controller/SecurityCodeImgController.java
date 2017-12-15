package cn.scau.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.core.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONObject;

/**
 * 验证码生成工具
 * @author Unicorn
 *
 */
@Controller
public class SecurityCodeImgController {
	
	
	@RequestMapping(value="/securitycode")
	public String getImage(HttpSession session,HttpServletResponse response) throws Exception {
		/**图片的宽度*/
		int width = 120;
		/**图片的高度*/
		int height = 30;
		
		/**在内存区域开拓图片的缓存空间*/
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		/**创建图片*/
		Graphics graphics = bufferedImage.getGraphics();
		/**生成随机的颜色点干扰*/
		graphics.setColor(getRandColor(200, 250));
		graphics.fillRect(0, 0, width, height);
		
		/**生成随机的颜色方块干扰*/
		graphics.setColor(Color.WHITE);
		graphics.drawRect(0, 0, width-1, height-1);
		
		/**将图片转为2d样式*/
		Graphics2D graphics2d = (Graphics2D) graphics;
		/**设置字体*/
		graphics2d.setFont(new Font("方正幼圆", Font.BOLD, 20));
		
		String words = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
		
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		
		int x = 10;
		for (int i = 0; i < 4; i++) {
			graphics2d.setColor(new Color(20 + random.nextInt(110), 20 + random
					.nextInt(110), 20 + random.nextInt(110)));
			int jiaodu = random.nextInt(60) - 30;
			double theta = jiaodu * Math.PI / 180;
			int index = random.nextInt(words.length());
			char c = words.charAt(index);
			sb.append(c);
			graphics2d.rotate(theta,x,20);
			graphics2d.drawString(String.valueOf(c), x, 20);
			graphics2d.rotate(-theta, x, 20);
			x += 30;
		}
		/**将生成的字符串放入session*/
		session.setAttribute("_securitycode", sb.toString().toLowerCase());
		/**生成随机颜色的干扰线条*/
		graphics.setColor(getRandColor(160, 200));
		int x1;
		int x2;
		int y1;
		int y2;
		for (int i = 0; i < 30; i++) {
			x1 = random.nextInt(width);
			x2 = random.nextInt(12);
			y1 = random.nextInt(height);
			y2 = random.nextInt(12);
			graphics.drawLine(x1, y1, x1 + x2, x2 + y2);
		}
		/**生成图片并输出*/
		graphics.dispose();
		ImageIO.write(bufferedImage, "jpg", response.getOutputStream());
		return null;
	}
	
	/**
	 * 随机颜色
	 * 
	 * @param fc
	 *            int 
	 * @param bc
	 *            int 
	 * @return Color
	 */
	private Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255) {
			fc = 255;
		}
		if (bc > 255) {
			bc = 255;
		}
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}
	
	@RequestMapping("/validCode")
	@ResponseBody
	public String validCodeCheck(@RequestParam(value="code")String code ,HttpSession session){
		JSONObject json = new JSONObject();
		Boolean flag = false;
		if(null != session.getAttribute("_securitycode") && null != code)
			flag = code.toLowerCase().equals(session.getAttribute("_securitycode").toString().toLowerCase());
		json.put("isCorrect", flag ? "yes" : "no");
		return json.toString();
	} 
}
