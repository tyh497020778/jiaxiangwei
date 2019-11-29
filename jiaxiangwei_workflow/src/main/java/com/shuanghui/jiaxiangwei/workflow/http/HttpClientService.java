package com.shuanghui.jiaxiangwei.workflow.http;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.*;

@Service
public class HttpClientService {
    public Logger logger = LoggerFactory.getLogger(HttpClientService.class);
    public  static final  String OPERATOR_ID="MA29446L1";
    public  static final  String URL_PREFIX="https://dev-adapter-evcos.wm-getngo.com/openapi/charging/cec/uniev/";

    public  String httpPost(String url, Map<String,Object> map, String encoding,Boolean isAuthorization){
        logger.info("发送http 请求 URL:{} 参数:{}",url,JSON.toJSONString(map));
        CloseableHttpClient httpClient = null;
        HttpPost httpPost = null;
        String result= null;
        try{
            httpClient = HttpClients.createDefault();
            httpPost = new HttpPost(url);
            if(isAuthorization){
                httpPost.addHeader("Authorization",this.getToken());
                httpPost.addHeader("Content-Type", "application/json");
            }
            StringEntity entity = new StringEntity(JSON.toJSONString(map),encoding);
            entity.setContentType("application/json");
            entity.setContentEncoding("UTF-8");
            httpPost.setEntity(entity);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            if(httpResponse!=null){
                HttpEntity entity1 = httpResponse.getEntity();
                if(entity1!=null){
                    result = EntityUtils.toString(entity1,encoding);
                }
            }
            logger.info("获取http 请求 URL:{} 参数:{},结果:{}",url,JSON.toJSONString(map),result);
        }catch (Exception e){
            logger.error("发送http 请求失败 URL:{} 参数:{}",url,map);
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取SOC和GPS信息
     * @param cartNo
     * @param connectorId
     * @param reqTime
     * @return
     */
    public  Map<String,Object>  getSocAndGpsInfo(String cartNo,String connectorId,Long reqTime){
        String result = null;
        //消息体
        try{
            String TimeStamp=Dateutil.getYyyyMMddHHmmss();
            String Seq="0001";

            Map<String,Object> map = Maps.newHashMap();
            map.put("carNo",cartNo);
            map.put("ConnectorID",connectorId);
            map.put("reqTime",reqTime);
            //DataSecret加密
            String Data = AES.encrypt(JSON.toJSONString(map),AES.DATA_SECRET,AES.DATA_SECRET);
            StringBuffer buffer = new StringBuffer();
            buffer.append("OperatorId="+OPERATOR_ID);
            buffer.append("+Data="+Data);
            buffer.append("+TimeStamp="+TimeStamp);
            buffer.append("+Seq="+Seq);
            //SigSecret加密
            String sig = AES.byteArrayToHexString(AES.encryptHMAC(buffer.toString().getBytes(),AES.SIG_SECREET));

            String url = URL_PREFIX+"authentication_plug_and_charge";
            Map<String,Object> param_map = new HashMap<>();
            param_map.put("Data",Data);
            param_map.put("OperatorID",OPERATOR_ID);
            param_map.put("Seq",Seq);
            param_map.put("Sig",sig);
            param_map.put("TimeStamp",TimeStamp);
            result = httpPost(url,param_map,"UTF-8",true);
            logger.info("源报文 :{}",result);
            if(!StringUtils.isEmpty(result)){
                Map<String,Object> dataMap =(Map<String,Object>)JSON.parse(result);
                Boolean tokenFail = Boolean.valueOf(String.valueOf(dataMap.get("tokenFail")));
                if(tokenFail){
                    logger.error("调用威马服务获取Soc和Gps 失败,{}",dataMap.get("Msg"));
                    return null;
                }
                //数据解析
                return (Map<String,Object>)JSON.parse(AES.decrypt(String.valueOf(dataMap.get("Data")),AES.DATA_SECRET,AES.DATA_SECRET));
            }
        }catch (Exception e){
            logger.error("调用威马服务获取Soc和Gps 失败原因",e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取token
     * @return
     */
    public String getToken(){
        Map<String,Object> map = Maps.newHashMap();
        map.put("OperatorSecret",AES.OPERATOR_SECRET);
        map.put("OperatorId",OPERATOR_ID);
        String url = URL_PREFIX+"query_token";
        String result = httpPost(url,map,"UTF-8",false);
        if(!StringUtils.isEmpty(result)){
            Map<String,Object> dataMap =(Map<String,Object>)JSON.parse(result);
            Boolean tokenFail = Boolean.valueOf(String.valueOf(dataMap.get("tokenFail")));
            if(tokenFail){
                logger.error("调用威马服务获取token 失败,{}",dataMap.get("Msg"));
                return null;
            }
            //token解码
            Map<String,Object> tokenMap =(Map<String,Object>)JSON.parse(AES.decrypt(String.valueOf(dataMap.get("Data")),AES.DATA_SECRET,AES.DATA_SECRET));
            int SuccStat = Integer.valueOf(String.valueOf(tokenMap.get("SuccStat")));
            if(SuccStat==1){
                logger.error("调用威马服务获取token 解析失败,Data:{} 原因:{}",dataMap.get("Data"),tokenMap.get("FailReason"));
                return null;
            }
            result = String.valueOf(tokenMap.get("AccessToken"));
        }
        return result;
    }


    public  static void main(String[] args){
        try{
            HttpClientService httpClientService = new HttpClientService();
            System.out.println(httpClientService.getSocAndGpsInfo("abcdefghijklmnopqrest","1111111120191015114321227706",System.currentTimeMillis()));
//            System.out.println(httpClientService.getToken());
//            System.out.println(AES.decrypt("uGGt92xrDKbGCSUY/E38SCJ/qPFUJMGqOfaKgU04wK51pQPjGR+bOTkAGvqXvxS8",AES.DATA_SECRET,AES.DATA_SECRET));
        }catch (Exception e){
            e.printStackTrace();
        }


    }





}
