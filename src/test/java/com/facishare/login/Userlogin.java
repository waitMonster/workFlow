package com.facishare.login; /**

 * Created by sunsk on 2017/3/1.
 */
//实例1 httppost and httpGet

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.facishare.openapi.http.httpCline;
import com.facishare.openapi.vo.RequestVo;
import com.facishare.openapi.vo.ResponseVo;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.ITestContext;


import com.facishare.openapi.common.fileUntil;

import java.util.ArrayList;
import java.util.HashMap;

public class Userlogin extends initDate {

    static HashMap<String, String> usersCookie = new HashMap<String, String>();
    HashMap<String ,Object> requestHeads=new HashMap();
    httpCline hc=new httpCline();
    //public String url= "https://www.ceshi112.com";
    String requestUrl =url+"/FHH/EM0HXUL/Authorize/Login";
    String TestEnv;
    loginBean loginB =new loginBean();


    public String Cookie=getCookie("admin");


   // initDate initDate =new initDate();

    @BeforeClass
    public void loginint(ITestContext iTestContext){
        TestEnv=iTestContext.getCurrentXmlTest().getParameter("TestEnv").toString();




    }


    public String  adminlogin(){
        requestHeads.put("Content-Type","application/json");
        requestHeads.put("Accept","application/json");
        loginB.setEnterpriseAccount("57612");
        loginB.setUserAccount("18210911798");
        //loginB.setUserAccount("11212345677");
        loginB.setPassword("a123456");
        loginB.setImgCode("");
        loginB.setPersistenceHint("true");
        loginB.setClientId("undefined");

        JSONObject json= JSON.parseObject(JSONObject.toJSONString(loginB));
        System.out.println(json);

        RequestVo requestVo =new RequestVo(requestUrl,json,requestHeads);
        ResponseVo responseVo=hc.httpPost(requestVo);
        System.err.println(responseVo.getCookies().get(1).getValue());
        return  "FSAuthXC="+responseVo.getCookies().get(1).getValue();
    }
    public String  Ptlogin(){
        requestHeads.put("Content-Type","application/json");
        requestHeads.put("Accept","application/json");
        loginB.setEnterpriseAccount("57612");
        //loginB.setUserAccount("18210911798");
        loginB.setUserAccount("11212345677");
        loginB.setPassword("a123456");
        loginB.setImgCode("");
        loginB.setPersistenceHint("true");
        loginB.setClientId("undefined");

        JSONObject json= JSON.parseObject(JSONObject.toJSONString(loginB));
        System.out.println(json);

        RequestVo requestVo =new RequestVo(requestUrl,json,requestHeads);
        ResponseVo responseVo=hc.httpPost(requestVo);
        System.err.println(responseVo.getCookies().get(1).getValue());
        return  "FSAuthXC="+responseVo.getCookies().get(1).getValue();
    }
    /**
     *  获取cookie
     *
     * @param userId
     */
    public String getCookie(String userId){

        if (usersCookie.get(userId)==null){
            if(userId=="admin"){
                usersCookie.put(userId,adminlogin());
            }
            if(userId=="pt"){

                usersCookie.put(userId,Ptlogin());
            }
        }
        return usersCookie.get(userId);
    }
//    @Test
//    public String getRequestUrl(){
//
//        String fileUrl=fileUntil.getCurrentWorkDir()+TestEnv+".json";
//        System.err.println(fileUrl+"---------------------------------");
//        //fileUrl=fileUntil.getCurrentWorkDir()+"ceshi112.json";
//        try{
//
//            JSONObject jsonObject=JSON.parseObject(fileUntil.read(fileUrl));
//            this.url=jsonObject.get("url").toString();
//            System.err.println(jsonObject.get("url")+"---------------------------------");
//            System.err.println(JSON.parseObject(jsonObject.get("user").toString()).get("admin")+"---------------------------------");
//
//        }catch ( Exception e){
//
//        }
//        return url;
//    }
    @Parameters("TestEvn")
    //@Test
    public void getRequestUrl1(String TestEnv){
        //String TestEnv=iTestContext.getCurrentXmlTest().getParameter("TestEnv").toString();
        String fileUrl=fileUntil.getCurrentWorkDir()+TestEnv+".json";
        System.err.println(fileUrl+"---------------------------------");
        //fileUrl=fileUntil.getCurrentWorkDir()+"ceshi112.json";
        try{

            JSONObject jsonObject=JSON.parseObject(fileUntil.read(fileUrl));
            this.url=jsonObject.get("url").toString();
            System.err.println(jsonObject.get("url")+"---------------------------------");
            System.err.println(JSON.parseObject(jsonObject.get("user").toString()).get("admin")+"---------------------------------");

        }catch ( Exception e){

        }
        //return url;
    }
    @Test
    public void test(){
        //String ab;
       System.err.println(url);
        // System.err.println(getCookie("admin"));
       // System.err.println(TestEnv+"---------------------------------");

    }

}
