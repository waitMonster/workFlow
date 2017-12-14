package com.facishare.openapi;

import com.alibaba.fastjson.JSONObject;
import com.facishare.login.Userlogin;
import com.facishare.openapi.common.WorkFlowArg;
import com.facishare.openapi.common.workflowUrl;
import com.facishare.openapi.http.httpCline;
import com.facishare.openapi.model.WorkflowVo;
import com.facishare.openapi.vo.RequestVo;
import com.facishare.openapi.vo.ResponseVo;
import com.facishare.rest.proxy.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.test.context.ContextConfiguration;
import com.facishare.openapi.workflow.WorkflowGeneral;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by cuiyongxu on 17/8/4.
 */
@Slf4j
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class BaseTest extends Userlogin{



    public httpCline hc =new httpCline();
    public String url;
            //=getRequestUrl();
    public RequestVo requestVo;
    public JSONObject json=null;

    public String requestUrl="";
    public String sourceWorkflowId="";

    public HashMap<String ,Object> requestHeads=new HashMap();

    public ResponseVo responseVo;

    public WorkFlowArg workFlowArg =new WorkFlowArg();


    @BeforeMethod
    public void initMethod(){
        System.err.println(url+"before1-----------");
        requestUrl =url+ workflowUrl.GetApiName;
        requestHeads.put("Accept","application/json");
        requestHeads.put("Cookie",Cookie);
    }
    public WorkflowVo getWorkflowVo(String fileName) {

        try {
            return JsonUtil.fromJson(
                    IOUtils.toString(getClass().getClassLoader().getResourceAsStream("data/" + fileName)).replaceAll(" ", "")
                    , WorkflowVo.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }
    @Test()
    public  void test(){
        System.err.println(url);

    }

}
