package com.facishare.openapi.workflow;

import com.alibaba.fastjson.JSONObject;
import com.facishare.openapi.BaseTest;
import com.facishare.openapi.common.DefinitionDetailArg;
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
 * Created by sunsk on 2017/8/8.
 */
public class GetDefinitionDetailTest extends BaseTest {
    //查询查询工作流详情页


    public WorkflowGeneral workflow =new WorkflowGeneral();
    DefinitionDetailArg definitionDetailArg =new DefinitionDetailArg();

    @BeforeClass
    public void initClass(){
        requestUrl =url+ workflowUrl.GetDefinitionDetail;

        requestHeads.put("Accept","application/json");
    }
    @BeforeMethod
    public void initMethod(){

        requestHeads.put("Cookie",Cookie);
    }
    @Test
    public void ordinaryTest(){
        //普通人员无法查询流程详情
        workFlowArg=workflow.workflowCreateRandom();
        System.err.println(workFlowArg);

        requestHeads.put("Cookie",getCookie("pt"));
        definitionDetailArg.setSourceWorkflowId(workFlowArg.getSourceWorkflowId());
        json = JSONObject.parseObject(JsonUtil.toJson(definitionDetailArg));

        requestVo =new RequestVo(requestUrl,json,requestHeads);
        responseVo=hc.httpPost(requestVo);
        json=responseVo.getJson().getJSONObject("Result");
        Assert.assertEquals(json.getIntValue("StatusCode"), 6,  "查询流程详情");
        Assert.assertEquals(json.getString("FailureMessage"), "您无该操作的执行权限",  "查询流程详情");
        workflow.deleteDefinition(workFlowArg.getSourceWorkflowId());

    }
    @Test
    public void adminTest(){
        //超级管理员可以完成
        workFlowArg=workflow.workflowCreateRandom();
        System.err.println(workFlowArg);


        definitionDetailArg.setSourceWorkflowId(workFlowArg.getSourceWorkflowId());
        json = JSONObject.parseObject(JsonUtil.toJson(definitionDetailArg));

        requestVo =new RequestVo(requestUrl,json,requestHeads);
        responseVo=hc.httpPost(requestVo);
        json=responseVo.getJson().getJSONObject("Result");
        Assert.assertEquals(json.getIntValue("StatusCode"), 0,  "查询流程详情");

        workflow.deleteDefinition(workFlowArg.getSourceWorkflowId());

    }
}
