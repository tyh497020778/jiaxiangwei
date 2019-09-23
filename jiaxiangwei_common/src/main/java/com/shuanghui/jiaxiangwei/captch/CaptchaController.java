package com.shuanghui.jiaxiangwei.captch;

/**
 * Created by tyh on 2015/3/18.
 */

import com.shuanghui.jiaxiangwei.util.CaptchaUtil;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 验证码
 */
@Controller
@RequestMapping(value = "/captcha")
public class CaptchaController {
    public static final String KEY_CAPTCHA = "SE_KEY_MM_CODE";

    @RequestMapping(value = "/captchaCode", method = RequestMethod.GET)
    protected void getImage(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // 设置相应类型,告诉浏览器输出的内容为图片
        resp.setContentType("image/jpeg");
        // 不缓存此内容
        resp.setHeader("Pragma", "No-cache");
        resp.setHeader("Cache-Control", "no-cache");
        resp.setDateHeader("Expire", 0);
        try {

            HttpSession session = req.getSession();

            CaptchaUtil tool = new CaptchaUtil();
            StringBuffer code = new StringBuffer();
            BufferedImage image = tool.genRandomCodeImage(code);
            session.removeAttribute(KEY_CAPTCHA);
            session.setAttribute(KEY_CAPTCHA, code.toString());

            // 将内存中的图片通过流动形式输出到客户端
            ImageIO.write(image, "JPEG", resp.getOutputStream());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @RequestMapping(value = "/ajaxgetImage", method = RequestMethod.POST)
    @ResponseBody
    protected Boolean ajaxgetImage(HttpServletRequest req,HttpSession session,String captcha) throws ServletException, IOException {
        boolean isTrue = false;
        if(StringUtils.isEmpty(captcha) && captcha.equalsIgnoreCase((String)session.getAttribute(KEY_CAPTCHA))){
            isTrue = true;
        }
        return isTrue;
    }


//    /**
//     * 获取短信验证码
//     *
//     * @param captcha
//     * @return
//     */
//    @RequestMapping(value = "/smsCaptchaCode",method = RequestMethod.POST)
//    @ResponseBody
//    public boolean mobilecode(String captcha, HttpSession session) {
//        boolean isTrue = false;
//        try {
//            if (StringUtils.isEmpty(captcha)  && ((String) session.getAttribute(SMSController.KEY_CAPTCHA)).equalsIgnoreCase(captcha)) {
//                isTrue = true;
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return isTrue;
//    }



}
