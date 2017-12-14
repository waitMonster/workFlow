package com.facishare.openapi.common;

import lombok.Data;

/**
 * Created by sunsk on 2017/8/9.
 */
@Data
public class WorkFlowArg {
    private String name = null;
    private String sourceWorkflowId = null;
    private String entityId = null;
}
