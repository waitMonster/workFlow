package com.facishare.openapi.workflow;

import com.alibaba.fastjson.JSONObject;
import com.facishare.openapi.BaseTest;
import com.facishare.openapi.common.GetAfterActionsArg;
import com.facishare.openapi.common.GetInstancesArg;
import com.facishare.openapi.common.workflowUrl;
import com.facishare.openapi.vo.RequestVo;
import com.facishare.rest.proxy.util.JsonUtil;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by sunsk on 2017/10/17.
 */
public class GetAfterActionsTest extends BaseTest {
    //查询工作流后动作
    // TODO: 2017/10/17  6.0版本工作流后动作 功能不支持 此接口并不完善不做过多测试--1.排序，权限
    public WorkflowGeneral workflow =new WorkflowGeneral();

    GetAfterActionsArg getAfterActionsArg =new GetAfterActionsArg();
    @BeforeClass
    public void initClass(){
        requestUrl =url+ workflowUrl.GetAfterActions;

        requestHeads.put("Accept","application/json");
    }
    @BeforeMethod
    public void initMethod(){

        requestHeads.put("Cookie",Cookie);
    }
    @Test
    public void adminTest(){
        //超级管理员可以进行流程后动作的查询
        workFlowArg=workflow.workflowCreateRandom();
        System.err.println(workFlowArg);


        getAfterActionsArg.setSourceWorkflowId(workFlowArg.getSourceWorkflowId());
        getAfterActionsArg.getOrderBy();
        getAfterActionsArg.setKeyWord(workFlowArg.getName());
        json = JSONObject.parseObject(JsonUtil.toJson(getAfterActionsArg));

        requestVo =new RequestVo(requestUrl,json,requestHeads);
        responseVo=hc.httpPost(requestVo);
        Assert.assertEquals(responseVo.getJson().getJSONObject("Result").getIntValue("StatusCode"), 0, "查询流程");
        Assert.assertEquals(responseVo.getJson().getJSONObject("Value").getIntValue("total"), 1, "后动作个数为1");

        workflow.deleteDefinition(workFlowArg.getSourceWorkflowId());

    }
    @Test
    public void ordinarytest(){
        //普通人员查询流程实例

        workFlowArg=workflow.workflowCreateRandom();
        System.err.println(workFlowArg);

        requestHeads.put("Cookie",getCookie("pt"));

        getAfterActionsArg.setSourceWorkflowId(workFlowArg.getSourceWorkflowId());
        getAfterActionsArg.getOrderBy();
        getAfterActionsArg.setKeyWord(workFlowArg.getName());
        json = JSONObject.parseObject(JsonUtil.toJson(getAfterActionsArg));

        requestVo =new RequestVo(requestUrl,json,requestHeads);
        responseVo=hc.httpPost(requestVo);
        Assert.assertEquals(responseVo.getJson().getJSONObject("Result").getIntValue("StatusCode"), 0, "查询流程");
        Assert.assertEquals(responseVo.getJson().getJSONObject("Value").getIntValue("total"), 1, "流程实例为0");

//        json=responseVo.getJson().getJSONObject("Result");
//        Assert.assertEquals(json.getIntValue("StatusCode"), 6,  "禁用流程");
//        Assert.assertEquals(json.getString("FailureMessage"), "您无该操作的执行权限",  "禁用流程");
        workflow.deleteDefinition(workFlowArg.getSourceWorkflowId());

    }
}
