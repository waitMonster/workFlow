package com.facishare.openapi.workflow;

import com.alibaba.fastjson.JSONObject;
import com.facishare.openapi.BaseTest;
import com.facishare.openapi.common.workFlows;
import com.facishare.openapi.common.workflowUrl;
import com.facishare.openapi.dataManage;
import com.facishare.openapi.model.WorkflowVo;
import com.facishare.openapi.vo.RequestVo;
import com.facishare.rest.proxy.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by sunsk on 2017/8/8.
 */
@Slf4j
public class UpdateDefinitionTest extends BaseTest{
    public WorkflowVo workflowVo;
    WorkflowGeneral workflow =new WorkflowGeneral();
    //更新工作流 10
    @BeforeClass
    public void initClass(){
        requestUrl =url+ workflowUrl.UpdateDefinition;

        requestHeads.put("Accept","application/json");

    }
    @BeforeMethod
    public void initMethod(){
        workflowVo = getWorkflowVo("Update.json");
        log.info("对象内容:{}", workflowVo);
        requestHeads.put("Cookie",Cookie);
    }
    //普通人员无法编辑
    @Test
    public void ordinarytest(){
        sourceWorkflowId=workflow.workflowCreate();
        requestHeads.put("Cookie",getCookie("pt"));

        workflowVo.setName("123");
        workflowVo.setSourceWorkflowId(sourceWorkflowId);
        json = JSONObject.parseObject(JsonUtil.toJson(workflowVo));
        log.info("对象内容:{}", json);

        requestVo =new RequestVo(requestUrl,json,requestHeads);

        responseVo=hc.httpPost(requestVo);
        json=responseVo.getJson().getJSONObject("Result");
        Assert.assertEquals(json.getIntValue("StatusCode"),6,"判断普通人员无法添加");
        Assert.assertEquals(json.getString("FailureMessage"),"您无该操作的执行权限","判断普通人员无法添加");
        workflow.enableDefinition(sourceWorkflowId);
        workflow.deleteDefinition(sourceWorkflowId);
    }
    //停用的流程可以编辑
    @Test
    public void stopTest(){
        sourceWorkflowId=workflow.workflowCreate();
        workflow.enableDefinition(sourceWorkflowId);

        workflowVo.setSourceWorkflowId(sourceWorkflowId);
        json = JSONObject.parseObject(JsonUtil.toJson(workflowVo));
        log.info("对象内容:{}", json);

        requestVo =new RequestVo(requestUrl,json,requestHeads);

        responseVo=hc.httpPost(requestVo);
        Assert.assertEquals(responseVo.getJson().getJSONObject("Result").getIntValue("StatusCode"), 0,  "编辑流程");
        workflow.enableDefinition(sourceWorkflowId);
        workflow.deleteDefinition(sourceWorkflowId);
    }
    //删除的流程再次编辑相当于新增（不纯在的apiname直接编辑相当于新增）
    @Test
    public void deleteTest(){
        sourceWorkflowId=workflow.workflowCreate();
        workflow.enableDefinition(sourceWorkflowId);
        workflow.deleteDefinition(sourceWorkflowId);
        workflowVo.setSourceWorkflowId(sourceWorkflowId);
        json = JSONObject.parseObject(JsonUtil.toJson(workflowVo));
        log.info("对象内容:{}", json);
        //workflowVo.setEntityId(workFlows.WorkFlowscope.合同.toString());
        requestVo =new RequestVo(requestUrl,json,requestHeads);

        responseVo=hc.httpPost(requestVo);
        Assert.assertEquals(responseVo.getJson().getJSONObject("Result").getIntValue("StatusCode"), 0,  "编辑流程");
        // TODO: 2017/8/10 增加现在传接口
    }
    //修改范围不做限制
    @Test
    public void scopeTest(){
        sourceWorkflowId=workflow.workflowCreate();
        workflowVo.setSourceWorkflowId(sourceWorkflowId);
        json = JSONObject.parseObject(JsonUtil.toJson(workflowVo));
        workflowVo.setEntityId(workFlows.WorkFlowscope.合同.toString());
        log.info("对象内容:{}", json);
        requestVo =new RequestVo(requestUrl,json,requestHeads);

        responseVo=hc.httpPost(requestVo);
        Assert.assertEquals(responseVo.getJson().getJSONObject("Result").getIntValue("StatusCode"), 0,  "编辑流程");
        workflow.enableDefinition(sourceWorkflowId);
        workflow.deleteDefinition(sourceWorkflowId);    
    }
    //修改流程字段字数限制，修改不存在的apiName

    // TODO: 2017/8/8 编辑没有字数限制 


    @Test(dataProvider="testNum",dataProviderClass=dataManage.class)
        public void codeNumTest(String text,String name){
        if(name.contains("name")){
            sourceWorkflowId=workflow.workflowCreate();
            workflowVo.setName(text);

        } if(name.contains("sourceWorkflowId")){

            sourceWorkflowId=workflow.workflowCreate();
            workflowVo.setName("我的流程");

            workflowVo.setSourceWorkflowId("");
        }
        if(name.contains("description")){
            workflowVo.setName("我的流程");
            workflowVo.setDescription(text);
            workflowVo.setSourceWorkflowId("wf"+System.currentTimeMillis());
        }

        json = JSONObject.parseObject(JsonUtil.toJson(workflowVo));
        requestVo = new RequestVo(requestUrl, json, requestHeads);
        responseVo = hc.httpPost(requestVo);
        if(text.length()==0){
            json=responseVo.getJson().getJSONObject("Result");

            if(name.contains("name")){
                Assert.assertEquals(json.getIntValue("StatusCode"), 6, "新建流程");
                Assert.assertEquals(json.getString("FailureMessage"), "请填写流程名称", "新建流程");
            } if(name.contains("sourceWorkflowId")){
                Assert.assertEquals(json.getString("FailureMessage"), "请设置APINAME", "新建流程");
            }

        }else{
            Assert.assertEquals(responseVo.getJson().getIntValue("StatusCode"), 0, "新建流程");
            workflow.enableDefinition(sourceWorkflowId);
            workflow.deleteDefinition(sourceWorkflowId);

        }
        }

}
