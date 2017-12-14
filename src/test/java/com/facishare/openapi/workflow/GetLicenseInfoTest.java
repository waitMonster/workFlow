package com.facishare.openapi.workflow;

import com.alibaba.fastjson.JSONObject;
import com.facishare.openapi.BaseTest;
import com.facishare.openapi.common.GetDefinitionsArg;
import com.facishare.openapi.common.WorkFlowArg;
import com.facishare.openapi.common.workflowUrl;
import com.facishare.openapi.vo.RequestVo;
import com.facishare.rest.proxy.util.JsonUtil;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by sunsk on 2017/10/16. //查询流程分版信息
 */
public class GetLicenseInfoTest extends BaseTest{
    //查询查询工作流定义列表

    public WorkflowGeneral workflow =new WorkflowGeneral();

    WorkFlowArg workFlowArg =new WorkFlowArg();
    int currentNumber1;
    int currentNumber2;
    @BeforeClass
    public void initClass(){
        requestUrl =url+ workflowUrl.GetLicenseInfo;
        json =JSONObject.parseObject("{}");
        requestHeads.put("Accept","application/json");

    }
    @BeforeMethod
    public void initMethod(){

        requestHeads.put("Cookie",Cookie);
    }
    @Test
    public void adminTest(){
        //超级管理员可以进行流程查询操作,新增一条数据，分版信息变化

        requestVo =new RequestVo(requestUrl,json,requestHeads);
        responseVo=hc.httpPost(requestVo);
        Assert.assertEquals(responseVo.getJson().getJSONObject("Result").getIntValue("StatusCode"), 0, "查询分版信息");
        currentNumber1=responseVo.getJson().getJSONObject("Value").getIntValue("currentNumber");

        //新增一条数据
        workFlowArg=workflow.workflowCreateRandom();
        System.err.println(workFlowArg);

        responseVo=hc.httpPost(requestVo);
        currentNumber2=responseVo.getJson().getJSONObject("Value").getIntValue("currentNumber");


        Assert.assertEquals(currentNumber2-currentNumber1, 1, "增加一条数据");
        //Assert.assertEquals(limitedNumber1-limitedNumber2, 0, "减少一条限制数据");

        workflow.deleteDefinition(workFlowArg.getSourceWorkflowId());

    }
    @Test
    public void ordinaryTest(){
        //普通人员无法查询流程定义

         requestHeads.put("Cookie",getCookie("pt"));

        requestVo =new RequestVo(requestUrl,json,requestHeads);
        responseVo=hc.httpPost(requestVo);
        Assert.assertEquals(responseVo.getJson().getJSONObject("Result").getIntValue("StatusCode"), 6, "查询流程");
        Assert.assertEquals(responseVo.getJson().getJSONObject("Result").getString("FailureMessage"), "您无该操作的执行权限",  "普通人员无法查询流程定义");


    }
// TODO: 2017/10/16  当限制增加条数满的话 不能继续增加流程，不做接口测试--功能测试验证通过
}
