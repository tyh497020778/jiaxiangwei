package com.shuanghui.jiaxiangwei.workflow.http;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Dateutil {

    //获取YyyyMMddHHmmss格式的日期
    public static  String getYyyyMMddHHmmss(){
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar calendar = Calendar.getInstance();
        return format.format(calendar.getTime());
    }

}
