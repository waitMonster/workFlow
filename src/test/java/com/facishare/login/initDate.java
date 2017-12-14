package com.facishare.login;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.facishare.openapi.common.fileUntil;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 * Created by sunsk on 2017/10/25.
 */
public class initDate {

    String TestEnv;
    String url=getRequestUrl();


    @BeforeClass
    public void loginint(ITestContext iTestContext){
        TestEnv=iTestContext.getCurrentXmlTest().getParameter("TestEnv").toString();




    }

    public String getRequestUrl(){

        System.err.println(this+"---------------------------------");
        String fileUrl= fileUntil.getCurrentWorkDir()+TestEnv+".json";
        System.err.println(fileUrl+"---------------------------------");

        // TODO: 2017/10/25 写死 可以去掉 为了调试方便
        fileUrl=fileUntil.getCurrentWorkDir()+"ceshi112.json";
        try{

            JSONObject jsonObject= JSON.parseObject(fileUntil.read(fileUrl));
            this.url=jsonObject.get("url").toString();
            System.err.println(jsonObject.get("url")+"---------------------------------");
            System.err.println(JSON.parseObject(jsonObject.get("user").toString()).get("admin")+"---------------------------------");

        }catch ( Exception e){

        }
        return url;

    }
    @Test
public void test(){
    System.err.println(url+"---------------------------------");

}
}
