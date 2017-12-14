package com.facishare.openapi.workflow;

import com.alibaba.fastjson.JSONObject;
import com.facishare.openapi.BaseTest;
import com.facishare.openapi.common.workflowUrl;
import com.facishare.openapi.dataManage;
import com.facishare.openapi.model.WorkflowVo;
import com.facishare.openapi.vo.RequestVo;
import com.facishare.rest.proxy.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;

/**
 * Created by sunsk on 2017/8/7.
 */
@Slf4j
public class CreateDefinitionTest extends BaseTest {
    //部署工作流---20个用例啊
    //普通用户无法部署工作流
    public  WorkflowVo workflowVo;
    public WorkflowGeneral workflow =new WorkflowGeneral();
    @BeforeClass
    public void initClass(){
        requestUrl =url+ workflowUrl.CreateDefinition;

        requestHeads.put("Accept","application/json");

    }
    @BeforeMethod
    public void initMethod(){
        workflowVo = getWorkflowVo("deploy.json");
        log.info("对象内容:{}", workflowVo);
        requestHeads.put("Cookie",Cookie);
    }
    //测试普通人员无法添加账号
    @Test
    public void ordinaryTest() {

        requestHeads.put("Cookie",getCookie("pt"));
        workflowVo.setName("我的流程");
        //设置流程apiname
        workflowVo.setSourceWorkflowId("wf"+System.currentTimeMillis());
        System.err.println(workflowVo);
        json=null;
        json = JSONObject.parseObject(JsonUtil.toJson(workflowVo));
        System.err.println(json);
        requestVo =new RequestVo(requestUrl,json,requestHeads);
        responseVo=hc.httpPost(requestVo);
        json=responseVo.getJson().getJSONObject("Result");
        Assert.assertEquals(json.getIntValue("StatusCode"),6,"判断普通人员无法添加");
        Assert.assertEquals(json.getString("FailureMessage"),"您无该操作的执行权限","判断普通人员无法添加");
    }
    //测试适用范围
    @Test(dataProvider="testDate",dataProviderClass=dataManage.class)
    public void scopeTest(String e) {

        System.out.println(e);


            workflowVo.setName("我的流程" + e.toString());
            sourceWorkflowId = "wf" + System.currentTimeMillis();
            workflowVo.setSourceWorkflowId(sourceWorkflowId);
            //workflowVo.setEntityId(e.toString());
            System.err.println(workflowVo);
            json = JSONObject.parseObject(JsonUtil.toJson(workflowVo));
            System.err.println(json);
            requestVo = new RequestVo(requestUrl, json, requestHeads);
            responseVo = hc.httpPost(requestVo);
            Assert.assertEquals(responseVo.getJson().getIntValue("StatusCode"), 0, e.toString() + "新建流程");
            workflow.enableDefinition(sourceWorkflowId);
            workflow.deleteDefinition(sourceWorkflowId);
       // testNum}
    }
        //字数限制测试和为空验证
        @Test(dataProvider="testNum",dataProviderClass=dataManage.class)
        public void nameNum(String text,String name){
        if(name.contains("name")){
            workflowVo.setName(text);
            workflowVo.setSourceWorkflowId("wf"+System.currentTimeMillis());
        } if(name.contains("sourceWorkflowId")){
                workflowVo.setName("我的流程");
                workflowVo.setSourceWorkflowId(text);
            }
            if(name.contains("description")){
                workflowVo.setName("我的流程");
                workflowVo.setDescription(text);
                workflowVo.setSourceWorkflowId("wf"+System.currentTimeMillis());
            }

            json = JSONObject.parseObject(JsonUtil.toJson(workflowVo));
            requestVo = new RequestVo(requestUrl, json, requestHeads);
            responseVo = hc.httpPost(requestVo);
            json=responseVo.getJson().getJSONObject("Result");
            if(text.length()==0&&name!="description"){

                Assert.assertEquals(json.getIntValue("StatusCode"), 6, "新建流程");
                if(name.contains("name")){
                    Assert.assertEquals(json.getString("FailureMessage"), "请填写流程名称", "新建流程");
                } if(name.contains("sourceWorkflowId")){
                    Assert.assertEquals(json.getString("FailureMessage"), "请设置APINAME", "新建流程");
                }

            } else if(text.length()==0&&name=="description"){
                Assert.assertEquals(json.getIntValue("StatusCode"), 0, "新建流程");
                workflow.enableDefinition(sourceWorkflowId);
                workflow.deleteDefinition(sourceWorkflowId);
            }else{
                Assert.assertEquals(json.getIntValue("StatusCode"), 6, "新建流程");
                if(name.contains("name")){
                    Assert.assertEquals(json.getString("FailureMessage"), "流程名称不能超过20字", "新建流程");
                } if(name.contains("sourceWorkflowId")){
                    Assert.assertEquals(json.getString("FailureMessage"), "APINAME不能超过100字", "新建流程");

                }


            }


        }
//apiName 重复的apiname不可添加，但是删除后可apiname可复用
    @Test
    public void apiNameTest(){
        sourceWorkflowId="wf"+System.currentTimeMillis();
        workflowVo.setSourceWorkflowId(sourceWorkflowId);
        json = JSONObject.parseObject(JsonUtil.toJson(workflowVo));
        requestVo = new RequestVo(requestUrl, json, requestHeads);
        responseVo = hc.httpPost(requestVo);
        Assert.assertEquals(responseVo.getJson().getIntValue("StatusCode"), 0, "新建流程");
        //新增重复流程
        responseVo = hc.httpPost(requestVo);
        Assert.assertEquals(responseVo.getJson().getJSONObject("Result").getIntValue("StatusCode"), 6, "新建流程");
        Assert.assertEquals(responseVo.getJson().getJSONObject("Result").getString("FailureMessage"), "API名称重复，无法部署，请检查！", "新建流程");

        workflow.enableDefinition(sourceWorkflowId);
        workflow.deleteDefinition(sourceWorkflowId);

        //删除后的的apiname可用
        responseVo = hc.httpPost(requestVo);

        Assert.assertEquals(responseVo.getJson().getIntValue("StatusCode"), 0, "新建流程");
        workflow.enableDefinition(sourceWorkflowId);
        workflow.deleteDefinition(sourceWorkflowId);
    }


}
