package com.facishare.openapi.http; /**
 * Created by sunsk on 2017/3/1.
 */
//实例1 httppost and httpGet

import com.alibaba.fastjson.JSONObject;
import com.facishare.openapi.common.urlChange;
import com.facishare.openapi.vo.RequestVo;
import com.facishare.openapi.vo.ResponseVo;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.util.List;

public class httpCline {
    String url="";
    urlChange uc=new urlChange();
    ResponseVo responseVo;
    private JSONObject json = null;
    private String responseStr = "";
    private int httpCode = 0;

    private List<Cookie> cookies;
    private Integer fileSize = 0;
    private org.apache.http.Header[] headers;
//
//    public void httpGet(RequestVo requestVo) {
//        HttpClient client = new DefaultHttpClient();
//        url = requestVo.getRequestUrl()+"?";
//        url =uc.mapToStr(requestVo.getRequestParams(),url);
//        System.out.println(url);
//        CookieStore cookieStore = new BasicCookieStore();
//        HttpContext localContext = new BasicHttpContext();
//        localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
//        HttpGet get=new HttpGet(url);
//        responseVo=new ResponseVo();
//        try {
//            //添加文件头部
//            if (!requestVo.getRequestHeads().isEmpty()) {
//                for (String key : requestVo.getRequestHeads().keySet()) {
//                    get.addHeader(key,requestVo.getRequestHeads().get(key).toString());
//                }
//            }
//            //执行get语句
//            HttpResponse Response =client.execute(get,localContext);
//            //打印返回值
//            httpCode=Response.getStatusLine().getStatusCode();
//            System.out.println(httpCode);
//            responseVo.setHttpCode(httpCode);
//            //打印返回值
//            //EntityUtils
//            json=JSONObject.parseObject(EntityUtils.toString(Response.getEntity()));
//            System.out.println(json);
//            responseVo.setJson(json);
//            //打印head
//            responseVo.setCookies(cookieStore.getCookies());
//            System.out.println(cookieStore.getCookies());
//           // System.out.println(Response.getFirstHeader("Content-Type"));
//      //      ResponseVo responseVo =new ResponseVo();
//
//
//        }catch (Exception E){
//
//        }
//
//
//    }
    public ResponseVo httpPost(RequestVo requestVo)  {
        //HttpClient client = new createDefault();
        CloseableHttpClient client= HttpClients.createDefault();

        url = requestVo.getRequestUrl();
        System.out.println(url);
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(60000).setConnectTimeout(60000)
                .build();
        HttpPost post=new HttpPost(url);
        post.setConfig(requestConfig);
        CookieStore cookieStore = new BasicCookieStore();
        HttpContext localContext = new BasicHttpContext();
        localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
        //localContext.setAttribute("ke");
        responseVo=new ResponseVo();
        try{
            if (!requestVo.getRequestHeads().isEmpty()) {
                for (String key : requestVo.getRequestHeads().keySet()) {
                    post.addHeader(key,requestVo.getRequestHeads().get(key).toString());
                }
            }
            // 创建参数队列
//            List<NameValuePair> formparams = new ArrayList<NameValuePair>();
//            for (String key : requestVo.getRequestParams().keySet()) {
//                formparams.add(new BasicNameValuePair(key, requestVo.getRequestParams().get(key).toString()));
//
//            }
//            System.out.println(formparams);
//            UrlEncodedFormEntity uefEntity;
//            UrlEncodedFormEntity formEntiry = new UrlEncodedFormEntity(formparams, "UTF-8");
//            post.setEntity(formEntiry);
            JSONObject httpbody;
      //      httpbody=JSON.parseObject(JSONObject.toJSONString(requestVo.getRequestParams()));
            httpbody=requestVo.getJsonParams();
            System.err.println(httpbody);
            post.setEntity(new StringEntity(httpbody.toJSONString(),"utf-8"));
            //HttpResponse
            HttpResponse Response =client.execute(post,localContext);
             httpCode=Response.getStatusLine().getStatusCode();
            System.out.println("code"+httpCode);
            responseVo.setHttpCode(httpCode);
            //打印返回值
            //EntityUtils
            json=JSONObject.parseObject(EntityUtils.toString(Response.getEntity()));
            System.out.println("返回值为"+json);
            responseVo.setJson(json);
            //打印head
            responseVo.setCookies(cookieStore.getCookies());
            System.out.println(cookieStore.getCookies());


        }catch(Exception e){
            System.out.println(e);
        }
        return responseVo;
    }


}
