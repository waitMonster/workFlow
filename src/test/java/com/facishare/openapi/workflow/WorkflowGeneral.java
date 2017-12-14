package com.facishare.openapi.workflow;

import com.alibaba.fastjson.JSONObject;
import com.facishare.openapi.BaseTest;
import com.facishare.openapi.common.*;
import com.facishare.openapi.common.WorkFlowArg;
import com.facishare.openapi.model.WorkflowVo;
import com.facishare.openapi.vo.RequestVo;
import com.facishare.openapi.vo.ResponseVo;
import com.facishare.rest.proxy.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;


/**
 * Created by cuiyongxu on 17/8/4.
 */
@Slf4j
public class WorkflowGeneral extends BaseTest {

    WorkFlowArg workFlowArg=new WorkFlowArg();
//
    //部署工作流

    public String workflowCreate() {
        WorkflowVo workflowVo = getWorkflowVo("deploy.json");
        log.info("对象内容:{}", workflowVo);

        requestUrl =url+workflowUrl.CreateDefinition;

        requestHeads.put("Accept","application/json");
        requestHeads.put("Cookie",Cookie);

        sourceWorkflowId="wf"+System.currentTimeMillis();
        workflowVo.setName("我的流程");
        workflowVo.setSourceWorkflowId(sourceWorkflowId);
        System.err.println(workflowVo);
        json=null;
        json =JSONObject.parseObject(JsonUtil.toJson(workflowVo));
        System.err.println(json);
        requestVo =new RequestVo(requestUrl,json,requestHeads);
        ResponseVo responseVo=hc.httpPost(requestVo);
        Assert.assertEquals(responseVo.getJson().getIntValue("StatusCode"), 0, "新建流程");
        return sourceWorkflowId;

    }
    //新建不重名的流程
    public WorkFlowArg workflowCreateRandom() {

        //WorkFlowArg workFlowArg =new WorkFlowArg();
        WorkflowVo workflowVo = getWorkflowVo("deploy.json");
        log.info("对象内容:{}", workflowVo);

        requestUrl =url+workflowUrl.CreateDefinition;

        requestHeads.put("Accept","application/json");
        requestHeads.put("Cookie",Cookie);

        sourceWorkflowId="wf"+System.currentTimeMillis();
        String name="工作流"+System.currentTimeMillis();

        workflowVo.setName(name);
        workflowVo.setSourceWorkflowId(sourceWorkflowId);
        workflowVo.setEntityId("AccountObj");
        System.err.println(workflowVo);

        workFlowArg.setName(name);
        workFlowArg.setSourceWorkflowId(sourceWorkflowId);
        workFlowArg.setEntityId("AccountObj");

        json =JSONObject.parseObject(JsonUtil.toJson(workflowVo));
        requestVo =new RequestVo(requestUrl,json,requestHeads);
        ResponseVo responseVo=hc.httpPost(requestVo);
        Assert.assertEquals(responseVo.getJson().getIntValue("StatusCode"), 0, "新建流程");
        return workFlowArg;

    }
    //查询工作流详情
    public void postDefinitionDetail() {

        DefinitionDetailArg definitionDetailArg =new DefinitionDetailArg();
        requestUrl =url+"/FHH/EM1HPROCESS/WorkflowAction/GetDefinitionDetail";

        requestHeads.put("Content-Type","application/json");
        requestHeads.put("Accept","application/json");
        requestHeads.put("Cookie",Cookie);

        definitionDetailArg.setSourceWorkflowId("wf"+System.currentTimeMillis());
        json=null;
        json =JSONObject.parseObject(JsonUtil.toJson(definitionDetailArg));
        System.err.println(json);

        requestVo =new RequestVo(requestUrl,json,requestHeads);
        ResponseVo responseVo=hc.httpPost(requestVo);

    }
    //编辑工作流

    public void posttest() {
        WorkflowVo workflowVo = getWorkflowVo("Update.json");
        log.info("对象内容:{}", workflowVo);

        requestUrl =url+"/FHH/EM1HPROCESS/WorkflowAction/UpdateDefinition";

        requestHeads.put("Accept","application/json");
        requestHeads.put("Cookie",Cookie);


        workflowVo.setName("我的流程");
        System.err.println(workflowVo);
        json=null;
        json =JSONObject.parseObject(JsonUtil.toJson(workflowVo));
        System.err.println(json);
        requestVo =new RequestVo(requestUrl,json,requestHeads);
        ResponseVo responseVo=hc.httpPost(requestVo);
    }
    //启用活停用工作流

