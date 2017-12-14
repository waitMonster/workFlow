package com.facishare.openapi.model;

import com.facishare.rest.proxy.model.RemoteContext;
import com.google.common.base.Strings;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by cuiyongxu on 17/6/27.
 */
@Slf4j
@Data
public class WorkflowVo implements Serializable {

    private String id;
    private String name;
    private String description;
    private String sourceWorkflowId;
    private String entityId;
    private String entityName;
    private String type;

    //private Map rule ;    //ruleJson剔除:      appId,tenantId,name,workflowSrcId,   entityId ,更新时有roleId
    private Map<String,Object> workflow;//workflowJson剔除:  appId,tenantId,name,sourceWorkflowId,type,description 更新时有id:
    private List<Integer> triggerTypes;

    private String lastModifiedBy;
    private long modifyTime;
    private String createdBy;
    private long createTime;
    private boolean enable;

    public boolean isNew() {
        return !Strings.isNullOrEmpty(id);
    }


    @Deprecated
    public void addRuleField(Map<String, Object> rule, RemoteContext context) {
        addContext(rule, context);
        rule.put(WorkflowConstants.StringConstants.WORKFLOW_SRC_ID, this.sourceWorkflowId);
        rule.put(WorkflowConstants.StringConstants.ENTITY_ID, this.entityId);
    }

    public void addWorkflowField(Map<String, Object> workflow, RemoteContext context) {
        addContext(workflow, context);
        workflow.put(WorkflowConstants.StringConstants.SOURCE_WORKFLOW_ID, this.sourceWorkflowId);
        workflow.put(WorkflowConstants.StringConstants.TYPE, WorkflowConstants.TYPE);
        workflow.put(WorkflowConstants.StringConstants.NAME, this.name);
        workflow.put(WorkflowConstants.StringConstants.DESCRIPTION, this.description);
        workflow.put(WorkflowConstants.StringConstants.TRIGGER_TYPES, this.triggerTypes);
        workflow.put(WorkflowConstants.StringConstants.ENTITY_ID, this.entityId);
        if (isNew()) {
            workflow.put(WorkflowConstants.StringConstants.ID, this.id);
        }
    }

    private void addContext(Map<String, Object> collection, RemoteContext context) {
        collection.put(WorkflowConstants.StringConstants.APP_ID, context.getAppId());
        collection.put(WorkflowConstants.StringConstants.TENANT_ID, context.getTenantId());
    }

}
