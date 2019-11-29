package com.shuanghui.jiaxiangwei.util;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**工具类
 * Created by Administrator on 2015/3/27.
 */
public  class Util {
    /**
     * 非空判断
     * @param obj
     * @return
     */
    public static boolean checkNotNull(Object obj){
        if(obj!=null && !obj.equals("")){
            return true;
        }
        return false;
    }

    /**
     * 比较两个对象是否相同,不能比较数字吧
     * @param obj1
     * @param obj2
     * @return
     */
    public static boolean isEquals(Object obj1,Object obj2){
        if(Util.checkNotNull(obj1) && Util.checkNotNull(obj2) && !obj1.equals("") && !obj2.equals("")){
            if(obj1.equals(obj2)){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    /**
     *
     * @param isTrue        查询结果 true,successMsg y
     * @param falseMsg      失败提醒消息
     * @param successMsg        成功提醒消息
     * @return
     */
    public static String getCheckMeg(boolean isTrue,String successMsg,String falseMsg){
        // 验证框架需要两个key：info和status(y/n),y标识通过验证,n标识验证不通过
        return  "{\"info\":\"" + (isTrue ? successMsg : falseMsg) + "\",\"status\":\"" + (isTrue ? 'y' : 'n') + "\"}";
    }

    /**
     *
     * @param realPath      服务器真实地址
     * @param path2          要保存文件的文件夹路径
     * @param aftername     后缀名
     * @param request
     * @param f                要上传的文件
     * @param filesroad        存入数据库的字段名
     * @return
     */
    public static String saveFile(String realPath,String path2,String aftername,HttpServletRequest request,MultipartFile f,String filesroad){
        //复制
        try {
            File file1=new File(realPath+path2);
            if(!file1.exists()){
                file1.mkdirs();
            }
            Long time=System.currentTimeMillis();
//            filesroad="Uploads"+path2+time+aftername;
            filesroad=path2+time+aftername;
            //拷贝并且上传
            FileUtils.copyInputStreamToFile(f.getInputStream(), new File(realPath+path2, time + aftername));
            return filesroad;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("http_client_ip");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }

        // 如果是多级代理，那么取第一个ip为客户ip
        if (ip != null && ip.indexOf(",") != -1) {
            ip = ip.substring(ip.lastIndexOf(",") + 1, ip.length()).trim();
        }

        if (isBoolIp(ip)) return ip;
        return "";
    }

    public static boolean isBoolIp(String ipAddress) {
        Pattern pattern = Pattern.compile("\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b");
        Matcher matcher = pattern.matcher(ipAddress);
        return matcher.matches();
    }

    public static boolean isPhoneNumber(String phone) {
        String telRegex = "(^1[3|4|5|7|8][0-9]{9}$)";
        // String telRegex2 = "^1(3[0-9]|4[57]|5[0-35-9]|8[0-9]|70)\\d{8}$";
        return phone.matches(telRegex);
    }
}

