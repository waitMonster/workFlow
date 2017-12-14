
package com.facishare.openapi.model;


/**
 * Created by cuiyongxu on 17/6/21.
 */
public interface WorkflowConstants {
    //TODO 和波波老师沟通下,依然是CRM
    String APP_ID = "CRM";
    String TYPE = "workflow";

    interface StringConstants {
        String TENANT_ID = "tenantId",
                APP_ID = "appId",
                TRIGGER_TYPES = "triggerTypes",
                WORKFLOW_SRC_ID = "workflowSrcId",//历史原因,都是sourceWorkflowId,流程引擎暂不做修改
                ENTITY_ID = "entityId",
                SOURCE_WORKFLOW_ID = "sourceWorkflowId",
                NAME = "name",
                DESCRIPTION = "description",
                ID = "id",
                TYPE = "type";
    }

    interface MetadataKey {
        String tenantId = "tenant_id",
                displayName = "display_name",
                apiName = "api_name",
                fields = "fields",
                id = "_id",
                isActive = "is_active",
                lastModifiedTime = "last_modified_time",
                defineType = "define_type";
    }


}
