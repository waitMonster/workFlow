package com.facishare.openapi.workflow;

import com.alibaba.fastjson.JSONObject;
import com.facishare.openapi.BaseTest;
import com.facishare.openapi.common.GetInstancesArg;
import com.facishare.openapi.common.workflowUrl;
import com.facishare.openapi.vo.RequestVo;
import com.facishare.rest.proxy.util.JsonUtil;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by sunsk on 2017/10/16.
 */
public class GetInstancesTest extends BaseTest {
    //查询工作流实例列表
    // TODO: 2017/10/17  6.0版本流程实例 功能不支持 此接口并不完善不做过多测试--1.排序，权限
    public WorkflowGeneral workflow =new WorkflowGeneral();
    GetInstancesArg getInstancesArg =new GetInstancesArg();
    @BeforeClass
    public void initClass(){
        requestUrl =url+ workflowUrl.GetInstances;

        requestHeads.put("Accept","application/json");
    }
    @BeforeMethod
    public void initMethod(){

        requestHeads.put("Cookie",Cookie);
    }
    @Test
    public void adminTest(){
        //超级管理员可以进行工作流实例查询，并且新建的流程没有工作流实例
        workFlowArg=workflow.workflowCreateRandom();
        System.err.println(workFlowArg);

        // requestHeads.put("Cookie",getCookie("pt"));
        getInstancesArg.getOrderBy();
        getInstancesArg.setSourceWorkflowId(workFlowArg.getSourceWorkflowId());
        getInstancesArg.setKeyWord(workFlowArg.getName());
        json = JSONObject.parseObject(JsonUtil.toJson(getInstancesArg));

        requestVo =new RequestVo(requestUrl,json,requestHeads);
        responseVo=hc.httpPost(requestVo);
        Assert.assertEquals(responseVo.getJson().getJSONObject("Result").getIntValue("StatusCode"), 0, "查询流程");
        Assert.assertEquals(responseVo.getJson().getJSONObject("Value").getIntValue("total"), 0, "流程实例为0");

        // TODO: 2017/10/17 发起一个流程之后，流程实例添加不做验证
        workflow.deleteDefinition(workFlowArg.getSourceWorkflowId());

    }
    @Test
    public void ordinarytest(){
        //普通人员查询流程实例

        workFlowArg=workflow.workflowCreateRandom();
        System.err.println(workFlowArg);

         requestHeads.put("Cookie",getCookie("pt"));
        getInstancesArg.getOrderBy();
        getInstancesArg.setSourceWorkflowId(workFlowArg.getSourceWorkflowId());
        getInstancesArg.setKeyWord(workFlowArg.getName());
        json = JSONObject.parseObject(JsonUtil.toJson(getInstancesArg));

        requestVo =new RequestVo(requestUrl,json,requestHeads);
        responseVo=hc.httpPost(requestVo);
        Assert.assertEquals(responseVo.getJson().getJSONObject("Result").getIntValue("StatusCode"), 0, "查询流程");
        Assert.assertEquals(responseVo.getJson().getJSONObject("Value").getIntValue("total"), 0, "流程实例为0");

//        json=responseVo.getJson().getJSONObject("Result");
//        Assert.assertEquals(json.getIntValue("StatusCode"), 6,  "禁用流程");
//        Assert.assertEquals(json.getString("FailureMessage"), "您无该操作的执行权限",  "禁用流程");
        workflow.deleteDefinition(workFlowArg.getSourceWorkflowId());

    }
}
