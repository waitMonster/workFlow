package com.facishare.openapi.workflow;

import com.alibaba.fastjson.JSONObject;
import com.facishare.openapi.BaseTest;
import com.facishare.openapi.common.WorkFlowArg;
import com.facishare.openapi.common.workflowUrl;
import com.facishare.openapi.vo.RequestVo;
import com.facishare.rest.proxy.util.JsonUtil;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.facishare.openapi.common.GetDefinitionsArg;

/**
 * Created by sunsk on 2017/8/8. //查询工作流列表
 */
public class GetDefinitionsTest extends BaseTest{
    //查询查询工作流定义列表

    GetDefinitionsArg getDefinitionsArg =new GetDefinitionsArg();
    public WorkflowGeneral workflow =new WorkflowGeneral();
    @BeforeClass
    public void initClass(){
        requestUrl =url+ workflowUrl.GetDefinitions;

        requestHeads.put("Accept","application/json");
    }
    @BeforeMethod
    public void initMethod(){

        requestHeads.put("Cookie",Cookie);
    }
    @Test
    public void adminTest(){
        //超级管理员可以进行流程查询操作
        workFlowArg=workflow.workflowCreateRandom();
        System.err.println(workFlowArg);

       // requestHeads.put("Cookie",getCookie("pt"));
        getDefinitionsArg.setEnable("false");
        getDefinitionsArg.setEntityId(workFlowArg.getEntityId());
        getDefinitionsArg.setKeyWord(workFlowArg.getName());
        json = JSONObject.parseObject(JsonUtil.toJson(getDefinitionsArg));

       requestVo =new RequestVo(requestUrl,json,requestHeads);
        responseVo=hc.httpPost(requestVo);
        Assert.assertEquals(responseVo.getJson().getJSONObject("Result").getIntValue("StatusCode"), 0, "查询流程");
        Assert.assertEquals(responseVo.getJson().getJSONObject("Value").getJSONArray("datas").getJSONObject(0).getString("sourceWorkflowId"), workFlowArg.getSourceWorkflowId(), "流程ID");
        Assert.assertEquals(responseVo.getJson().getJSONObject("Value").getJSONArray("datas").getJSONObject(0).getString("name"), workFlowArg.getName(), "流程名称");
        Assert.assertEquals(responseVo.getJson().getJSONObject("Value").getJSONArray("datas").getJSONObject(0).getString("entityId"), workFlowArg.getEntityId(), "流程对象");
        workflow.deleteDefinition(workFlowArg.getSourceWorkflowId());

    }
    @Test
    public void ordinaryTest(){
        //普通人员无法查询流程定义
        workFlowArg=workflow.workflowCreateRandom();
        System.err.println(workFlowArg);

         requestHeads.put("Cookie",getCookie("pt"));
        getDefinitionsArg.setEnable("false");
        getDefinitionsArg.setEntityId(workFlowArg.getEntityId());
        getDefinitionsArg.setKeyWord(workFlowArg.getName());
        json = JSONObject.parseObject(JsonUtil.toJson(getDefinitionsArg));

        requestVo =new RequestVo(requestUrl,json,requestHeads);
        responseVo=hc.httpPost(requestVo);
        Assert.assertEquals(responseVo.getJson().getJSONObject("Result").getIntValue("StatusCode"), 6, "查询流程");
        Assert.assertEquals(json.getString("FailureMessage"), "您无该操作的执行权限",  "普通人员无法查询流程定义");
        workflow.deleteDefinition(workFlowArg.getSourceWorkflowId());

    }
    @Test
    public void getNotNameTest(){
        //不存在的流程名称无法查询出结果
        workFlowArg=workflow.workflowCreateRandom();
        System.err.println(workFlowArg);


        getDefinitionsArg.setKeyWord(workFlowArg.getName()+"1");
        json = JSONObject.parseObject(JsonUtil.toJson(getDefinitionsArg));

        requestVo =new RequestVo(requestUrl,json,requestHeads);
        responseVo=hc.httpPost(requestVo);
        Assert.assertEquals(responseVo.getJson().getJSONObject("Result").getIntValue("StatusCode"), 0, "查询流程");
        Assert.assertEquals(responseVo.getJson().getJSONObject("Value").getJSONArray("datas").size(), 0, "查询的数据为0");
        workflow.deleteDefinition(workFlowArg.getSourceWorkflowId());

    }
    @Test
    public void getMHNameTest(){
        //流程定义模糊查询
        workFlowArg=workflow.workflowCreateRandom();
        System.err.println(workFlowArg);


        getDefinitionsArg.setKeyWord("工作流");
        json = JSONObject.parseObject(JsonUtil.toJson(getDefinitionsArg));

        requestVo =new RequestVo(requestUrl,json,requestHeads);
        responseVo=hc.httpPost(requestVo);
        Assert.assertEquals(responseVo.getJson().getJSONObject("Result").getIntValue("StatusCode"), 0, "查询流程");
        int num=responseVo.getJson().getJSONObject("Value").getJSONArray("datas").size();
        json=responseVo.getJson().getJSONObject("Value");
        for(int i=0;i<num;i++){
            Assert.assertEquals(json.getJSONArray("datas").getJSONObject(i).getString("name").contains("工作流"),true, "所有包含关键字的流程");
        }
        //Assert.assertEquals(responseVo.getJson().getJSONObject("Value").getJSONArray("datas").size(), 0, "查询的数据为0");
        workflow.deleteDefinition(workFlowArg.getSourceWorkflowId());

    }
}
