package com.facishare.openapi.workflow;

import com.alibaba.fastjson.JSONObject;
import com.facishare.openapi.BaseTest;
import com.facishare.openapi.common.DeleteDefinitionArg;
import com.facishare.openapi.common.EnableDefinitionArg;
import com.facishare.openapi.common.workflowUrl;
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
public class DeleteDefinitionTest extends BaseTest {
    public WorkflowGeneral workflow =new WorkflowGeneral();
    DeleteDefinitionArg deleteDefinitionArg =new DeleteDefinitionArg();
    //删除流程
      @BeforeClass
    public void initClass(){
          requestUrl =url+ workflowUrl.DeleteDefinition;

          requestHeads.put("Accept","application/json");
          requestHeads.put("Cookie",Cookie);
      }
    @BeforeMethod
    public void initMethod(){

        requestHeads.put("Cookie",Cookie);
    }


@Test
    public void ordinaryDelete(){
        //普通人员不可删除
        sourceWorkflowId=workflow.workflowCreate();
        workflow.enableDefinition(sourceWorkflowId);

        requestHeads.put("Cookie",getCookie("pt"));
        deleteDefinitionArg.setSourceWorkflowId(sourceWorkflowId);

        json = JSONObject.parseObject(JsonUtil.toJson(deleteDefinitionArg));
        log.info("对象内容:{}", json);

        requestVo =new RequestVo(requestUrl,json,requestHeads);

        responseVo=hc.httpPost(requestVo);
        json=responseVo.getJson().getJSONObject("Result");
        Assert.assertEquals(json.getIntValue("StatusCode"), 6,  "禁用流程");
        Assert.assertEquals(json.getString("FailureMessage"), "您无该操作的执行权限",  "禁用流程");

        workflow.deleteDefinition(sourceWorkflowId);
    // TODO: 2017/10/10 删除的流程无法查询

 }
 //删除的流程无法触发
    // TODO: 2017/10/10 waiting

}