    public void enableDefinition(String  sourceWorkflowId) {

        EnableDefinitionArg enableDefinitionArg =new EnableDefinitionArg();
        requestUrl =url+workflowUrl.EnableDefinition;

        requestHeads.put("Content-Type","application/json");
        requestHeads.put("Accept","application/json");
        requestHeads.put("Cookie",Cookie);

        enableDefinitionArg.setSourceWorkflowId(sourceWorkflowId);
        enableDefinitionArg.setEnabled(false);
        json=null;
        json =JSONObject.parseObject(JsonUtil.toJson(enableDefinitionArg));
        System.err.println(json);

        requestVo =new RequestVo(requestUrl,json,requestHeads);
        ResponseVo responseVo=hc.httpPost(requestVo);

    }
    //删除工作流

    public JSONObject deleteDefinition(String  sourceWorkflowId) {

        DeleteDefinitionArg deleteDefinitionArg =new DeleteDefinitionArg();
        requestUrl =url+workflowUrl.DeleteDefinition;

        requestHeads.put("Content-Type","application/json");
        requestHeads.put("Accept","application/json");
        requestHeads.put("Cookie",Cookie);

        deleteDefinitionArg.setSourceWorkflowId(sourceWorkflowId);
        json=null;
        json =JSONObject.parseObject(JsonUtil.toJson(deleteDefinitionArg));
        System.err.println(json);

        requestVo =new RequestVo(requestUrl,json,requestHeads);
        responseVo=hc.httpPost(requestVo);
        return responseVo.getJson();

    }
    //获取分版信息
    public void getLicenseInfo() {


        requestUrl =url+"/FHH/EM1HPROCESS/WorkflowAction/GetLicenseInfo";

        requestHeads.put("Content-Type","application/json");
        requestHeads.put("Accept","application/json");
        requestHeads.put("Cookie",Cookie);
        json =JSONObject.parseObject("{}");
        System.err.println(json);


        requestVo =new RequestVo(requestUrl,json,requestHeads);
        ResponseVo responseVo=hc.httpPost(requestVo);

    }
    //查询工作流实例列表

    public void GetInstances() {

        GetInstancesArg getInstancesArg =new GetInstancesArg();
        requestUrl =url+"/FHH/EM1HPROCESS/WorkflowAction/GetInstances";

        requestHeads.put("Content-Type","application/json");
        requestHeads.put("Accept","application/json");
        requestHeads.put("Cookie",Cookie);

        getInstancesArg.setSourceWorkflowId("wf-005");
        getInstancesArg.setKeyWord("");
        getInstancesArg.setOrderBy("");
        json=null;
        json =JSONObject.parseObject(JsonUtil.toJson(getInstancesArg));
        System.err.println(json);
        requestVo =new RequestVo(requestUrl,json,requestHeads);
        ResponseVo responseVo=hc.httpPost(requestVo);

    }
    //查询工作流后动作列表

    public void GetAfterActions() {

        GetAfterActionsArg afterActionsArg =new GetAfterActionsArg();
        requestUrl =url+"/FHH/EM1HPROCESS/WorkflowAction/GetAfterActions";

        requestHeads.put("Content-Type","application/json");
        requestHeads.put("Accept","application/json");
        requestHeads.put("Cookie",Cookie);

        afterActionsArg.setSourceWorkflowId("wf-005");
        afterActionsArg.setKeyWord("");
        afterActionsArg.setOrderBy("");
        json=null;
        json =JSONObject.parseObject(JsonUtil.toJson(afterActionsArg));
        System.err.println(json);

        requestVo =new RequestVo(requestUrl,json,requestHeads);
        ResponseVo responseVo=hc.httpPost(requestVo);

    }
    //查询查询工作流定义列表

    public void GetDefinitions() {

        GetDefinitionsArg getDefinitionsArg =new GetDefinitionsArg();
        requestUrl =url+"/FHH/EM1HPROCESS/WorkflowAction/GetAfterActions";

        requestHeads.put("Content-Type","application/json");
        requestHeads.put("Accept","application/json");
        requestHeads.put("Cookie",Cookie);

        getDefinitionsArg.setEntityId("AccountObj");
        getDefinitionsArg.setKeyWord("");
        getDefinitionsArg.setEnable("");
        json=null;
        json =JSONObject.parseObject(JsonUtil.toJson(getDefinitionsArg));
        System.err.println(json);
        //报错
//        ApiClient.Post(requestUrl,json.toString(),requestHeads);
//        json =JSONObject.parseObject(definitionDetailArg.toString());

        requestVo =new RequestVo(requestUrl,json,requestHeads);
        ResponseVo responseVo=hc.httpPost(requestVo);

    }
}
