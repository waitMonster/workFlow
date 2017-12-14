package com.facishare.openapi.common;

import lombok.Data;

/**
 * Created by sunsk on 2017/8/4.
 */
@Data
public class EnableDefinitionArg {
    private String sourceWorkflowId = null;
    private Boolean enabled;

}
