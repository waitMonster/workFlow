package com.facishare.openapi.common;

import lombok.Data;

/**
 * Created by sunsk on 2017/8/7.
 */
public class workflowUrl {
    //新建流程
    public static final String CreateDefinition="/FHH/EM1APROCESS/WorkflowAction/CreateDefinition";
    //停用启用流程
    public static final String EnableDefinition="/FHH/EM1HPROCESS/WorkflowAction/EnableDefinition";
    //删除流程
    public static final String DeleteDefinition="/FHH/EM1HPROCESS/WorkflowAction/DeleteDefinition";
    //编辑工作流
    public static final String UpdateDefinition="/FHH/EM1HPROCESS/WorkflowAction/UpdateDefinition";
    //获取流程详情
    public static final String GetDefinitionDetail="/FHH/EM1HPROCESS/WorkflowAction/GetDefinitionDetail";
    //获取分版数据
    public static final String GetLicenseInfo="/FHH/EM1HPROCESS/WorkflowAction/GetLicenseInfo";
    //查询工作流实例列表
    public static final String GetInstances="/FHH/EM1HPROCESS/WorkflowAction/GetInstances";
    //查询查询后置动作列表
    public static final String GetAfterActions="/FHH/EM1HPROCESS/WorkflowAction/GetAfterActions";
    //查询工作流定义列表
    public static final String GetDefinitions="/FHH/EM1HPROCESS/WorkflowAction/GetDefinitions";
    //查询Apiname是否存在
    public static final String GetApiName="/FHH/EM1HPROCESS/WorkflowAction/GetApiName";



}
