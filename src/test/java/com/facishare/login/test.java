package com.facishare.login; /**
 * Created by sunsk on 2017/3/1.
 */
//实例1 httppost and httpGet

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.facishare.openapi.http.httpCline;
import com.facishare.openapi.vo.RequestVo;
import com.facishare.openapi.vo.ResponseVo;
import org.testng.annotations.Test;

import java.util.HashMap;

public class test {
    httpCline hc=new httpCline();


    @Test
    public void login(){
        String requestUrl ="http://www.ceshi112.com/FHH/EM0HXUL/Authorize/Login";
        HashMap<String ,Object> requestHeads=new HashMap();
        requestHeads.put("Content-Type","application/json");
        requestHeads.put("Accept","application/json");

        loginBean loginB =new loginBean();
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
    }

}
