package com.facishare.openapi.workflow;

import com.alibaba.fastjson.JSONObject;
import com.facishare.openapi.BaseTest;
import com.facishare.openapi.common.GetApiNameArg;
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
public class GetApiNameTest extends BaseTest{
    //查询APIName是否存在
    public WorkflowGeneral workflow =new WorkflowGeneral();

    GetApiNameArg getApiNameArg=new GetApiNameArg();
    @BeforeClass
    public void initClass(){



    }
    @BeforeMethod
    public void initMethod(){
        System.err.println(url+"before-----------");
        requestUrl =url+ workflowUrl.GetApiName;
        requestHeads.put("Accept","application/json");
        requestHeads.put("Cookie",Cookie);
    }
    @Test
    public void adminTest(){
        //管理员可以查询apiname，新增的，停用的均查询，删除和不存在的无法查询
        workFlowArg=workflow.workflowCreateRandom();
        System.err.println(workFlowArg);

        getApiNameArg.setSourceWorkflowId(workFlowArg.getSourceWorkflowId());

        json = JSONObject.parseObject(JsonUtil.toJson(getApiNameArg));

        requestVo =new RequestVo(requestUrl,json,requestHeads);
        responseVo=hc.httpPost(requestVo);
        Assert.assertEquals(responseVo.getJson().getJSONObject("Result").getIntValue("StatusCode"), 0, "查询流程");
        //新增的账号apiname存在

        Assert.assertEquals(responseVo.getJson().getJSONObject("Value").getBooleanValue("exist"), true, "新增的账号apiname存在");
        //停用的工作流apiname存在
        workflow.enableDefinition(workFlowArg.getSourceWorkflowId());
        responseVo=hc.httpPost(requestVo);
        Assert.assertEquals(responseVo.getJson().getJSONObject("Value").getBooleanValue("exist"), true, "停用的工作流apiname存在");
        //删除的流程apiname不存在
        workflow.deleteDefinition(workFlowArg.getSourceWorkflowId());
        responseVo=hc.httpPost(requestVo);
        Assert.assertEquals(responseVo.getJson().getJSONObject("Value").getBooleanValue("exist"), false, "删除的流程apiname不存在");
        //不存在的apiname无法查询
        getApiNameArg.setSourceWorkflowId(workFlowArg.getSourceWorkflowId()+1);
        json = JSONObject.parseObject(JsonUtil.toJson(getApiNameArg));
        requestVo =new RequestVo(requestUrl,json,requestHeads);
        responseVo=hc.httpPost(requestVo);
        Assert.assertEquals(responseVo.getJson().getJSONObject("Value").getBooleanValue("exist"), false, "不存在的apiname无法查询");
    }
    @Test
    public void ordinarytest(){
        //普通人员查询apiname

        workFlowArg=workflow.workflowCreateRandom();
        System.err.println(workFlowArg);

        requestHeads.put("Cookie",getCookie("pt"));

        getApiNameArg.setSourceWorkflowId(workFlowArg.getSourceWorkflowId());

        json = JSONObject.parseObject(JsonUtil.toJson(getApiNameArg));

        requestVo =new RequestVo(requestUrl,json,requestHeads);
        responseVo=hc.httpPost(requestVo);
        Assert.assertEquals(responseVo.getJson().getJSONObject("Result").getIntValue("StatusCode"), 0, "查询流程");
        Assert.assertEquals(responseVo.getJson().getJSONObject("Value").getBooleanValue("exist"), true, "新增的账号apiname存在");

        workflow.deleteDefinition(workFlowArg.getSourceWorkflowId());

    }
}
