package com.shuanghui.jiaxiangwei.workflow.http;

import javax.swing.text.NumberFormatter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;

public class SeqUtil {
    public static  int i=0;
    public static Date date;
    public static void main(String[] args) {
        try {
            DecimalFormat format= new DecimalFormat("0000");
            date = new Date();
            Date date1 = new Date();
            //超过一秒
            while((new Date().getTime()-date.getTime())/1000<1){
                i=i+1;
                System.out.println(format.format(i));
            }
            i=1;
        }catch (Exception e){

        }



    }
}
