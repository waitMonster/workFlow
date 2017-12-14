package com.facishare.openapi.workflow;

import com.alibaba.fastjson.JSONObject;
import com.facishare.openapi.BaseTest;
import com.facishare.openapi.common.workflowUrl;
import com.facishare.openapi.vo.RequestVo;
import com.facishare.openapi.vo.ResponseVo;
import com.facishare.rest.proxy.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import  com.facishare.openapi.workflow.WorkflowGeneral;
import com.facishare.openapi.common.EnableDefinitionArg;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by sunsk on 2017/8/8.
 */
@Slf4j
public class EnableDefinitionTest extends BaseTest {
    public WorkflowGeneral workflow =new WorkflowGeneral();
    EnableDefinitionArg enableDefinitionArg =new EnableDefinitionArg();
    //启用禁用2
      @BeforeClass
    public void initClass(){
          requestUrl =url+ workflowUrl.EnableDefinition;

          requestHeads.put("Accept","application/json");
          requestHeads.put("Cookie",Cookie);
      }
    @BeforeMethod
    public void initMethod(){

        requestHeads.put("Cookie",Cookie);
    }
      //启用状态流程不可删除，禁用流程可删除
    @Test
    public void enable(){
        sourceWorkflowId=workflow.workflowCreate();

        enableDefinitionArg.setSourceWorkflowId(sourceWorkflowId);
        enableDefinitionArg.setEnabled(true);
        json = JSONObject.parseObject(JsonUtil.toJson(enableDefinitionArg));
        log.info("对象内容:{}", json);

        requestVo =new RequestVo(requestUrl,json,requestHeads);

         responseVo=hc.httpPost(requestVo);
        //启动的流程不能删除

        json=workflow.deleteDefinition(sourceWorkflowId).getJSONObject("Result");

        Assert.assertEquals(json.getIntValue("StatusCode"), 6,  "禁用流程");
       Assert.assertEquals(json.getString("FailureMessage"), "请确认该流程是否已停用",  "禁用流程");

        enableDefinitionArg.setEnabled(false);
        json = JSONObject.parseObject(JsonUtil.toJson(enableDefinitionArg));

        requestVo =new RequestVo(requestUrl,json,requestHeads);
         responseVo=hc.httpPost(requestVo);

        json=workflow.deleteDefinition(sourceWorkflowId).getJSONObject("Result");
        Assert.assertEquals(json.getIntValue("StatusCode"), 0,  "禁用流程");
    }

@Test
    public void ordinaryEnable(){
        //普通人员不可停用流程
        sourceWorkflowId=workflow.workflowCreate();
        requestHeads.put("Cookie",getCookie("pt"));
        enableDefinitionArg.setSourceWorkflowId(sourceWorkflowId);
        enableDefinitionArg.setEnabled(true);
        json = JSONObject.parseObject(JsonUtil.toJson(enableDefinitionArg));
        log.info("对象内容:{}", json);

        requestVo =new RequestVo(requestUrl,json,requestHeads);

        responseVo=hc.httpPost(requestVo);
        json=responseVo.getJson().getJSONObject("Result");
        Assert.assertEquals(json.getIntValue("StatusCode"), 6,  "禁用流程");
        Assert.assertEquals(json.getString("FailureMessage"), "您无该操作的执行权限",  "禁用流程");
        workflow.enableDefinition(sourceWorkflowId);
        workflow.deleteDefinition(sourceWorkflowId);


 }
 //禁用的流程无法触发
    // TODO: 2017/10/10 waiting 

}
